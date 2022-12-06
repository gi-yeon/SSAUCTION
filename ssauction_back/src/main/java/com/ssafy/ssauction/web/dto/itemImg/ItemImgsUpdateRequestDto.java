package com.ssafy.ssauction.web.dto.itemImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemImgsUpdateRequestDto {
    private String imgName;
    private String imgUri;
    @Builder
    public ItemImgsUpdateRequestDto(String imgName, String imgUri){
        this.imgName=imgName;
        this.imgUri=imgUri;
    }
}
