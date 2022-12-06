package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Authority;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersAuthResponseDto {

    private Long userNo;
    private String userEmail;
    private String userPwd;

    private String userPhoneNo;

    private String userNickname;

    private String userDesc;

    private String userComment;

    private int userGrade;

    private String userRefreshToken;

    private Authority authority;

    public UsersAuthResponseDto(Users entity) {
        this.userNo=entity.getUserNo();
        this.userEmail = entity.getUserEmail();
        this.userPwd = entity.getUserPwd();
        this.userPhoneNo=entity.getUserPhoneNo();
        this.userNickname=entity.getUserNickname();
        this.userDesc=entity.getUserDesc();
        this.userComment=entity.getUserComment();
        this.userGrade=entity.getUserGrade();
        this.userRefreshToken = entity.getRefreshToken();
        this.authority = entity.getAuthority();
    }

}
