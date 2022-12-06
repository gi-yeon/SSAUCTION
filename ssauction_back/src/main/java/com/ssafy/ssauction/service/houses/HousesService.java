package com.ssafy.ssauction.service.houses;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.houses.HousesRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.items.ItemsRepository;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.Houses.HouseUpdateRequestDto;
import com.ssafy.ssauction.web.dto.Houses.HousesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HousesService {
    private final HousesRepository housesRepository;
    private final ItemsRepository itemsRepository;
    public Houses save(Items item, HousesSaveRequestDto requestDto){
        Houses house=requestDto.toEntity(item);
        housesRepository.save(house);
        return house;
    }

    public Long delete(Long houseNo){
        housesRepository.deleteById(houseNo);
        return houseNo;
    }

    public Houses findEntityById(Long houseNo){
        return housesRepository.findById(houseNo).get();
    }

    public List<Houses> findEntityByHouseStatus(int houseStatus) {
        return housesRepository.findByHouseStatus(houseStatus);
    }

    public List<Houses> findEntityByHouseStatusAndHouseTitleContaining(int houseStatus, String search) {
        return housesRepository.findEntityByHouseStatusAndHouseTitleContaining(houseStatus, search);
    }

    @Transactional
    public void update(Houses house, HouseUpdateRequestDto houseDto) {
        house.setHouseTitle(houseDto.getHouseTitle());
        house.setHouseAucTime(houseDto.getAuctionTime());
    }
    @Transactional
    public void deleteHouseInProfile(Long houseNo) {
        Houses house=findEntityById(houseNo);
        Items item=house.getItem();
        housesRepository.delete(house);
        itemsRepository.delete(item);
    }
}
