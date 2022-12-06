package com.ssafy.ssauction.service.Items;

import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.ItemsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.Houses.HouseUpdateRequestDto;
import com.ssafy.ssauction.web.dto.Items.ItemsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    public Items findEntityById(Long itemNo){
        return itemsRepository.findById(itemNo).get();
    }
    @Transactional
    public Items save(Users user, ItemsSaveRequestDto requestDto) {
        Items item=requestDto.toEntity(user);
        return itemsRepository.save(item);
    }

    @Transactional
    public void delete(Long itemNo) {
        itemsRepository.deleteById(itemNo);
    }

    @Transactional
    public void update(Items item, HouseUpdateRequestDto houseDto) {
        item.setItemDealStatus(houseDto.getDealStatus());
        item.setItemDesc(houseDto.getDesc());
        item.setItemModelNo(houseDto.getModelNo());
        item.setItemStartPrice(houseDto.getStartPrice());
        item.setItemName(houseDto.getItemName());
    }
}
