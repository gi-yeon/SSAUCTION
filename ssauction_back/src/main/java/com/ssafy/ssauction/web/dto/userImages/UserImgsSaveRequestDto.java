package com.ssafy.ssauction.web.dto.userImages;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
public class UserImgsSaveRequestDto {

    private String userImgName;

    private String userImgUri;

    private Timestamp userImgRegDate;

    @Builder
    public UserImgsSaveRequestDto() {
        this.userImgRegDate = new Timestamp(System.currentTimeMillis());
    }

    public UserImgs toEntity(Users user){
        UserImgs temp=UserImgs.builder()
                .userImgName(null)
                .userImgUri(null)
                .userImgRegDate(null)
                .userImgUpdateDate(null)
                .user(user)
                .build();
        return temp;
    }
}
