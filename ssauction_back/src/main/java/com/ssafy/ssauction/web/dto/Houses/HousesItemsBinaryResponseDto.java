package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.web.dto.Items.ItemInfoResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ImgInfo;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HousesItemsBinaryResponseDto {
    private ItemsResponseDto item;
    private List<ImgInfo> itemImgList;
    private HousesResponseDto house;

    @Builder
    public HousesItemsBinaryResponseDto(ItemsResponseDto item, List<ImgInfo> itemImgList, HousesResponseDto house) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.house = house;
    }
}
