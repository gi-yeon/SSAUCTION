package com.ssafy.ssauction.domain.houses;

import com.ssafy.ssauction.web.dto.Houses.HousesItemsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HousesRepository extends JpaRepository<Houses,Long> {

    List<Houses> findByHouseStatus(int houseStatus);

    List<Houses> findEntityByHouseStatusAndHouseTitleContaining(int houseStatus, String houseTitle);
}
