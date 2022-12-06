package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UsersLoginDto {
    String loginEmail;
    String loginPwd;

    @Setter
    Authority authority;

    @Override
    public String toString() {
        return "UsersLoginDto{" +
                "loginEmail='" + loginEmail + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", authority=" + authority +
                '}';
    }

    @Builder
    public UsersLoginDto(String loginEmail, String loginPwd) {
        this.loginEmail = loginEmail;
        this.loginPwd = loginPwd;
    }
}
