package com.ssafy.ssauction.domain.userImages;

import com.ssafy.ssauction.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImgsRepository extends JpaRepository<UserImgs,Long> {
    Optional<UserImgs> findByUser(Users user);
}
