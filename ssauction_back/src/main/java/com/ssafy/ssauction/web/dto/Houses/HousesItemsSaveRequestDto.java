package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;

import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HousesItemsSaveRequestDto {

    private HousesSaveRequestDto houseDto;
    private ItemsSaveRequestDto itemDto;
    private List<ItemImgsSaveRequestDto> imgDtoList;
}
