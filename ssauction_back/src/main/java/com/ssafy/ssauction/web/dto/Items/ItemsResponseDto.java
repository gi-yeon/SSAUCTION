package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.DealStatus;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.DealStatus;
import com.ssafy.ssauction.domain.items.Quality;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemsResponseDto {
    private Long itemNo;
    private String itemName;
    private String itemModelNo;
    private Quality itemQuality;
    private DealStatus itemDealStatus;
    private String itemDesc;
    private int itemStartPrice;
    private int itemFinalPrice;
    private String sellerNickname;
    private List<Categories> ctgrList;
    private Long sellerNo;

    @Builder
    public ItemsResponseDto(Items item) {
        this.itemNo = item.getItemNo();
        this.itemName = item.getItemName();
        this.itemModelNo = item.getItemModelNo();
        this.itemDesc = item.getItemDesc();
        this.itemQuality = item.getItemQuality();
        this.itemDealStatus = item.getItemDealStatus();
        this.itemStartPrice = item.getItemStartPrice();
        this.itemFinalPrice = item.getItemFinalPrice();
        this.sellerNickname = item.getSeller().getUserNickname();
        this.sellerNo = item.getSeller().getUserNo();
        this.ctgrList=item.getCategories();
    }
}
