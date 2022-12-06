package com.ssafy.ssauction.web.dto.itemImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemImgsSortRequestDto {
    List<Long> indexs;
    @Builder
    public ItemImgsSortRequestDto(List<Long> list){
        indexs=list;
    }
}
