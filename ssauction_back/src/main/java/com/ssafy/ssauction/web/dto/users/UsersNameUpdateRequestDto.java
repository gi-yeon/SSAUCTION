package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersNameUpdateRequestDto {
    private String userNickname;

    @Builder
    UsersNameUpdateRequestDto(String userNickname){
        this.userNickname=userNickname;
    }
}
