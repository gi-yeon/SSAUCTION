package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.likes.Likes;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.likes.LikesService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.BothHousesItemsResponseDto;
import com.ssafy.ssauction.web.dto.Houses.HousesResponseDto;
import com.ssafy.ssauction.web.dto.Houses.MyHouseResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemInfoResponseDto;
import com.ssafy.ssauction.web.dto.Items.ItemsResponseDto;
import com.ssafy.ssauction.web.dto.Items.SellItemResponseDto;
import com.ssafy.ssauction.web.dto.likes.LikesAllResponseDto;
import com.ssafy.ssauction.web.dto.likes.LikesSaveRequestDto;
import com.ssafy.ssauction.web.dto.resultOrders.ResultOrdersSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikesController {
    private final UsersService usersService;
    private final HousesService housesService;
    private final LikesService likesService;

    @GetMapping("/{sellerNo}")
    public ResponseEntity<List<BothHousesItemsResponseDto>> getLikes(@PathVariable Long sellerNo) {
        System.out.println("1 here is myallhouses");
        Users user = usersService.findEntityById(sellerNo);
        List<BothHousesItemsResponseDto> likesList = new ArrayList<>();
        for (Likes like : user.getLikes()) {
            Items item = like.getHouse().getItem();

            HousesResponseDto hr = HousesResponseDto.builder()
                    .house(like.getHouse())
                    .build();
            ItemsResponseDto ir = ItemsResponseDto.builder()
                    .item(item)
                    .build();

            List<ItemImgs> itemImgs = item.getImages();
            List<byte[]> imgSources=new ArrayList<>();

            for(ItemImgs img:itemImgs){
                String uri=img.getItemImgUri();
                byte[] transform = null;
                try {
                    File file = new File(System.getProperty("user.dir") + "/imgs/item/" + uri);
                    FileInputStream inputStream = new FileInputStream(file);
                    transform = new byte[(int) file.length()];
                    inputStream.read(transform);
                    inputStream.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
                if (transform != null)
                    imgSources.add(transform);
            }
            likesList.add(new BothHousesItemsResponseDto(ir,imgSources,hr));
        }
        if (likesList == null)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(likesList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> saveLikeHouses(@RequestBody LikesSaveRequestDto requestDto) {
        Users user = usersService.findEntityById(requestDto.getUserNo());
        Houses house = housesService.findEntityById(requestDto.getHouseNo());
        Likes like = likesService.save(user, house);
        if (like == null)
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        user.getLikes().add(like);
        house.getLikes().add(like);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
//    @GetMapping("/{userNo}")
//    public ResponseEntity<LikesAllResponseDto> getLikeHouses(@PathVariable Long userNo){
//        Users user=usersService.findEntityById(userNo);
//
//    }
}
