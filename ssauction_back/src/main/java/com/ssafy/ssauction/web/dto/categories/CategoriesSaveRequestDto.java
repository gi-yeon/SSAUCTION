package com.ssafy.ssauction.web.dto.categories;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class CategoriesSaveRequestDto {
    private Items item;
    private String ctgrName;

    @Builder
    public CategoriesSaveRequestDto(Items item, String ctgrName) {
        this.item=item;
        this.ctgrName=ctgrName;
    }


    public Categories toEntity(Items item, String ctgrName){
        Categories temp=Categories.builder()
                .ctgrName(ctgrName)
                .item(item)
                .build();
        return temp;
    }
}
