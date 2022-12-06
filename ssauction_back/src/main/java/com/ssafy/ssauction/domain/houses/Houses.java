package com.ssafy.ssauction.domain.houses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Houses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_no")
    private Long houseNo;

    @Column(name = "house_title", length = 50, nullable = false)
    private String houseTitle;

    @Column(name = "house_auc_time", nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    private Timestamp houseAucTime;

    @Column(name="house_desc",length = 200)
    private String houseDesc;

    @Column(name="house_reg_time")
    private Timestamp houseRegTime;

    @Column(name="house_update_time")
    private Timestamp houseUpdateTime;

    @Setter
    @Column(name = "house_status",nullable = false)
    private int houseStatus;

    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @Setter
    @JoinColumn(name = "house_item_no")
    private Items item;

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "house",cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<ResultOrders>  results=new ArrayList<>();

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "house",cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private List<Likes>  likes=new ArrayList<>();

    @Builder
    public Houses(String houseTitle, Timestamp houseAucTime, String houseDesc, int houseStatus, Items item) {
        this.houseTitle = houseTitle;
        this.houseAucTime = houseAucTime;
        this.houseDesc = houseDesc;
        this.houseRegTime = new Timestamp(System.currentTimeMillis());
        this.houseStatus = houseStatus;
        this.item = item;
    }
}
