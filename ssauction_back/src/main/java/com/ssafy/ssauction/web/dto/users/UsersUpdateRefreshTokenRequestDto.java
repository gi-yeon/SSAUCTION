package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersUpdateRefreshTokenRequestDto {

    private String userRefreshToken;


    @Builder
    UsersUpdateRefreshTokenRequestDto(String userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }
}

