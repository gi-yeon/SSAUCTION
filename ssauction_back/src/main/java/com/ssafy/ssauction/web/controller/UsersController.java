package com.ssafy.ssauction.web.controller;


import com.ssafy.ssauction.auth.JwtTokenProvider;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.likes.LikesService;
import com.ssafy.ssauction.service.storage.FileSystemStorageService;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.HousesResponseDto;
import com.ssafy.ssauction.web.dto.likes.LikesSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsGetResponseDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import com.ssafy.ssauction.web.dto.users.*;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private static final String SUCCESS = "success";

    private static final String FAIL = "fail";
    private final UsersService usersService;
    private final UserImgsService userImgsService;
    private final JwtTokenProvider jwtTokenProvider;


    private final HousesService housesService;
    private final LikesService likesService;
    private final FileSystemStorageService storageService;

    @Autowired
    PasswordEncoder passwordEncoder;


    //프로필 가져오기
    @GetMapping("/profile/{userNo}")
    public UserInfoResponseDto findById(@PathVariable Long userNo) {
        return usersService.getInfo(userNo);
    }


    @GetMapping("/profile/img/{userNo}")
    public ResponseEntity<UserImgsGetResponseDto> findProfileInfoById(@PathVariable Long userNo) {
        UserImgs img = userImgsService.findEntityById(userNo);
        String uri = img.getUserImgUri();
        System.out.println(uri);
        byte[] transform = null;
        System.out.println("2 under transform");
        try {
            System.out.println("3 before file");
            File file = new File(System.getProperty("user.dir") + "/imgs/profile/" + uri);
            System.out.println("3-1 " + file);
            FileInputStream inputStream = new FileInputStream(file);
            System.out.println("3-2 " + inputStream);
            transform = new byte[(int) file.length()];
            inputStream.read(transform);
            inputStream.close();
            System.out.println("4 after file");
            System.out.println("5 ioexception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        UserImgsGetResponseDto resDto = UserImgsGetResponseDto.builder()
                .userMainImg(transform)
                .infoDto(
                        UserInfoResponseDto.builder()
                                .user(usersService.findEntityById(userNo))
                                .build()
                )
                .build();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(resDto);
    }

    @PutMapping("/profile/img/{userNo}")
    public ResponseEntity<String> updateImg(
            @PathVariable Long userNo,
            @RequestPart(value = "file") MultipartFile[] files) {
        boolean isSuccess = false;
        MultipartFile file = files[0];
        // FileUpload 관련 설정
        if (file != null && !file.isEmpty()) {                          //  file 데이터가 유효하다면,
            System.out.println(file.getName());
            System.out.println(file.getContentType());
            String originalFileName = file.getOriginalFilename();           //          원본 파일 이름을 알아둔다.
            if (!originalFileName.isEmpty()) {                              //          원본 파일 이름이 유효하다면,
                String saveFileName = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
                        + originalFileName                                  //              원본 파일 이름을 합친다.
                        .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
                storageService.store(file, saveFileName, "profile");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
                isSuccess = userImgsService.update(userNo, UserImgsUpdateRequestDto.builder()
                        .imgName(originalFileName)
                        .imgUri(saveFileName)
                        .build());   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
            }
        }
        if (isSuccess)
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/join")
    public String save(@RequestBody UsersSaveRequestDto requestDto) {
        System.out.println(requestDto.toString());
        Users user = usersService.save(requestDto);
        Long userImgs = userImgsService.save(user);
        System.out.println(userImgs);
        if (user == null) {
            return "FAIL";
        }
        return "OK";
    }

    //이메일 중복 확인
    //중복되면 true, 중복되지 않으면 false 반환
    @GetMapping("/checkEmail/{userEmail}")
    public ResponseEntity<?> checkEmailDuplicate(@PathVariable String userEmail) {
        //false라면, 즉 중복되지 않는다면
        if (!usersService.checkEmailDuplicate(userEmail)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //닉네임 중복 확인
    @GetMapping("/checkNickname/{userNickname}")
    public ResponseEntity<?> checkNicknameDuplicate(@PathVariable String userNickname) {
        //false라면, 즉 중복되지 않는다면
        if (!usersService.checkNicknameDuplicate(userNickname)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //비밀번호 확인
    @PostMapping("/pwdCheck")
    public ResponseEntity<?> pwdCheck(@RequestBody PasswordChckRequestDto requestDto) {
        Long userNo = requestDto.getUserNo();
        String userPwd = requestDto.getUserPwd();

        //userEmail로 DB에 저장된 user정보 불러옴
        UsersAuthResponseDto user = usersService.findByUserNo(userNo);

        //비밀번호가 올바르게 입력됐다면
        if (passwordEncoder.matches(userPwd, user.getUserPwd())) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //회원정보 수정에서 비밀번호 재설정
    @PutMapping("/profile/resetPwd/{userNo}")
    public ResponseEntity<?> update(@PathVariable Long userNo, @RequestBody UsersUpdatePwdDto resetPwdDto) {
        if (usersService.profileUpdatePwd(userNo, resetPwdDto)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    // 아이디 찾기
    // 전화번호를 이용해 아이디(이메일) 찾기 구현
    @GetMapping("/findId/{userPhoneNo}")
    public UsersFindIdDto findByPhoneNo(@PathVariable String userPhoneNo) {
        return usersService.findByPhoneNo(userPhoneNo);
    }

    // 비밀번호 재설정
    // 아이디(이메일) + 전화번호를 이용해 비밀번호 재설정 구현
    @PutMapping("/resetPwd/{userPhoneNo}/{userId}")
    public String update(@PathVariable String userPhoneNo, @PathVariable String userId, @RequestBody UsersUpdatePwdDto resetPwdDto) {
        return usersService.updatePwd(userPhoneNo, userId, resetPwdDto);
    }


    @PutMapping("/profile/info/{userNo}")
    public ResponseEntity<String> updateInfo(@PathVariable Long userNo, @RequestBody UsersInfoUpdateRequestDto requestDto) {
        try {
            usersService.updateInfo(userNo, requestDto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }


    @PutMapping("deleteUser/{userNo}")
    public ResponseEntity delete(@PathVariable Long userNo) {
        if (usersService.deleteUser(userNo)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsersAuthRequestDto loginInfo, HttpServletResponse res) {
        String userEmail = loginInfo.getUserEmail();
        String userPwd = loginInfo.getUserPwd();
        HttpStatus status = null;

        HashMap<String, Object> result = new HashMap<>();

        //userEmail로 DB에 저장된 user정보 불러옴
        UsersAuthResponseDto user = usersService.findByUserEmail(userEmail);

//        if (user.getAuthority().equals("ROLE_ADMIN") || user.getAuthority().equals("ROLE_USER")) {

            //비밀번호가 올바르게 입력됐다면
            if (passwordEncoder.matches(userPwd, user.getUserPwd())) {

                //맵에 유저정보 담아준다. (jwt 페이로드에 넣을 것)
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("userEmail", user.getUserEmail());
                userMap.put("userNickname", user.getUserNickname());
                userMap.put("authority", user.getAuthority());


                //accessToken, refreshToken 생성하고 refresh token은 DB에 저장
                String accessToken = jwtTokenProvider.createAccessToken(userMap);
                String refreshToken = jwtTokenProvider.createRefreshToken(userEmail);
                usersService.updateRefreshToken(user.getUserNo(), refreshToken); //token db 저장

                //result에 정보 담아준다.
                result.put("userNo", user.getUserNo());
                result.put("userNickname", user.getUserNickname());
                result.put("userGrade", user.getUserGrade());
                result.put("userAuthority", user.getAuthority());
                result.put("accessToken", accessToken);
                result.put("refreshToken", refreshToken);

                //access token 쿠키에 담아줌
                Cookie cookie = new Cookie("accessToken", accessToken);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);

                cookie.setMaxAge(60 * 30); //파기 시간은 토큰의 유효시간과 같다.
                res.addCookie(cookie);

                //refresh token 쿠키에 담아줌
                Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
                refreshCookie.setPath("/");
                refreshCookie.setHttpOnly(true);
                refreshCookie.setSecure(true);
                refreshCookie.setMaxAge(60 * 60 * 24 * 3); //3일 간 유효
                res.addCookie(refreshCookie);

                //success 메시지 담아준다.
                result.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED; //202

            } else {
                //실패
                result.put("message", FAIL);
                status = HttpStatus.ACCEPTED;

            }
        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse res) {
        //access token 제거
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);

        //refresh token 제거
        Cookie recookie = new Cookie("refreshToken", null);
        recookie.setHttpOnly(true);
        recookie.setSecure(true);
        recookie.setMaxAge(0);
        recookie.setPath("/");
        res.addCookie(recookie);

        Map<String, Object> result = new HashMap<>();
        result.put("message", SUCCESS);

        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String, Object>>(result, status);


    }

    //refresh token으로 access token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> validateRefreshToken(@RequestBody Long userNo, HttpServletResponse res) {

        HashMap<String, Object> result = new HashMap<>();

        //userNo로 db에서 refresh token 가져온다.
        String refreshToken = usersService.findByUserNo(userNo).getUserRefreshToken();

        //userNo로 email, nickname, authorigy 가져와서 map에 넣어준다.
        UsersAuthResponseDto user = usersService.findByUserNo(userNo);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userEmail", user.getUserEmail());
        userMap.put("userNickname", user.getUserNickname());
        userMap.put("authority", user.getAuthority());

        //refresh token 유효성 검사 후 유효하다면 새로운 access token 생성
        String newAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken, userMap);
        HttpStatus status = null;

        //새로운 access token 이 생성됐다면
        if (newAccessToken != null) {

            //기존 access token 쿠기 삭제하고
            Cookie cookie = new Cookie("accessToken", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);

            //새로운 access token 쿠키로 등록
            Cookie newCookie = new Cookie("accessToken", newAccessToken);
            newCookie.setPath("/");
            newCookie.setHttpOnly(true);
            newCookie.setSecure(true);
            newCookie.setMaxAge(60 * 30);
            res.addCookie(newCookie);

            //유저 정보 결과로 넣는다.
            result.put("userNo", user.getUserNo());
            result.put("userNickname", user.getUserNickname());
            result.put("userGrade", user.getUserGrade());

            //access token 결과로 넣는다.
            result.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        }
        //access token이 생성되지 않았다면 refresh token이 유효하지 않은 것
        else {
            result.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    //쿠키에서 토큰 가져다 반환
    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> getCookieToken(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", null);
        map.put("refreshToken", null);
        HttpStatus status = null;
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setHeader("Access-Control-Allow-Origin", "*");
        //token 추출해서 map에 넣어준다.
        Cookie[] list = req.getCookies();
        if(list != null){
        for (Cookie cookie : list) {
            if (cookie.getName().equals("accessToken")) {
                map.replace("accessToken", cookie.getValue());
            } else if (cookie.getName().equals("refreshToken")) {
                map.replace("refreshToken", cookie.getValue());
            }
        }}
        status = HttpStatus.ACCEPTED;

        return new ResponseEntity<>(map, status);
    }

    @PostMapping("/likes")
    public ResponseEntity<String> createLikes(@RequestBody LikesSaveRequestDto saveDto) {
        Users user = usersService.findEntityById(saveDto.getUserNo());
        Houses house = housesService.findEntityById(saveDto.getHouseNo());
        Likes like = likesService.save(user, house);
        user.getLikes().add(like);
        house.getLikes().add(like);
        return new ResponseEntity<>("created", HttpStatus.OK);
    }

    @GetMapping("/likes/{userNo}")
    public ResponseEntity<List<HousesResponseDto>> getLikeHouse(@PathVariable Long userNo) {
        Users user = usersService.findEntityById(userNo);
        System.out.println(user.toString());
        List<Likes> likes = user.getLikes();
        List<HousesResponseDto> list = new ArrayList<>();
        System.out.println(likes.toString());
        for (Likes like : likes) {
            list.add(HousesResponseDto.builder().house(like.getHouse()).build());
        }
        System.out.println("\n\n" + list.toString() + "\n\n");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @DeleteMapping("/users/likes")
//    public ResponseEntity<String>
}
