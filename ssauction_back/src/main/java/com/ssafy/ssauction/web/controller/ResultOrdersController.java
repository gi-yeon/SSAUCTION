package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.resultOrders.ResultOrdersService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.resultOrders.ResultOrdersSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ResultOrdersController {
    private final UsersService usersService;
    private final HousesService housesService;
    private final ResultOrdersService resultOrdersService;

    @PostMapping("/results")
    public Long save(@RequestBody ResultOrdersSaveDto requestDto){
        Users user =usersService.findEntityById(requestDto.getUserNo());
        Houses house=housesService.findEntityById(requestDto.getHouseNo());
        ResultOrders resultOrders=resultOrdersService.save(user,house,requestDto);
        user.getResults().add(resultOrders);
        house.getResults().add(resultOrders);
        return resultOrders.getOrderNo();
    }

}
