package com.ssafy.ssauction.web.dto.categories;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
public class CategoriesLoadRequestDto {
    private String[] ctgrName;

    @Builder
    public CategoriesLoadRequestDto( String[] ctgrName) {
        this.ctgrName=ctgrName;
    }

    @Override
    public String toString() {
        return "CategoriesSaveRequestDto{" +
                ", ctgrName=" + Arrays.toString(ctgrName) +
                '}';
    }

    public Categories toEntity(Items item, String ctgrName){
        Categories temp=Categories.builder()
                .ctgrName(ctgrName)
                .item(item)
                .build();
        return temp;
    }
}
