package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.items.DealStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class HouseUpdateRequestDto {
    String houseTitle;
    Timestamp auctionTime;
    String itemName;
    String modelNo;
    DealStatus dealStatus;
    String desc;
    String describe;
    int startPrice;

    @Builder
    public HouseUpdateRequestDto(String houseTitle, Timestamp auctionTime, String itemName, String desc, String modelNo, DealStatus dealStatus, String describe, int startPrice) {
        this.houseTitle = houseTitle;
        this.auctionTime = auctionTime;
        this.itemName = itemName;
        this.modelNo = modelNo;
        this.dealStatus = dealStatus;
        this.describe = describe;
        this.startPrice = startPrice;
        this.desc=desc;
    }
}
