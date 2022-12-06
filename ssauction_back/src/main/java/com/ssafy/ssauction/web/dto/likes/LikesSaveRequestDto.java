package com.ssafy.ssauction.web.dto.likes;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesSaveRequestDto {
    private Long userNo,houseNo;

    @Builder
    public LikesSaveRequestDto(Long userNo, Long houseNo){
        this.userNo=userNo;
        this.houseNo=houseNo;
    }

    public Likes toEntity(Users user, Houses house){
        return Likes.builder()
                .user(user)
                .house(house)
                .build();
    }
}
