package com.ssafy.ssauction.web.dto.Sessions;

import com.ssafy.ssauction.web.dto.resultOrders.ResultOrdersSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SessionsInfoResponseDto {
    String host;
    ArrayDeque<ResultOrdersSaveDto> currentTop3;
}
