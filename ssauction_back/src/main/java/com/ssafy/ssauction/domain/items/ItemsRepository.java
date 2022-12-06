package com.ssafy.ssauction.domain.items;

import com.ssafy.ssauction.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Long> {
}
