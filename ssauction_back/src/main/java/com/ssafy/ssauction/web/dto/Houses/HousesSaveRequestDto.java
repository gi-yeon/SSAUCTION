package com.ssafy.ssauction.web.dto.Houses;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class HousesSaveRequestDto {
    private String houseTitle;
    private Timestamp houseAucTime;
    private int houseStatus;
    private String houseDesc;
    private Long itemNo;

    @Builder
    public HousesSaveRequestDto(String houseTitle, Timestamp houseAucTime, int houseStatus, String houseDesc, Long itemNo) {
        this.houseTitle = houseTitle;
        this.houseAucTime=houseAucTime;
        this.houseStatus = houseStatus;
        this.houseDesc=houseDesc;
        this.itemNo = itemNo;
    }


    public Houses toEntity(Items item) {
        return Houses.builder()
                .houseAucTime(houseAucTime)
                .houseTitle(houseTitle)
                .houseStatus(houseStatus)
                .houseDesc(houseDesc)
                .item(item)
                .build();
    }
}
