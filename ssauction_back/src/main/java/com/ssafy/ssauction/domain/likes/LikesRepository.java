package com.ssafy.ssauction.domain.likes;

import com.ssafy.ssauction.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
}
