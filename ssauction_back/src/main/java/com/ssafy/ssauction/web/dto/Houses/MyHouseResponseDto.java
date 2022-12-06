package com.ssafy.ssauction.web.dto.Houses;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.Items.ItemInfoResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemResultInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyHouseResponseDto {
    private List<ItemInfoResponseDto> sellItemsList;
    private List<ItemResultInfoResponseDto> buyItemsList;
}
