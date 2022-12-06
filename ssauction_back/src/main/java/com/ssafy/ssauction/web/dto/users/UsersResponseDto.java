package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Authority;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersResponseDto {
    private Long userNo;

    private String userNickname;

    private String userDesc;

    private String userComment;

    private Integer userGrade;

    private Authority authority;
    @Override
    public String toString() {
        return "UsersResponseDto{" +
                "userNo=" + userNo +
                ", userNickname='" + userNickname + '\'' +
                ", userGrade=" + userGrade +
                '}';
    }

    @Builder
    public UsersResponseDto(Users entity){
        this.userNo=entity.getUserNo();
        this.userNickname=entity.getUserNickname();
        this.userGrade=entity.getUserGrade();
        this.authority=entity.getAuthority();
    }
}
