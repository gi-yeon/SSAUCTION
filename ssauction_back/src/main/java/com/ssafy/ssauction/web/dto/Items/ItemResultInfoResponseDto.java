package com.ssafy.ssauction.web.dto.Items;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.web.dto.itemImg.ImgInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ItemResultInfoResponseDto {
    private SellItemResponseDto item;
    private ImgInfo[] itemImgs;
    private ResultOrders resultOrders;
}
