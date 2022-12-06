package com.ssafy.ssauction.web.dto.resultOrders;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultOrdersSaveDto {
    private Long userNo,houseNo;
    private int orderPrice;

    @Builder
    public ResultOrdersSaveDto(Long userNo, Long houseNo, int orderPrice){
        this.userNo=userNo;
        this.houseNo=houseNo;
        this.orderPrice=orderPrice;
    }

    public ResultOrders toEntity(Users bidder, Houses house){
       return ResultOrders.builder()
               .orderPrice(orderPrice)
               .bidder(bidder)
               .house(house)
               .build();
    }
}
