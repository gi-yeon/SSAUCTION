package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPlusImgSaveDto {
    private ItemsSaveRequestDto itemsSaveRequestDto;
    private ItemImgsSaveRequestDto itemImgsSaveRequestDto;
}
