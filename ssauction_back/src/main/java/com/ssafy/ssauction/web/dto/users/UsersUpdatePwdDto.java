package com.ssafy.ssauction.web.dto.users;

import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersUpdatePwdDto {
    private String userPwd;

    @Builder
    UsersUpdatePwdDto(String userPwd){
        this.userPwd = userPwd;
    }

    public Users toEntity(){
        return Users.builder()
                .userPwd(userPwd)
                .build();
    }
}
