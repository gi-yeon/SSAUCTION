package com.ssafy.ssauction.web.dto.Houses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
public class HousesResponseDto {
    private Long houseNo;
    private String houseTitle;
    private Timestamp houseAucTime;
    private String houseDesc;
    private int houseStatus;

    @Builder
    public HousesResponseDto(Houses house) {
        this.houseNo = house.getHouseNo();
        this.houseTitle = house.getHouseTitle();
        this.houseAucTime = house.getHouseAucTime();
        this.houseDesc = house.getHouseDesc();
        this.houseStatus = house.getHouseStatus();
    }
}
