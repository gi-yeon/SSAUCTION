package com.ssafy.ssauction.web.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersInfoUpdateRequestDto {
    private String userNickname;
    private String userPhoneNo;
    private String userComment;
    private String userDesc;

    @Override
    public String toString() {
        return "UsersInfoUpdateRequestDto{" +
                "userNickname='" + userNickname + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", userComment='" + userComment + '\'' +
                ", userDesc='" + userDesc + '\'' +
                '}';
    }

    @Builder
    UsersInfoUpdateRequestDto(String userNickname, String userPhoneNo, String userComment, String userDesc) {
        this.userNickname = userNickname;
        this.userPhoneNo = userPhoneNo;
        this.userComment = userComment;
        this.userDesc = userDesc;
    }
}
