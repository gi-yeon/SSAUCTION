package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersNameResponseDto {
    private String userNickname;

    @Builder
    UsersNameResponseDto(String userNickname){
        this.userNickname=userNickname;
    }
}
