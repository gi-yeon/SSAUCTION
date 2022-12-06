package com.ssafy.ssauction.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long likeNo;

    @Column(name = "like_reg_time")
    private Timestamp likeRegTime;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house")
    private Houses house;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user")
    private Users user;

    @Builder
    public Likes(Houses house, Users user){
        this.likeRegTime = new Timestamp(System.currentTimeMillis());
        this.house = house;
        this.user = user;
    }
}
