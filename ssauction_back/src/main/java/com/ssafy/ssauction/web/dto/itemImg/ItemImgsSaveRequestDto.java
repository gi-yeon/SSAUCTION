package com.ssafy.ssauction.web.dto.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
public class ItemImgsSaveRequestDto {
    private Long itemNo;
    private String itemImgName;
    private String itemImgUri;
    private Boolean isMain;
    @Builder
    public ItemImgsSaveRequestDto(Long itemNo,String itemImgName, Boolean isMain, String itemImgUri) {
        this.itemNo=itemNo;
        this.itemImgName=itemImgName;
        this.itemImgUri=itemImgUri;
        this.isMain=isMain;
    }

    @Override
    public String toString() {
        return "ItemImgsSaveRequestDto{" +
                "itemNo=" + itemNo +
                ", itemImgName='" + itemImgName + '\'' +
                ", itemImgUri='" + itemImgUri + '\'' +
                '}';
    }

    public ItemImgs toEntity(Items item){
        ItemImgs temp=ItemImgs.builder()
                .itemImgName(itemImgName)
                .itemImgUri(itemImgUri)
                .isMain(isMain)
                .item(item)
                .build();
        return temp;
    }
}
