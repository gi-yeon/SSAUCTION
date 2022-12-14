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


    //????????? ????????????
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
        // FileUpload ?????? ??????
        if (file != null && !file.isEmpty()) {                          //  file ???????????? ???????????????,
            System.out.println(file.getName());
            System.out.println(file.getContentType());
            String originalFileName = file.getOriginalFilename();           //          ?????? ?????? ????????? ????????????.
            if (!originalFileName.isEmpty()) {                              //          ?????? ?????? ????????? ???????????????,
                String saveFileName = UUID.randomUUID().toString()          //              ????????? ???????????? ????????????.
                        + originalFileName                                  //              ?????? ?????? ????????? ?????????.
                        .substring(originalFileName.lastIndexOf('.'));  //              ?????? ?????? ???????????? ?????????.
                storageService.store(file, saveFileName, "profile");       //              ?????? ?????? ????????? ???????????? ??? ????????? ????????? ???????????? file??? ????????????.
                isSuccess = userImgsService.update(userNo, UserImgsUpdateRequestDto.builder()
                        .imgName(originalFileName)
                        .imgUri(saveFileName)
                        .build());   //  itemImgsService??? ?????? DB??? ItemImg ????????? ????????????.
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

    //????????? ?????? ??????
    //???????????? true, ???????????? ????????? false ??????
    @GetMapping("/checkEmail/{userEmail}")
    public ResponseEntity<?> checkEmailDuplicate(@PathVariable String userEmail) {
        //false??????, ??? ???????????? ????????????
        if (!usersService.checkEmailDuplicate(userEmail)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //????????? ?????? ??????
    @GetMapping("/checkNickname/{userNickname}")
    public ResponseEntity<?> checkNicknameDuplicate(@PathVariable String userNickname) {
        //false??????, ??? ???????????? ????????????
        if (!usersService.checkNicknameDuplicate(userNickname)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //???????????? ??????
    @PostMapping("/pwdCheck")
    public ResponseEntity<?> pwdCheck(@RequestBody PasswordChckRequestDto requestDto) {
        Long userNo = requestDto.getUserNo();
        String userPwd = requestDto.getUserPwd();

        //userEmail??? DB??? ????????? user?????? ?????????
        UsersAuthResponseDto user = usersService.findByUserNo(userNo);

        //??????????????? ???????????? ???????????????
        if (passwordEncoder.matches(userPwd, user.getUserPwd())) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    //???????????? ???????????? ???????????? ?????????
    @PutMapping("/profile/resetPwd/{userNo}")
    public ResponseEntity<?> update(@PathVariable Long userNo, @RequestBody UsersUpdatePwdDto resetPwdDto) {
        if (usersService.profileUpdatePwd(userNo, resetPwdDto)) {
            return ResponseEntity.ok(SUCCESS);
        } else {
            return ResponseEntity.ok(FAIL);
        }
    }

    // ????????? ??????
    // ??????????????? ????????? ?????????(?????????) ?????? ??????
    @GetMapping("/findId/{userPhoneNo}")
    public UsersFindIdDto findByPhoneNo(@PathVariable String userPhoneNo) {
        return usersService.findByPhoneNo(userPhoneNo);
    }

    // ???????????? ?????????
    // ?????????(?????????) + ??????????????? ????????? ???????????? ????????? ??????
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

        //userEmail??? DB??? ????????? user?????? ?????????
        UsersAuthResponseDto user = usersService.findByUserEmail(userEmail);

//        if (user.getAuthority().equals("ROLE_ADMIN") || user.getAuthority().equals("ROLE_USER")) {

            //??????????????? ???????????? ???????????????
            if (passwordEncoder.matches(userPwd, user.getUserPwd())) {

                //?????? ???????????? ????????????. (jwt ??????????????? ?????? ???)
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("userEmail", user.getUserEmail());
                userMap.put("userNickname", user.getUserNickname());
                userMap.put("authority", user.getAuthority());


                //accessToken, refreshToken ???????????? refresh token??? DB??? ??????
                String accessToken = jwtTokenProvider.createAccessToken(userMap);
                String refreshToken = jwtTokenProvider.createRefreshToken(userEmail);
                usersService.updateRefreshToken(user.getUserNo(), refreshToken); //token db ??????

                //result??? ?????? ????????????.
                result.put("userNo", user.getUserNo());
                result.put("userNickname", user.getUserNickname());
                result.put("userGrade", user.getUserGrade());
                result.put("userAuthority", user.getAuthority());
                result.put("accessToken", accessToken);
                result.put("refreshToken", refreshToken);

                //access token ????????? ?????????
                Cookie cookie = new Cookie("accessToken", accessToken);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);

                cookie.setMaxAge(60 * 30); //?????? ????????? ????????? ??????????????? ??????.
                res.addCookie(cookie);

                //refresh token ????????? ?????????
                Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
                refreshCookie.setPath("/");
                refreshCookie.setHttpOnly(true);
                refreshCookie.setSecure(true);
                refreshCookie.setMaxAge(60 * 60 * 24 * 3); //3??? ??? ??????
                res.addCookie(refreshCookie);

                //success ????????? ????????????.
                result.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED; //202

            } else {
                //??????
                result.put("message", FAIL);
                status = HttpStatus.ACCEPTED;

            }
        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse res) {
        //access token ??????
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);

        //refresh token ??????
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

    //refresh token?????? access token ?????????
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> validateRefreshToken(@RequestBody Long userNo, HttpServletResponse res) {

        HashMap<String, Object> result = new HashMap<>();

        //userNo??? db?????? refresh token ????????????.
        String refreshToken = usersService.findByUserNo(userNo).getUserRefreshToken();

        //userNo??? email, nickname, authorigy ???????????? map??? ????????????.
        UsersAuthResponseDto user = usersService.findByUserNo(userNo);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userEmail", user.getUserEmail());
        userMap.put("userNickname", user.getUserNickname());
        userMap.put("authority", user.getAuthority());

        //refresh token ????????? ?????? ??? ??????????????? ????????? access token ??????
        String newAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken, userMap);
        HttpStatus status = null;

        //????????? access token ??? ???????????????
        if (newAccessToken != null) {

            //?????? access token ?????? ????????????
            Cookie cookie = new Cookie("accessToken", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);

            //????????? access token ????????? ??????
            Cookie newCookie = new Cookie("accessToken", newAccessToken);
            newCookie.setPath("/");
            newCookie.setHttpOnly(true);
            newCookie.setSecure(true);
            newCookie.setMaxAge(60 * 30);
            res.addCookie(newCookie);

            //?????? ?????? ????????? ?????????.
            result.put("userNo", user.getUserNo());
            result.put("userNickname", user.getUserNickname());
            result.put("userGrade", user.getUserGrade());

            //access token ????????? ?????????.
            result.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        }
        //access token??? ???????????? ???????????? refresh token??? ???????????? ?????? ???
        else {
            result.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<Map<String, Object>>(result, status);

    }

    //???????????? ?????? ????????? ??????
    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> getCookieToken(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", null);
        map.put("refreshToken", null);
        HttpStatus status = null;
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setHeader("Access-Control-Allow-Origin", "*");
        //token ???????????? map??? ????????????.
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
