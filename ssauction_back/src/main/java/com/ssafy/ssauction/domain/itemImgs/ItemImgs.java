package com.ssafy.ssauction.domain.itemImgs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class ItemImgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imgNo;

    @Column(name = "item_img_name", length = 50)
    private String itemImgName;

    @Column(name = "item_img_uri", length = 200)
    private String itemImgUri;

    @Setter
    @Column(name ="item_img_main", nullable = false)
    private Boolean isMain;
    @Column(name = "item_img_reg_date")
    private Timestamp itemImgRegDate;

    @Column(name = "item_img_update_date")
    private Timestamp itemImgUpdateDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_no")
    @Setter
    private Items item;
    public void updateMain(boolean isMain){
        this.isMain=isMain;
    }
    public void update(String itemImgName, String itemImgUri) {
        this.itemImgName = itemImgName;
        this.itemImgUri = itemImgUri;
        this.itemImgUpdateDate = new Timestamp(System.currentTimeMillis());
    }
    @Builder
    public ItemImgs(String itemImgName, String itemImgUri, boolean isMain,Items item) {
        this.itemImgName = itemImgName;
        this.itemImgUri = itemImgUri;
        this.isMain=isMain;
        this.item = item;
        this.itemImgRegDate = new Timestamp(System.currentTimeMillis());
        this.itemImgUpdateDate = itemImgUpdateDate;
    }
}
