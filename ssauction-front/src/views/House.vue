<template>
  <div>
    <br />
    <h1>경매방 만들기</h1>
    <br />
    <br />
    <div class="row">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">경매방 이름</h4>
      </div>
      <div class="col-2">
        <input
          id="houseTitle"
          v-model="house.houseTitle"
          type="text"
          required
          class="input_style"
          style="margin-bottom: 0.5rem"
          label="경매방 이름"
        />
      </div>
    </div>
    <div class="date">
      <div class="row house-input">
        <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
          <h4 align="right">경매 일시</h4>
        </div>
        <div class="col-6 data">
          <div class="row house-input">
            <el-date-picker
              v-model="house.houseAucTime"
              type="datetime"
              placeholder="Pick a Date"
              format="YYYY/MM/DD hh:mm:ss"
              value-format="YYYY-MM-DD hh:mm:ss"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">매물 이름</h4>
      </div>
      <div class="col-8 data">
        <input
          v-model="item.itemName"
          class="form-control"
          type="text"
          required
          style="margin-bottom: 0.5rem"
        />
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">모델 번호</h4>
      </div>
      <div class="col-8 data">
        <input
          v-model="item.itemModelNo"
          class="form-control"
          type="text"
          required
          style="margin-bottom: 0.5rem"
        />
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">카테고리</h4>
      </div>

      <div class="col-8 data align-left">
        <div class="box">
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr1"
              value="전자기기"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr1">전자기기</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr2"
              value="생활가전"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr2">생활가전</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr3"
              value="가구/인테리어"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr3">가구/인테리어</label>
          </div>
          <br />
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr4"
              value="생활/주방"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr4">생활/주방</label>
          </div>

          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr5"
              value="여성명품"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr5">여성명품</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr6"
              value="여성의류/신발"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr6">여성의류/신발</label>
          </div>
          <br />
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr7"
              value="여성잡화"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr7">여성잡화</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr8"
              value="뷰티/미용"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr8">뷰티/미용</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr9"
              value="남성명품"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr9">남성명품</label>
          </div>
          <br />
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr10"
              value="남성의류/신발"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr10">남성의류/신발</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr11"
              value="남성잡화"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr11">남성잡화</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr12"
              value="스포츠/레저"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr12">스포츠/레저</label>
          </div>
          <br />
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr13"
              value="취미/게임/음반"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr13">취미/게임/음반</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr14"
              value="도서"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr14">도서</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr15"
              value="티켓/입장권"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr15">티켓/입장권</label>
          </div>
          <br />
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr16"
              value="상품권/교환권"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr16">상품권/교환권</label>
          </div>

          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr17"
              value="유아동"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr17">유아동</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr18"
              value="가공식품"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr18">가공식품</label>
          </div>
          <br />
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr19"
              value="반려동물용품"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr19">반려동물용품</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr20"
              value="식물"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr20">식물</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="ctgr21"
              value="기타물품"
              v-model="ctgr.ctgrName"
            />
            <label class="form-check-label" for="ctgr21">기타물품</label>
          </div>
        </div>
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">매물상태</h4>
      </div>
      <div class="col-8 data">
        <!-- <input
          v-model="item.itemQuality"
          class="form-control"
          type="text"
          style="margin-bottom: 0.5rem"
        /> -->
        <div class="form-group">
          <select
            v-model="item.itemQuality"
            class="form-control"
            type="text"
            style="margin-bottom: 0.5rem"
            id="exampleFormControlSelect1"
          >
            <option>S</option>
            <option>A</option>
            <option>B</option>
            <option>C</option>
          </select>
        </div>
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">매물설명</h4>
      </div>
      <div class="col-8 data">
        <textarea
          v-model="item.itemDesc"
          class="form-control"
          type="text"
          style="margin-bottom: 0.5rem"
        ></textarea>
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">시작가격</h4>
      </div>
      <div class="col-8 data">
        <input
          v-model="item.itemStartPrice"
          class="form-control"
          type="number"
          required
          style="margin-bottom: 0.5rem"
        />
      </div>
    </div>
    <div class="row house-input">
      <div class="col-4 label" style="margin-top: auto; margin-bottom: auto">
        <h4 align="right">사진</h4>
      </div>
      <div class="col-2 data">
        <div class="mb-3">
          <input
            type="file"
            ref="itemImage"
            @change="imageUpload"
            multiple
            style="margin-bottom: 0.5rem"
          />
        </div>
      </div>
    </div>
    <div class="row" style="height: 320px">
      <div class="col-4"></div>
      <div class="text-start preview col-8">
        <item-image-preview
          v-for="(file, index) in itemImages"
          :key="file.lastModified"
          :previewfile="file"
          :index="index"
          @delete-img="deleteImg"
          style="margin-bottom: 0.5rem; margin-left: 0.5rem"
        />
      </div>
    </div>
    <div class="row justify-content-center house-input p-5">
      <button class="btn_pink2" @click="createHouse">경매방 생성</button>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import ItemImagePreview from "@/components/ItemImagePreview.vue";
export default {
  name: "SsauctionHouse",
  components: { ItemImagePreview },
  data() {
    return {
      item: {
        itemName: null,
        itemModelNo: null,
        itemQuality: null,
        itemStartPrice: null,
        itemDesc: null,
        itemDealStatus: "SELL",
        userNo: this.$store.getters["user/userNo"], // 유저정보를 현재 로그인 된 유저로 설정
        // userNo: 1, // 유저정보를 현재 로그인 된 유저가 아닌 임시로 1번유저로 지정
      },
      ctgr: {
        ctgrName: [],
      },
      house: {
        houseTitle: null,
        houseAucTime: null,
        houseStatus: 0,
      },
      houseDate: new Date(),
      modelConfig: {
        type: "string",
        mask: "YYYY-MM-DDThh:mm:ss",
      },
      itemImages: [],
    };
  },

  mounted() {},

  methods: {
    imageUpload() {
      // $refs를 통해 DOM에 있는 input의 files에 직접 접근하면 FileList 객체가 반환된다.
      // FileList 객체는 read only이므로 다루기 어렵다.
      // 아래의 코드를 통해 this.itemImages 배열에 files에 있는 File 객체를 그대로 복사한다.
      Array.prototype.push.apply(this.itemImages, this.$refs.itemImage.files);
      console.log(this.itemImages);
      this.$refs.itemImage.value = "";
    },
    createHouse() {
      // this.house.houseAucTime = this.houseAucTime;
      console.log(this.house);
      console.log(this.item);
      console.log("ctgr");
      console.log(this.ctgr);
      this.house.houseAucTime =
        this.house.houseAucTime.split(" ")[0] +
        "T" +
        this.house.houseAucTime.split(" ")[1];
      // file은 multipart/form-data 형식으로 전송되어야 한다.
      // multipart/form-data 형식으로 요청하면 JSON을 바로 body에 넣는 방식을 사용할 수 없다.
      // 따라서 json Blob 객체로 만들어 파일 형식으로 전달한다.
      const housejson = JSON.stringify(this.house);
      const itemjson = JSON.stringify(this.item);
      const ctgrjson = JSON.stringify(this.ctgr);

      console.log(itemjson);

      const houseblob = new Blob([housejson], { type: "application/json" });
      const itemblob = new Blob([itemjson], { type: "application/json" });
      const ctgrblob = new Blob([ctgrjson], { type: "application/json" });

      // html의 form 태그를 이용해 submit하면 formData 객체와 multipart/form-data 형식으로 전달된다.
      // form 태그를 이용하고 있지 않지만 이용한 것처럼 요청하기 위해 새 formData 객체를 만든다.
      let formData = new FormData();
      formData.append("itemDto", itemblob);
      formData.append("houseDto", houseblob);
      formData.append("ctgrDto", ctgrblob);
      // Spring에서 여러 file을 자동으로 배열로 받기 위해서는 Json 리스트를 그대로 전달하면 안된다.
      // 같은 이름을 가진 여러 개의 file을 전송한다.
      for (let img of this.itemImages) {
        formData.append("files", img);
      }

      axios
        .post("/houses", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })

        .then(({ data }) => {
          console.log(data);
          this.itemNo = data;
          console.log(this.itemNo);
          this.itemNo = data.item_no;
          console.log(this.itemNo);
          // this.sendFile();
          this.itemImages.splice(0);
          alert("생성 완료");

          this.$router.push({ name: "Home" });
        })
        .catch((error) => {
          alert(error);
        });
    },

    deleteImg(index) {
      this.itemImages.splice(index, 1);
      console.log(this.$refs.itemImage);
    },
  },
};
</script>

<style scoped>
label {
  text-align: Left;
  display: inline-block;
  width: 140px;
  line-height: 30px;
}
.preview {
  overflow-x: auto;
  white-space: nowrap;
}

.form-check {
  margin-top: 0.5rem;
  margin-right: 0.5rem;
  font-size: 1rem;
}
.form-check-input {
  margin-top: 0;
  margin-right: 0.5rem;
  height: 1.8rem;
  /* background-color: rgb(255, 211, 182); */
  border: solid grey;
  background-color: white;
  width: 30px;
  height: 30px;
}

input {
  width: 630px;
  height: 50px;
  border: 0;
  background-color: rgb(230, 230, 230);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left: 30px;
}

select {
  width: 630px;
  height: 50px;
  border: 0;
  background-color: rgb(230, 230, 230);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left: 30px;
}

.btn_pink2 {
  width: 150px;
  height: 40px;
  border: 0;
  background-color: rgba(255, 169, 165, 0.7);
  border-radius: 10px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.btn_yellow {
  width: 70px;
  height: 40px;
  border: 0;
  background-color: rgb(255, 211, 182, 0.7);
  border-radius: 10px;
  color: rgb(94, 94, 94);
  text-align: center;
}

textarea {
  outline: none;
  resize: none;
  padding: 20px;
  background-color: rgba(158, 158, 158, 0.212);
  border: none;
  border-radius: 10px;
  width: 630px;
}

textarea:focus {
  outline: none;
}

.box {
  margin-left: 0px;
  margin-top: 10px;
  margin-bottom: 20px;
  margin-right: 10px;
  padding: 20px;
  border: 0px solid grey;
  border-radius: 5px;
  width: 650px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

.preview {
  overflow-x: auto;
  white-space: nowrap;
}

.date {
  margin-top: 5px;
  margin-bottom: 10px;
}
</style>
