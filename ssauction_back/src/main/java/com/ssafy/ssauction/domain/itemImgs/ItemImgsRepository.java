package com.ssafy.ssauction.domain.itemImgs;

import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemImgsRepository extends JpaRepository<ItemImgs,Long> {
    Optional<ItemImgs> findByItem(Items item);
}
