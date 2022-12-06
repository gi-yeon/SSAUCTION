package com.ssafy.ssauction.web.dto.userImages;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserImgsUpdateRequestDto {
    private String imgName;
    private String imgUri;

    @Builder
    UserImgsUpdateRequestDto(String imgName, String imgUri){
        this.imgName=imgName;
        this.imgUri=imgUri;
    }
}
