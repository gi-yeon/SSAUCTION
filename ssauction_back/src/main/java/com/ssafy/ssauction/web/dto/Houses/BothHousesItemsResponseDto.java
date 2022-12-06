package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BothHousesItemsResponseDto {
    private ItemsResponseDto item;
    private List<byte[]> itemImgList;
    private HousesResponseDto house;

    @Builder
    public BothHousesItemsResponseDto(ItemsResponseDto item, List<byte[]> itemImgList,HousesResponseDto house){
        this.item=item;
        this.itemImgList=itemImgList;
        this.house=house;
    }
}
