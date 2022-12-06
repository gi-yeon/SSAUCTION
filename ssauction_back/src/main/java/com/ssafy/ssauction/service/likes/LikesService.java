package com.ssafy.ssauction.service.likes;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.likes.LikesRepository;
import com.ssafy.ssauction.domain.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public Likes save(Users user, Houses house){
        return likesRepository.save(Likes.builder()
                .user(user)
                .house(house)
                .build());
    }
}
