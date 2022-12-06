package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Authority;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UsersSaveRequestDto {
    private String userEmail;

    private String userPwd;

    private String userPhoneNo;

    private String userNickname;
    private Authority authority;


    @Builder
    public UsersSaveRequestDto(String userEmail, String userPwd, String userPhoneNo, String userNickname) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userPhoneNo = userPhoneNo;
        this.userNickname = userNickname;
        this.authority=Authority.ROLE_USER;
    }

    @Override
    public String toString() {
        return "UsersSaveRequestDto{" +
                "userEmail='" + userEmail + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", authority=" + authority +
                '}';
    }

    public Users toEntity(){
        return Users.builder()
                .userEmail(userEmail)
                .userPwd(userPwd)
                .userPhoneNo(userPhoneNo)
                .userNickname(userNickname)
                .authority(authority)
                .userGrade(4)
                .build();
    }
}
