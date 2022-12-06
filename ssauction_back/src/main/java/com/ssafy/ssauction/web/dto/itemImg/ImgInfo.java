package com.ssafy.ssauction.web.dto.itemImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImgInfo {
    private byte[] img;
    private Long imgNo;
    private boolean isMain;
    @Builder
    public ImgInfo(Long imgNo,byte[] img, boolean isMain){
        this.imgNo=imgNo;
        this.img=img;
        this.isMain=isMain;
    }
}
