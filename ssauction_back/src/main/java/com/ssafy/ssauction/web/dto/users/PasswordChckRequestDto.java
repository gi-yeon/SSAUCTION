package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChckRequestDto {

    private Long userNo;
    private String userPwd;


    @Override
    public String toString() {
        return "UsersAuthRequestDto{" +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }

    @Builder
    public PasswordChckRequestDto(Long userNo, String userPwd) {
        this.userNo = userNo;
        this.userPwd = userPwd;
    }
}
