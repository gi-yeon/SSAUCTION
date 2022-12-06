package com.ssafy.ssauction.web.dto.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemImgsGetResponseDto {
    private Long itemImgNo;
    private String itemImgName;
    private String itemImgUri;

    @Builder
    public ItemImgsGetResponseDto(ItemImgs img) {
        this.itemImgNo=img.getImgNo();
        this.itemImgName=img.getItemImgName();
        this.itemImgUri=img.getItemImgUri();
    }
}
