package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.categories.Categories;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.itemImgs.ItemImgs;
import com.ssafy.ssauction.domain.items.DealStatus;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.resultOrders.ResultOrders;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.service.Items.ItemsService;
import com.ssafy.ssauction.service.categories.CategoriesService;
import com.ssafy.ssauction.service.houses.HousesService;
import com.ssafy.ssauction.service.itemImg.ItemImgsService;
import com.ssafy.ssauction.service.storage.StorageService;
import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.Houses.*;
import com.ssafy.ssauction.web.dto.Items.*;
import com.ssafy.ssauction.web.dto.categories.CategoriesLoadRequestDto;
import com.ssafy.ssauction.web.dto.itemImg.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/houses")
public class HousesController {
    private final UsersService usersService;
    private final HousesService housesService;
    private final ItemsService itemsService;

    private final ItemImgsService itemImgsService;
    private final StorageService storageService;
    private final CategoriesService categoriesService;
    @GetMapping("/profile/{userNo}")
    public ResponseEntity<MyHouseResponseDto> myAllHouses(@PathVariable Long userNo) {
        Users user = usersService.findEntityById(userNo);                                       //유저 정보 가져오기
        List<ItemInfoResponseDto> sellList = new ArrayList<>();                                 //판매중인 항목들 가져오기
        for (Items item : user.getSellItems()) {
            int imgSize = item.getImages().size();
            ImgInfo[] imgs=new ImgInfo[imgSize];
            for (int i = 0; i < imgSize; i++) {
                ItemImgs img = item.getImages().get(i);
                String uri = img.getItemImgUri();                              //로컬에 저장되어있는 이미지 가져오기
                byte[] transform = null;                                                              //바이트 코드로 이미지 부르기
                try {
                    File file = new File(System.getProperty("user.dir") + "/imgs/item/" + uri);  //이미지 파일 가져오기
                    FileInputStream inputStream = new FileInputStream(file);                          //파일을 바이트 값으로 저장하기 위한 스트림 생성
                    transform = new byte[(int) file.length()];                                         //바이트 배열 생성
                    inputStream.read(transform);                                                    //바이트 값으로 변환
                    inputStream.close();
                } catch (Exception e) {
                    return null;
                }
                if (transform != null) {
                    imgs[i]=ImgInfo.builder().img(transform.clone()).imgNo(img.getImgNo()).isMain(img.getIsMain()).build();
                }
            }
            boolean isNull = false;
            for (int i = 0; i < imgSize; i++) {
                if (imgs[i] == null)
                    isNull = true;
            }
            if (!isNull)
                sellList.add(new ItemInfoResponseDto(SellItemResponseDto.builder().item(item).house(item.getHouse()).build(),
                        imgs));                                                            //리스트에 판매 중인 아이템 저장
        }

        //구매 중인 아이템 정보 가져오는 것도 판매랑 같음
        List<ItemResultInfoResponseDto> buyList = new ArrayList<>();
        List<ResultOrders> resultOrdersList = user.getResults();
        for (ResultOrders result : resultOrdersList) {
            Items item = result.getHouse().getItem();
            int imgSize = item.getImages().size();
            ImgInfo[] imgs=new ImgInfo[imgSize];
            for (int i = 0; i < imgSize; i++) {
                ItemImgs img = item.getImages().get(i);
                String uri = img.getItemImgUri();
                byte[] transform = null;
                try {
                    File file = new File(System.getProperty("user.dir") + "/imgs/item/" + uri);
                    FileInputStream inputStream = new FileInputStream(file);
                    transform = new byte[(int) file.length()];
                    inputStream.read(transform);
                    inputStream.close();
                } catch (Exception e) {
                    return null;
                }
                if (transform != null) {
                    imgs[i] = ImgInfo.builder().img(transform.clone()).imgNo(img.getImgNo()).isMain(img.getIsMain()).build();
                }
            }
            boolean isNull = false;
            for (int i = 0; i < imgSize; i++) {
                if (imgs[i] == null)
                    isNull = true;
            }
            if (!isNull)
                buyList.add(new ItemResultInfoResponseDto(SellItemResponseDto.builder().item(item).house(item.getHouse()).build(),
                        imgs, result));
        }

        MyHouseResponseDto resDto = new MyHouseResponseDto(sellList, buyList);                  //보낼 데이터를 Dto로 감싸주기
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(resDto);        //보내기
    }


    @DeleteMapping("/{houseNo}")
    public ResponseEntity<String> deleteHouse(@PathVariable Long houseNo){
        housesService.deleteHouseInProfile(houseNo);
        return new ResponseEntity<>("String",HttpStatus.OK);
    }
    @PutMapping("/update/{houseNo}")
    public ResponseEntity<String> updateHouse(@PathVariable Long houseNo,
                                              @RequestPart(value = "houseUpdateDto") HouseUpdateRequestDto houseDto,
                                              @RequestPart(value = "ctgrDto") CategoriesLoadRequestDto ctgrDto,
                                              @RequestPart(value = "deleteDto") ItemImgsDeleteRequestDto deleteDto,
                                              @RequestPart(value = "files") MultipartFile[] files,
                                              @RequestPart(value="sortDto") ItemImgsSortRequestDto sortDto)
    {
        boolean isSuccess = true;
        Houses house = housesService.findEntityById(houseNo);
        Items item = house.getItem();

        itemImgsService.delete(item, deleteDto);
        System.out.println("sortDto : "+sortDto.getIndexs().toString());

        for(int i=0;i<sortDto.getIndexs().size();i++){
            ItemImgs img = itemImgsService.findById(sortDto.getIndexs().get(i));
            itemImgsService.updateMain(img, i==0?true:false);
            System.out.println(""+i+" "+img.getImgNo()+" "+img.getIsMain());
        }
        for (MultipartFile file : files) {
            if(file.getContentType().equals("text/plain")){
                break;
            }
            // FileUpload 관련 설정
            if (file != null && !file.isEmpty()) {                          //  file 데이터가 유효하다면,
                System.out.println(file.getName());
                System.out.println(file.getContentType());
                String originalFileName = file.getOriginalFilename();           //          원본 파일 이름을 알아둔다.
                if (!originalFileName.isEmpty()) {                              //          원본 파일 이름이 유효하다면,
                    String saveFileName = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
                            + originalFileName                                  //              원본 파일 이름을 합친다.
                            .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
                    storageService.store(file, saveFileName, "item");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
                    ItemImgs img = itemImgsService.save(item, originalFileName, saveFileName);   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
                    item.getImages().add(img);
                }
            } else {
                isSuccess = false;
            }
        }
        int size=item.getCategories().size();
        for(int i=size-1;i>=0;i--){
            System.out.println(item.getCategories().get(i).getCtgrNo());
            categoriesService.deleteAllbyItem(i,item);
        }
        for(String str:ctgrDto.getCtgrName()){
            categoriesService.save(item,str);
        }
        itemsService.update(item, houseDto);
        housesService.update(house, houseDto);
        return isSuccess ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    // swagger 2.x 버전에서는 여러 개의 파일을 한 번에 전송하는 요청을 지원하지 않는다ㅠㅠ
    @PostMapping
    public ResponseEntity<Long> createHouse(
            @RequestPart(value = "itemDto") ItemsSaveRequestDto itemDto,          //  House.vue의 item 관련 정보를 받는 객체
            @RequestPart(value = "houseDto") HousesSaveRequestDto houseDto,       //  House.vue의 house 관련 정보를 받는 객체
            @RequestPart(value = "ctgrDto") CategoriesLoadRequestDto ctgrDto,
            @RequestPart(value = "files") MultipartFile[] files) {                //  House.vue의 files를 받는 배열
        System.out.println(itemDto);
        Users user = usersService.findEntityById(itemDto.getUserNo());          //  itemDto에서 현재 사용자의 UserNo를 통해 현재 user를 찾는다.
        //      나중에 JWT 인증 정보로 대체해야 한다.
        System.out.println(itemDto.toString());
        Items item = itemsService.save(user, itemDto);                          //  item에 유저 정보와 item 정보를 등록한다.
        user.getSellItems().add(item);                                          //  user.SellItems에도 해당 item의 정보를 추가한다.
        for(String str:ctgrDto.getCtgrName()){   // for문을 이용해 복수의 카테고리를 하나씩 저장해주기
            System.out.println(str);
            Categories categories = categoriesService.save(item, str);
            item.getCategories().add(categories);
        }
        Houses house = housesService.save(item, houseDto);                      //  house에 item 정보와 house 정보를 등록한다.
        item.setHouse(house);                                                   //  item.house에도 해당 house의 정보를 추가한다.

        boolean isFirst=true;
        // FileUpload 관련 설정
        if (files[0] != null && !files[0].isEmpty()) {                          //  file 데이터가 유효하다면,
            for (MultipartFile file : files) {//      전송받은 file마다,
                String originalFileName = file.getOriginalFilename();           //          원본 파일 이름을 알아둔다.
                if (!originalFileName.isEmpty()) {                              //          원본 파일 이름이 유효하다면,
                    String saveFileUri = UUID.randomUUID().toString()          //              저장용 구분자를 생성한다.
                            + originalFileName                                  //              원본 파일 이름을 합친다.
                            .substring(originalFileName.lastIndexOf('.'));  //              원본 파일 확장자를 합친다.
                    storageService.store(file, saveFileUri, "item");       //              위와 같이 생성된 이름으로 된 파일을 생성해 요청받은 file을 저장한다.
                    ItemImgs img = itemImgsService.save(item, originalFileName, saveFileUri);   //  itemImgsService를 통해 DB에 ItemImg 정보를 저장한다.
                    if(isFirst){
                        img.setIsMain(true);
                    }
                    item.getImages().add(img);
                }
                isFirst=false;
            }
        }

        return new ResponseEntity<>(item.getItemNo(), HttpStatus.OK);
    }

    @GetMapping("/searchAll")
    public ResponseEntity<List<HousesItemsBinaryResponseDto>> searchAll(@RequestParam("page") int page,
                                                                                    @RequestParam("size") int size,
                                                                                    @RequestParam(value = "search", required = false) String search,
                                                                                    @RequestParam(value="houseStatus", required=false) int houseStatus) {
        System.out.println("search : " + search + " houseStatus : " + houseStatus);
        if (search == null || search.length() == 0) {
            search = "";
        }
        List<HousesItemsBinaryResponseDto> result = new ArrayList<>();
        List<Houses> housesList = housesService.findEntityByHouseStatusAndHouseTitleContaining(houseStatus, search);
        for (Houses house : housesList) {
            HousesResponseDto housesResponseDto = new HousesResponseDto(house);
            Items item = house.getItem();
            ItemsResponseDto itemsResponseDto = new ItemsResponseDto(item);
            List<ItemImgs> itemImgsList = item.getImages();
            List<ImgInfo> itemImgsBinaryList = new ArrayList<>();
            for (ItemImgs ii : itemImgsList) {
                String uri = ii.getItemImgUri();
                byte[] transform = null;

                try {
                    File file = new File(System.getProperty("user.dir") + "/imgs/item/" + uri);  //이미지 파일 가져오기
                    FileInputStream inputStream = new FileInputStream(file);                          //파일을 바이트 값으로 저장하기 위한 스트림 생성
                    transform = new byte[(int) file.length()];                                         //바이트 배열 생성
                    inputStream.read(transform);                                                    //바이트 값으로 변환
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                if (transform != null) {
                    itemImgsBinaryList.add(ImgInfo.builder().img(transform.clone()).imgNo(ii.getImgNo()).build());
                }
            }
            result.add(new HousesItemsBinaryResponseDto(itemsResponseDto, itemImgsBinaryList, housesResponseDto));
        }
        System.out.println(result.size());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{itemNo}")
    public ResponseEntity<HousesItemsResponseDto> search(@PathVariable Long itemNo) {
        Items item = itemsService.findEntityById(itemNo);
        ItemsResponseDto ir = new ItemsResponseDto(item);
        List<ItemImgs> itemImgs = item.getImages();
        List<ItemImgsResponseDto> iir = new ArrayList<ItemImgsResponseDto>();
        if (itemImgs.size() != 0) {
            for (ItemImgs ii : itemImgs) {
                iir.add(new ItemImgsResponseDto(ii));
            }
        }
        HousesResponseDto hr = new HousesResponseDto(item.getHouse());
        HousesItemsResponseDto result = new HousesItemsResponseDto(ir, iir, hr);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @PutMapping("{houseNo}")
//    public ResponseEntity<HousesItemsResponseDto> modify(@PathVariable Long houseNo, @RequestBody HousesItemsSaveRequestDto requestDto) {
//        Houses house = housesService.modify(houseNo, requestDto.getHouseDto());
//        Items item = itemsService.modify(house.getItem().getItemNo(), requestDto.getItemDto());
//        List<ItemImgsSaveRequestDto> iir = requestDto.getImgDtoList();
//        List<ItemImgs> itemImgs = new ArrayList<>();
//        for (ItemImgsSaveRequestDto r : iir) {
//            itemImgs.add(r.toEntity(item));
//            item.getImages().add(itemImgs);
//        }
//        HousesItemsResponseDto result = new HousesItemsResponseDto(new ItemsResponseDto(item), );
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
