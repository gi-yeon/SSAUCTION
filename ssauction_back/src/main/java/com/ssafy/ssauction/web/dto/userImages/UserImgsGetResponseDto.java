package com.ssafy.ssauction.web.dto.userImages;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.web.dto.users.UserInfoResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserImgsGetResponseDto {
    private UserInfoResponseDto infoDto;
    private byte[] userMainImg;
    @Builder
    public UserImgsGetResponseDto(UserInfoResponseDto infoDto,byte[] userMainImg){
        this.infoDto=infoDto;
        this.userMainImg=userMainImg;
    }
}