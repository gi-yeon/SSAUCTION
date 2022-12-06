package com.ssafy.ssauction.web.dto.itemImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemImgsDeleteRequestDto {
    List<Long> indexs;
    @Builder
    public ItemImgsDeleteRequestDto(List<Long> list){
        indexs=list;
    }
}
