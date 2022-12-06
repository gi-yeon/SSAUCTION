package com.ssafy.ssauction.web.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.mysql.cj.x.protobuf.MysqlxCursor;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.resultOrders.ResultOrdersService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Sessions.SessionsCreateJoinRequestDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsInfoResponseDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsRemoveUserRequestDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsTokenResponseDto;
import com.ssafy.ssauction.web.dto.resultOrders.ResultOrdersSaveDto;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import retrofit2.http.Path;

@Api(value="세션 API", tags={"Sessions"})
@RestController
@RequestMapping("/sessions")
public class SessionsController {

    // OpenVidu object as entrypoint of the SDK
    private OpenVidu openVidu;
    private final UsersService usersService;

    private final HousesService housesService;

    private final ResultOrdersService resultOrdersService;

    // Collection to pair session names and OpenVidu Session objects
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();

    // 경매장별 입찰 정보를 저장하는 map
    // 각 경매장 이름을 key로 사용하고, 각 경매장별 상위 3명의 정보를 ArrayDeque에 담아 value로 저장한다.
    // ArrayDeque는 적은 금액에서 큰 금액 순으로 3개까지만 저장한다.
    // 방이 생성될 때
    private Map<String, ArrayDeque<ResultOrdersSaveDto>> mapBids = new ConcurrentHashMap<>();

    // Collection to pair session names and tokens (the inner Map pairs tokens and
    // role associated)
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
    private Map<String, Set<String>> mapBlackList = new ConcurrentHashMap<>();

    // URL where our OpenVidu server is listening
    private String OPENVIDU_URL;
    // Secret shared with our OpenVidu server
    private String SECRET;

    public SessionsController(UsersService usersService, HousesService housesService, ResultOrdersService resultOrdersService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.usersService = usersService;
        this.housesService = housesService;
        this.resultOrdersService = resultOrdersService;
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostMapping("/getToken")
    @ApiOperation(value = "Token 요청", notes = "<strong>OpenVidu 서버</strong>에 토큰을 요청한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 404, message = "잘못된 요청", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 406, message = "잘못된 닉네임", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 500, message = "서버 오류", response = SessionsTokenResponseDto.class)
    })
    public ResponseEntity<SessionsTokenResponseDto> getToken(@RequestBody @ApiParam(value="세션 이름 및 유저 정보", required = true) SessionsCreateJoinRequestDto sessionNameLoggedUserParam)
            throws ParseException {

        System.out.println("Getting a token from OpenVidu Server | {sessionName, loggedUser, isHost}=" + sessionNameLoggedUserParam);

        // The video-call to connect
        // sessionName이라고 되어있지만 이름 중복으로 인한 오작동을 피하기 위해 sessionNo를 세션 이름으로 사용한다.
        String sessionName = (String) sessionNameLoggedUserParam.getSessionName();
        // 방에 입장할 때 입력한 닉네임
        String loggedUser = (String) sessionNameLoggedUserParam.getLoggedUser();
        // 실제 유저 번호
        String userNo = (String) sessionNameLoggedUserParam.getUserNo();
        // 방의 호스트인지 판별하는 변수
        Boolean isHost = (Boolean) sessionNameLoggedUserParam.getIsHost();

        System.out.println("sessionName : " + sessionName + " loggedUser : " + loggedUser + " userNo : " + userNo + " isHost : " + isHost);

        if (loggedUser == null || loggedUser.length() == 0 || loggedUser.length() > 10) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        // 토큰을 요청한 유저에게 부여할 권한
        //  SUBSCRIBER : stream 수신만 가능
        //  PUBLISHER : SUBSCRIBER + stream 전송 가능
        //  MODERATOR : PUBLISHER + 강제로 다른 사람의 연결을 끊을 수 있음
        OpenViduRole role = OpenViduRole.PUBLISHER;
        if (isHost) {
            role = OpenViduRole.MODERATOR;
        }

        // onConnectionCreated 이벤트 발생 시 기존에 접속해있던 다른 유저들에게 전달되는 JSON 형식 추가 정보.
        String serverData = "{\"clientData\": \"" + loggedUser + "\"," + "\"isHost\" : " + isHost +"}";

        // 서버 데이터와 역할을 담아 connectionProperties를 생성한다.
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role).build();

        SessionsTokenResponseDto response = new SessionsTokenResponseDto();

        // 이미 존재하는 세션이면,
        if (this.mapSessions.get(sessionName) != null) {
            System.out.println("Existing session " + sessionName);
            try {
                // 블랙리스트에 있는 경우 403 에러를 발생시킨다.
                if (this.mapBlackList.get(sessionName).contains(userNo)) {
                    return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
                };

                // Generate a new Connection with the recently created connectionProperties
                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

                // Update our collection storing the new token
                this.mapSessionNamesTokens.get(sessionName).put(token, role);

                // Prepare the response with the token
                response.setToken(token);

                // Return the response to the client
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (OpenViduJavaClientException e1) {
                // If internal error generate an error message and return it to client
                return getErrorResponse(e1);
            } catch (OpenViduHttpException e2) {
                if (404 == e2.getStatus()) {
                    // Invalid sessionId (user left unexpectedly). Session object is not valid
                    // anymore. Clean collections and continue as new session
                    this.mapSessions.remove(sessionName);
                    this.mapSessionNamesTokens.remove(sessionName);
                }
            }
        }

        // New session
        System.out.println("New session " + sessionName);
        try {
            // Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            // Generate a new Connection with the recently created connectionProperties
            String token = session.createConnection(connectionProperties).getToken();

            // 세션 객체를 저장
            this.mapSessions.put(sessionName, session);
            // 세션에 접속해있는 유저를 저장하는 map 저장하고,
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            // 현재 접속 요청한 유저를 추가
            this.mapSessionNamesTokens.get(sessionName).put(token, role);
            // 입찰 정보 저장 deque 생성
            this.mapBids.put(sessionName, new ArrayDeque<>());
            // 블랙리스트 생성
            this.mapBlackList.put(sessionName, new HashSet<>());

            // 응답에 토큰 세팅
            response.setToken(token);

            // Return the response to the client
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // If error generate an error message and return it to client
            return getErrorResponse(e);
        }
    }

    @PostMapping("/removeUser")
    @ApiOperation(value = "User 제거", notes = "session에 접속해있는 User의 token을 제거한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = JSONObject.class),
            @ApiResponse(code = 202, message = "이미 처리된 요청", response = JSONObject.class),
            @ApiResponse(code = 404, message = "잘못된 요청", response = JSONObject.class),
            @ApiResponse(code = 500, message = "서버 오류", response = JSONObject.class)
    })
    public ResponseEntity<JSONObject> removeUser(@RequestBody @ApiParam(value="세션 이름 및 토큰", required = true) SessionsRemoveUserRequestDto sessionNameToken)
            throws Exception {
        
        System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

        // Retrieve the params from BODY
        String sessionName = (String) sessionNameToken.getSessionName();
        String cameraToken = (String) sessionNameToken.getCameraToken();
//        String screenToken = (String) sessionNameToken.getScreenToken();
        String userNo = (String) sessionNameToken.getUserNo();
        String reason = (String) sessionNameToken.getReason();

//        System.out.println("cameraToken : " + cameraToken + " screenToken : " + screenToken);

        if (reason.equals("kick-out")) {
            mapBlackList.get(sessionName).add(userNo);
        }

        // If the session exists
         if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {
//            this.mapSessionNamesTokens.get(sessionName).remove(screenToken);
            // If the token exists
            if (this.mapSessionNamesTokens.get(sessionName).remove(cameraToken) != null) {

                // User left the session
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    // Last user left: session must be removed
                    this.mapSessions.remove(sessionName);
                    this.mapBids.remove(sessionName);
                    this.mapBlackList.remove(sessionName);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                // The TOKEN wasn't valid
                System.out.println("Problems in the app server: the TOKEN is not in the map. Maybe the auction is already finished");
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    private ResponseEntity<SessionsTokenResponseDto> getErrorResponse(Exception e) {
        SessionsTokenResponseDto response = new SessionsTokenResponseDto();
        response.setCause(e.getCause().toString());
        response.setError(e.getMessage());
        response.setException(e.getClass().toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 현재 열려있는 모든 세션을 조회한다.
    @GetMapping
    public ResponseEntity<List<String>> getSessions() throws Exception {
        List<String> sessionNames = new ArrayList<String>(this.mapSessions.keySet());
        return new ResponseEntity<>(sessionNames, HttpStatus.OK);
    }

    // 해당 세션에 있는 유저들을 조회한다.
    @GetMapping({"{sessionName}"})
    public ResponseEntity<List<String>> getSessionUsers(@PathVariable String sessionName) throws Exception {
        List<String> sessionUsers = new ArrayList<>(this.mapSessionNamesTokens.get(sessionName).keySet());
        return new ResponseEntity<>(sessionUsers, HttpStatus.OK);
    }

    // 입력한 이름을 가진 세션을 닫는다.
    @PostMapping("/closeSession")
    public ResponseEntity<String> closeSession(@RequestParam String sessionName) throws Exception {
        Session sessionToClose = mapSessions.get(sessionName);
        System.out.println(sessionToClose);
        if (sessionToClose.equals(null)) {
            return new ResponseEntity<String>("Unknown session", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        sessionToClose.close();
        mapSessions.remove(sessionName);
        mapSessionNamesTokens.remove(sessionName);
        mapBids.remove(sessionName);
        return new ResponseEntity<>("Session closed", HttpStatus.OK);
    }

    @GetMapping("/bid/{sessionName}")
    public ResponseEntity<ArrayDeque<ResultOrdersSaveDto>> getBid(@PathVariable String sessionName) {
        if (mapBids.containsKey(sessionName)) {
            return new ResponseEntity<>(mapBids.get(sessionName), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/bidstart")
    public ResponseEntity<String> startBid(@RequestBody Map<String, Object> info) throws Exception {
        String sessionName = info.get("sessionName").toString();

        Long houseNo = Long.parseLong(sessionName);
        Houses house=housesService.findEntityById(houseNo);
        // house 상태를 경매 시작(입장 불가)로 변경
        house.setHouseStatus(2);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // 입찰 현황을 갱신한다
    @PutMapping("/bid")
    public ResponseEntity<ArrayDeque<ResultOrdersSaveDto>> updateBid(@RequestBody Map<String, Object> bid) throws Exception {
        String sessionName = bid.get("sessionName").toString();
        String userName =  bid.get("bidder").toString();
        String userNo = bid.get("userNo").toString();
        String priceToBid = bid.get("priceToBid").toString();

        // 금액 유효성 검사
        if (priceToBid.length() > 13 || Long.parseLong(priceToBid) < 0) {
            return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

        System.out.println("bidder: " + userName + "userNo: " + userNo + " priceToBid: " + priceToBid + " mySessionId: " + sessionName);
        ResultOrdersSaveDto newBid = new ResultOrdersSaveDto(Long.parseLong(userNo), Long.parseLong(sessionName), Integer.parseInt(priceToBid));


        // 해당 경매장 입찰 정보를 담을 map이 존재하지 않는다면 경매가 종료된 것이므로 417을 return
        if (!mapBids.containsKey(sessionName)) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
        // 새로 입찰한 가격이 서버에 저장된 현재 최고가보다 낮거나,
        // 새로 입찰한 유저가 기존 최고가를 입찰한 유저와 같다면 203을 return
        ArrayDeque<ResultOrdersSaveDto> queue = mapBids.get(sessionName);
        if (!queue.isEmpty() &&
                (queue.getLast().getOrderPrice() > newBid.getOrderPrice()
                        || queue.getLast().getUserNo() == newBid.getUserNo())) {
            return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

        // 적은 금액에서 큰 금액 순으로 저장한다.
        queue.addLast(newBid);
        // 3개를 초과하는 입찰 데이터는 필요 없으므로 제거한다.
        if (queue.size() > 3) {
            queue.removeFirst();
        }

        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    // 경매를 종료한다.
    @PostMapping("/bid")
    public ResponseEntity<String> finishBid(@RequestBody Map<String, Object> result) throws Exception {
        String sessionName = result.get("sessionName").toString();

        ArrayDeque<ResultOrdersSaveDto> bidList = mapBids.get(sessionName);
        if (bidList != null && bidList.size() > 0) {
            for (ResultOrdersSaveDto bidInfo : bidList) {
                Long userNo = bidInfo.getUserNo();
                Long houseNo = Long.parseLong(sessionName);
                Users user =usersService.findEntityById(userNo);
                Houses house=housesService.findEntityById(houseNo);
                // house 상태를 경매 종료로 변경
                house.setHouseStatus(3);
                // 경매 결과를 DB에 저장한다.
                ResultOrders resultOrders=resultOrdersService.save(user, house, bidInfo);
                user.getResults().add(resultOrders);
                house.getResults().add(resultOrders);
            }
        }


        mapSessions.remove(sessionName);
        mapSessionNamesTokens.remove(sessionName);
        // 실시간 경매 결과 저장소를 제거한다.
        mapBids.remove(sessionName);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Spring boot 서버에서 유지하고 있는 실시간 세션-입찰 정보를 메모리에서 제거한다.(개발/테스트용)
    @DeleteMapping("/bid/{sessionName}")
    public ResponseEntity<ArrayDeque<ResultOrdersSaveDto>> removeBid(@PathVariable String sessionName) throws Exception {
        if (mapBids.containsKey(sessionName)) {
            ArrayDeque<ResultOrdersSaveDto> result = mapBids.get(sessionName);

            mapBids.remove(sessionName);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
