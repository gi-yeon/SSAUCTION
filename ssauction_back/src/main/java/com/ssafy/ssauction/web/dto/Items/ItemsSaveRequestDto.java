package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.domain.items.DealStatus;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.Quality;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class ItemsSaveRequestDto {
    private String itemName;
    private String itemModelNo;
    private Quality itemQuality;
    private DealStatus itemDealStatus;
    private String itemDesc;
    private int itemStartPrice;
    private int itemFinalPrice;
    private Long userNo;

    @Builder
    public ItemsSaveRequestDto(Long userNo, String itemName, String itemModelNo, Quality itemQuality,
            DealStatus itemDealStatus, String itemDesc, int itemStartPrice, int itemFinalPrice) {
        this.itemName = itemName;
        this.itemModelNo = itemModelNo;
        this.itemQuality = itemQuality;
        this.itemDealStatus = DealStatus.SELL;
        this.itemDesc = itemDesc;
        this.itemStartPrice = itemStartPrice;
        this.itemFinalPrice = itemFinalPrice;
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "ItemsSaveRequestDto{" +
                "itemName='" + itemName + '\'' +
                ", itemModelNo='" + itemModelNo + '\'' +
                ", itemQuality=" + itemQuality +
                ", itemDealStatus=" + itemDealStatus +
                ", itemDesc='" + itemDesc + '\'' +
                ", itemStartPrice=" + itemStartPrice +
                ", itemFinalPrice=" + itemFinalPrice +
                ", userNo=" + userNo +
                '}';
    }

    public Items toEntity(Users seller) {
        return Items.builder()
                .itemName(itemName)
                .itemModelNo(itemModelNo)
                .itemQuality(itemQuality)
                .itemDealStatus(itemDealStatus)
                .itemStartPrice(itemStartPrice)
                .seller(seller)
                .itemDesc(itemDesc)
                .build();
    }
}
