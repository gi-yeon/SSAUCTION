package com.ssafy.ssauction.service.userImages;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.userImages.UserImgsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserImgsService {
    private final UserImgsRepository userImgsRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(Users user) {
        UserImgsSaveRequestDto requestDto = UserImgsSaveRequestDto.builder().build();
        return userImgsRepository.save(requestDto.toEntity(user)).getUser().getUserNo();
    }

    @Transactional
    public boolean update(Long userNo, UserImgsUpdateRequestDto requestDto){
        try {
            UserImgs img = userImgsRepository.findById(userNo).get();
            img.update(requestDto.getImgName(), requestDto.getImgUri());
            return true;
        }catch(NoSuchElementException|IllegalArgumentException e){
            return false;
        }
    }

    public UserImgs findEntityById(Long userNo) {
        return userImgsRepository.findById(userNo).get();
    }
}
