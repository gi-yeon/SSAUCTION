package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsSaveRequestDto;
import com.ssafy.ssauction.web.dto.userImages.UserImgsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ItemImgsController {
    private final ItemsService itemsService;
    private final ItemImgsService itemImgsService;

//    @PostMapping("/items/imgs")
//    public Long save(@RequestBody ItemImgsSaveRequestDto requestDto){
//        Items item=itemsService.findEntityById(requestDto.getItemNo());
//        ItemImgs img= itemImgsService.save(item,requestDto);
//        item.getImages().add(img);
//        return img.getImgNo();
//    }

}
