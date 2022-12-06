package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HousesItemsResponseDto {
    private ItemsResponseDto item;
    private List<ItemImgsResponseDto> itemImgList;
    private HousesResponseDto house;

    @Builder
    public HousesItemsResponseDto(ItemsResponseDto item, List<ItemImgsResponseDto> itemImgList, HousesResponseDto house) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.house = house;
    }
}
