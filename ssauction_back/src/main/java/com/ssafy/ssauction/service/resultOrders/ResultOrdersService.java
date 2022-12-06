package com.ssafy.ssauction.service.resultOrders;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.resultOrders.ResultOrdersRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.web.dto.resultOrders.ResultOrdersSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ResultOrdersService {
    private final ResultOrdersRepository resultOrdersRepository;

    @Transactional
    public ResultOrders save(Users user, Houses house,ResultOrdersSaveDto saveDto){
        return resultOrdersRepository.save(saveDto.toEntity(user,house));
    }
}
