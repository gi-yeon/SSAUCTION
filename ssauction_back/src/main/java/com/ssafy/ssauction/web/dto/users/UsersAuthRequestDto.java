package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersAuthRequestDto {

    private String userEmail;
    private String userPwd;


    @Override
    public String toString() {
        return "UsersAuthRequestDto{" +
                "userEmail='" + userEmail + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }

    @Builder
    public UsersAuthRequestDto(String userEmail, String userPwd) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
    }
}



