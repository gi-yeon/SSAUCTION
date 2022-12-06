package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.service.userImages.UserImgsService;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserImgsTestController {
    private final UserImgsService userImgsService;

    @PostMapping("/users/imgs")
    public Long save(@RequestBody UserImgsSaveRequestDto requestDto){
        return 1L;
    }

    @GetMapping("/users/imgs/{userNo}")
    public UserImgs findById(@PathVariable Long userNo){
        return userImgsService.findEntityById(userNo);
    }
}
