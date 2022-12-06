package com.ssafy.ssauction.web.dto.Items;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.DealStatus;
import com.ssafy.ssauction.domain.items.Items;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellItemResponseDto {
    private String houseTitle;
    private Long itemNo;
    private Long houseNo;
    private String itemName;
    private int startPrice;
    private String modelNo;
    private Timestamp regTime;
    private Timestamp auctionTime;
    private DealStatus dealStatus;
    private String desc;

    private List<Categories> ctgrList;

    @Builder
    public SellItemResponseDto(Items item, Houses house){
        this.itemNo=item.getItemNo();
        this.houseNo=house.getHouseNo();
        this.itemName=item.getItemName();
        this.startPrice=item.getItemStartPrice();
        this.modelNo=item.getItemModelNo();
        this.regTime=item.getItemRegTime();
        this.auctionTime=house.getHouseAucTime();
        this.dealStatus=item.getItemDealStatus();
        this.desc=item.getItemDesc();
        this.houseTitle=house.getHouseTitle();
        this.ctgrList=item.getCategories();
    }
}