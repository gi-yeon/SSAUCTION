package com.ssafy.ssauction.service.itemImg;

import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.itemImgs.ItemImgsRepository;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsGetResponseDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsDeleteRequestDto;
import com.ssafy.ssauction.web.dto.itemImg.ItemImgsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemImgsService {
    private final ItemImgsRepository itemImgsRepository;

    @Transactional
    public ItemImgs save(Items item, String originalUri, String saveUri) {
        ItemImgs itemImg =
                ItemImgs.builder()
                        .itemImgName(originalUri)
                        .itemImgUri(saveUri)
                        .isMain(false)
                        .item(item)
                        .build();
        itemImg.setItem(item);
        return itemImgsRepository.save(itemImg);
    }
    public ItemImgs findById(Long imgNo){
        return itemImgsRepository.findById(imgNo).get();
    }
    @Transactional
    public boolean update(Long userNo, ItemImgsUpdateRequestDto requestDto){
        try {
            ItemImgs img = itemImgsRepository.findById(userNo).get();
            img.update(requestDto.getImgName(), requestDto.getImgUri());
            return true;
        }catch(NoSuchElementException | IllegalArgumentException e){
            return false;
        }
    }
    @Transactional
    public boolean delete(Items item, ItemImgsDeleteRequestDto requestDto) {
        List<Long> deleteList = requestDto.getIndexs();
        System.out.println(deleteList);
        for (int j = deleteList.size() - 1; j >= 0; j--) {
            ItemImgs img = itemImgsRepository.findById(deleteList.get(j)).get();
            String path = System.getProperty("user.dir") + "/imgs/item/" + img.getItemImgUri();
            File deleteFile = new File(path);
            deleteFile.delete();
            itemImgsRepository.delete(img);
        }
        return true;
    }

    public ItemImgsGetResponseDto getImgs(Items item) {
        return ItemImgsGetResponseDto.builder().img(itemImgsRepository.findByItem(item).get()).build();
    }

    @Transactional
    public void updateMain(ItemImgs img, boolean b) {
        img.updateMain(b);
    }
}
