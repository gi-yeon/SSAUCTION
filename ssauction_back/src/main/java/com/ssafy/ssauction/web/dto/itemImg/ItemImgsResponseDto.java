package com.ssafy.ssauction.web.dto.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ItemImgsResponseDto {
    private Long itemImgsNo;
    private String itemImgName;
    private String itemImgUri;

    @Builder
    public ItemImgsResponseDto(ItemImgs itemImgs) {
        this.itemImgsNo = itemImgs.getImgNo();
        this.itemImgName = itemImgs.getItemImgName();
        this.itemImgUri = itemImgs.getItemImgUri();
    }

    @Override
    public String toString() {
        return "ItemImgsResponseDto{" +
                "itemImgsNo=" + itemImgsNo +
                ", itemImgName='" + itemImgName + '\'' +
                ", itemImgUri='" + itemImgUri + '\'' +
                '}';
    }
}
