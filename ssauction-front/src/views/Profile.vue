<template>
  <div>
    <h2>{{ user.loginUser.userNickname }}님의 프로필</h2>
    <br />
    <div v-show="user.isLogin">
      <br />
      <!-- 수정 모달 -->
      <b-modal
        id="modal-3"
        scrollable
        title="물품 수정하기"
        size="lg"
        v-model="isHide"
      >
        <div class="flex m-10">
          <draggable class="dragArea d-flex" v-model="updateImgs" @change="log">
            <div
              class="list-group-item bg-gray-300 m-1 p-3 rounded-md text-center d-flex"
              v-for="(element, idx) in updateImgs"
              :key="element"
            >
              <img
                class="imgTag imgResize"
                v-bind:src="'data:image/png;base64,' + element.img"
              />
              <button class="btn_delete" @click="deleteImgs(idx)">X</button>
            </div>
          </draggable>
        </div>
        <div class="mb-3">
          <el-upload
            class="avatar-uploader"
            action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>

          <input
            type="file"
            ref="addImage"
            @change="imageUpload"
            multiple
            style="margin-bottom: 0.5rem"
          />
          <item-image-preview
            v-for="(file, index) in addImages"
            :key="file.lastModified"
            :previewfile="file"
            :index="index"
            @delete-img="deleteImg"
            style="margin-bottom: 0.5rem; margin-left: 0.5rem"
          />
        </div>
        <label class="formLabel">경매방 이름</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.houseTitle"
        />
        <br />
        <br />
        <label class="formLabel">매물 이름</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.itemName"
        />
        <br />
        <br />
        <label class="formLabel">모델 번호</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.modelNo"
        />
        <br />
        <br />

        <div class="row">
          <label class="formLabel col-2">카테고리</label>&nbsp;&nbsp;
          <div class="box2 col-8 align-left" style="margin-bottom=1rem ">
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr1"
                value="전자기기"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr1">전자기기</label>
            </div>
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr2"
                value="생활가전"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr2">생활가전</label>
            </div>
            <div class="form-check2 form-check-inline">
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
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr4"
                value="생활/주방"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr4">생활/주방</label>
            </div>

            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr5"
                value="여성명품"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr5">여성명품</label>
            </div>
            <div class="form-check2 form-check-inline">
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
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr7"
                value="여성잡화"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr7">여성잡화</label>
            </div>
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr8"
                value="뷰티/미용"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr8">뷰티/미용</label>
            </div>
            <div class="form-check2 form-check-inline">
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
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr10"
                value="남성의류/신발"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr10">남성의류/신발</label>
            </div>
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr11"
                value="남성잡화"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr11">남성잡화</label>
            </div>
            <div class="form-check2 form-check-inline">
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
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr13"
                value="취미/게임/음반"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr13"
                >취미/게임/음반</label
              >
            </div>
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr14"
                value="도서"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr14">도서</label>
            </div>
            <div class="form-check2 form-check-inline">
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
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr16"
                value="상품권/교환권"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr16">상품권/교환권</label>
            </div>

            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr17"
                value="유아동"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr17">유아동</label>
            </div>
            <div class="form-check2 form-check-inline">
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
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr19"
                value="반려동물용품"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr19">반려동물용품</label>
            </div>
            <div class="form-check2 form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="ctgr20"
                value="식물"
                v-model="ctgr.ctgrName"
              />
              <label class="form-check-label" for="ctgr20">식물</label>
            </div>
            <div class="form-check2 form-check-inline">
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

        <!-- <input class="customInput" style="margin-bottom=1rem" type="text" v-model="info.category" /> -->
        <br />
        <br />
        <label class="formLabel">시작 가격</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.startPrice"
        />
        <br />
        <br />
        <label class="formLabel">경매 시각</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.auctionTime"
        />
        <br />
        <br />
        <label class="formLabel">판매 상태</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.dealStatus"
        />
        <br />
        <br />
        <label class="formLabel">상품 설명</label>&nbsp;&nbsp;
        <input
          class="customInput"
          style="margin-bottom=1rem"
          type="text"
          v-model="info.desc"
        />
        <b-button class="btn_pink5" @click="cancel()" data-baz="buz"
          >삭제하기
        </b-button>
        <b-button class="btn_yellow5" @click="updateProfile()" data-baz="buz"
          >수정하기
        </b-button>
      </b-modal>

      <div class="box" style="margin: 10px">
        <div class="p-2" style="padding: 0.5rem; padding-bottom: 0rem">
          <!-- <h4 class="card-title" style="display: inline-block">기본 정보</h4> -->
          <br />
          <div class="row">
            <div class="col-sm-4" style="text-align: center">
              <div class="img-container">
                <img
                  class="userImgContainer"
                  v-if="hasImg"
                  v-bind:src="'data:image/png;base64,' + userMainImg"
                />
                <!-- </div> -->
                <!-- </div> -->
              </div>
            </div>
            <div class="col-sm-8">
              <div>
                <div style="padding: 0.5rem; padding-bottom: 0.5rem">
                  <div class="row">
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-left: 1rem"
                    >
                      <button
                        type="button"
                        class="labelbutton btn-sm col-sm-4"
                        disabled
                      >
                        아이디 (이메일)
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ userEmail }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-right: 1rem"
                    >
                      <button
                        type="button"
                        class="labelbutton btn-sm col-sm-4"
                        disabled
                      >
                        닉네임
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ user.loginUser.userNickname }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-left: 1rem"
                    >
                      <button
                        type="button"
                        class="labelbutton btn-sm col-sm-4"
                        disabled
                      >
                        휴대폰 번호
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ userPhoneNo }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-right: 1rem"
                    >
                      <button
                        type="button"
                        class="labelbutton btn-sm col-sm-4"
                        disabled
                      >
                        유저 등급
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ user.loginUser.userGrade }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-12"
                      style="
                        padding: 0rem;
                        padding-left: 1rem;
                        padding-right: 1rem;
                      "
                    >
                      <button
                        type="button"
                        class="labelbutton btn-sm col-sm-2"
                        disabled
                      >
                        상태 메시지
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-10"
                        disabled
                      >
                        {{ userComment }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-12"
                      style="
                        padding: 0rem;
                        padding-left: 1rem;
                        padding-right: 1rem;
                      "
                    >
                      <button
                        type="button"
                        class="labelbutton btn-sm col-sm-2"
                        disabled
                      >
                        자기 소개
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-10"
                        disabled
                      >
                        {{ userDesc }}
                      </button>
                    </div>
                    <br />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <br />

      <p>
        <button
          class="togglebtn"
          type="button"
          data-bs-target="#sellingItem"
          aria-expanded="false"
          aria-controls="sellingItem"
          @click="toSellingItem"
        >
          판매 내역</button
        >&nbsp; &nbsp;
        <button
          class="togglebtn"
          type="button"
          data-bs-target="#purchasedItem"
          aria-expanded="false"
          aria-controls="purchasedItem"
          @click="toPurchasedItem"
        >
          구매 내역
        </button>
      </p>

      <div id="sellingItem" style="margin-top: 20px" v-show="isSell">
        <div class="box" style="margin: 10px">
          <div class="p-2" style="padding: 0.5rem; padding-bottom: 0rem">
            <h4 class="card-title">판매 내역</h4>
            <br />
            <div class="row">
              <div
                v-b-modal.modal-1
                class="col-sm-3"
                v-for="index in sellItem"
                :key="index"
                @click="getItemDetail(index)"
              >
                <!-- 판매 물품 모달 -->
                <b-modal scrollable id="modal-1" title="판매 물품" size="lg">
                  <div>
                    <div style="text-align: center">
                      <Carousel :images="images" />
                    </div>
                    <br />

                    <div>
                      <br />
                      <br />
                      <label class="formLabel">경매방 이름</label>&nbsp;&nbsp;{{
                        info.houseTitle
                      }}
                      <br />
                      <label class="formLabel">매물 이름</label>&nbsp;&nbsp;{{
                        info.itemName
                      }}
                      <br />
                      <label class="formLabel">모델 번호</label>&nbsp;&nbsp;{{
                        info.modelNo
                      }}
                      <br />
                      <label class="formLabel">카테고리</label>
                      <span v-for="index in info.ctgrList" :key="index"
                          >&nbsp;&nbsp;#{{ index.ctgrName }}</span
                        >
                      <br />
                      <label class="formLabel">시작 가격</label>&nbsp;&nbsp;{{
                        info.startPrice
                      }}원
                      <br />
                      <label class="formLabel">경매 일시</label>&nbsp;&nbsp;{{
                        index.item.auctionTime.substr(0, 10)
                      }}
                      {{ index.item.auctionTime.substr(11, 5) }}
                      <br />
                      <label class="formLabel">판매 상태</label>&nbsp;&nbsp;{{
                        info.dealStatus
                      }}
                      <br />
                      <label class="formLabel">매물 설명</label>&nbsp;&nbsp;{{
                        info.desc
                      }}
                      <br />
                    </div>
                  </div>

                  <template v-slot:footer="{ ok }">
                    <b-button
                      class="btn_pink5"
                      v-b-modal.modal-3
                      @click="ok"
                      data-baz="buz"
                      >수정하기</b-button
                    >
                  </template>
                </b-modal>
                <div>
                  <div style="margin: 10px">
                    <div style="padding: 0.5rem; padding-bottom: 0rem">
                      <div class="sell-container text-align:left">
                        <img
                          class="sellImgContainer"
                          v-bind:src="
                            'data:image/png;base64,' +
                            index.itemImgs[getItemImgsIdx(index)].img
                          "
                        />
                        <br />
                        {{ index.item.itemName }} {{ index.item.startPrice }}원
                        <br />
                        {{ index.item.regTime.substr(0, 10) }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      &nbsp; &nbsp;
      <div v-show="isPurchased">
        <div id="purchasedItem">
          <div class="box" style="margin: 10px">
            <div style="padding: 0.5rem; padding-bottom: 0rem">
              <h4 class="card-title">구매 내역</h4>
              <br />
              <div class="row">
                <div
                  v-b-modal.modal-2
                  class="col-sm-3"
                  v-for="index in buyItem"
                  :key="index"
                  @click="getItemDetail(index)"
                >
                  <!-- 구매 물품 모달 -->
                  <b-modal
                    scrollable
                    id="modal-2"
                    title="구매 물품"
                    ok-title="update"
                    size="lg"
                  >
                    <div>
                      <div style="text-align: center">
                        <Carousel :images="images" />
                      </div>
                      <br />

                      <div>
                        <br />
                        <br />
                        <label class="formLabel">경매방 이름</label
                        >&nbsp;&nbsp;{{ info.houseTitle }}
                        <br />
                        <label class="formLabel">매물 이름</label>&nbsp;&nbsp;{{
                          info.itemName
                        }}
                        <br />
                        <label class="formLabel">모델 번호</label>&nbsp;&nbsp;{{
                          info.modelNo
                        }}
                        <br />
                        <label class="formLabel">카테고리</label>
                        <span v-for="index in info.ctgrList" :key="index"
                          >&nbsp;&nbsp;#{{ index.ctgrName }}</span
                        >
                        <br />
                        <label class="formLabel">시작 가격</label>&nbsp;&nbsp;{{
                          info.startPrice
                        }}원
                        <br />
                        <label class="formLabel">경매 일시</label>&nbsp;&nbsp;{{
                          index.item.auctionTime.substr(0, 10)
                        }}
                        {{ index.item.auctionTime.substr(11, 5) }}
                        <br />
                        <label class="formLabel">판매 상태</label>&nbsp;&nbsp;{{
                          info.dealStatus
                        }}
                        <br />
                        <label class="formLabel">매물 설명</label>&nbsp;&nbsp;{{
                          info.desc
                        }}
                        <br />
                      </div>
                    </div>
                  </b-modal>
                  <div>
                    <div>
                      <div style="padding: 0.5rem; padding-bottom: 0rem">
                        <div class="sell-container">
                          <img
                            class="sellImgContainer"
                            v-bind:src="
                              'data:image/png;base64,' +
                              index.itemImgs[getItemImgsIdx(index)].img
                            "
                          />
                          <br />
                          {{ index.item.itemName }}
                          <br />
                          {{ index.item.startPrice }}원 /
                          {{ index.item.regTime.substr(0, 10) }}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState } from "vuex";
import { defineComponent } from "vue";
import Carousel from "@/components/CarouselComp.vue";
import { VueDraggableNext } from "vue-draggable-next";
import ItemImagePreview from "@/components/ItemImagePreview.vue";

export default defineComponent({
  name: "SsauctionProfile",
  components: {
    Carousel,
    draggable: VueDraggableNext,
    ItemImagePreview,
  },
  computed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      userNo: 0,
      userEmail: "",
      userPhoneNo: "",
      userDesc: "",
      userComment: "",
      userMainImg: [],
      sellItem: [],
      buyItem: [],
      hasImg: false,
      images: [],
      info: {},
      enabled: true,
      updateImgs: {},
      delImgs: [],
      dragging: true,
      isHide: false,
      addImages: [],
      isSell: false,
      isPurchased: false,
      ctgr: {
        ctgrName: [],
      },
    };
  },

  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    this.getUserInfo();
    this.getUserImg();
    this.getItemInfo();
  },

  methods: {
    toSellingItem() {
      this.isSell = true;
      this.isPurchased = false;
    },
    toPurchasedItem() {
      this.isSell = false;
      this.isPurchased = true;
    },

    deleteImg(index) {
      this.addImages.splice(index, 1);
      console.log(this.$refs.addImage);
    },
    getUserInfo: async function () {
      await axios
        .get("/users/profile/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.userDesc = data.userDesc;
          this.userComment = data.userComment;
          this.userImgUri = data.userImgUri;
          this.userEmail = data.userEmail;
          this.userPhoneNo = data.userPhoneNo;
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    getUserImg: async function () {
      await axios
        .get("/users/profile/img/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.hasImg = data.userMainImg !== null;
          if (this.hasImg) this.userMainImg = data.userMainImg;
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },

    getItemInfo: async function () {
      console.log(this.userNo);
      await axios
        .get("/houses/profile/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          console.log(data[0]);
          if (data === null) {
            console.log("null data");
          } else {
            this.sellItem = data.sellItemsList;
            this.buyItem = data.buyItemsList;
            console.log(this.sellItem[0].itemImgs);
            console.log(this.sellItem);
            console.log(this.buyItem);
          }
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    // updateItem:async function(){
    //   //await axios.put("/houses/update/")
    // },
    getItemDetail: async function (index) {
      console.log(index.item);
      this.images = index.itemImgs;
      this.updateImgs = index.itemImgs;
      this.info = index.item;
    },
    ok: async function (index) {
      console.log(index);
      console.log("here is ok");
    },
    log(event) {
      console.log(event);
    },
    clicklist() {
      console.log(this.images);
    },
    deleteImgs(idx) {
      this.delImgs.push(this.updateImgs[idx].imgNo);
      this.updateImgs.splice(idx, 1);
      console.log(this.delImgs);
    },
    cancel: async function () {
      await axios.delete("/houses/" + this.info.houseNo).then(({ data }) => {
        console.log(data);
        alert("삭제 완료");
        this.isHide = false;
      });
    },
    updateProfile: async function () {
      const deleteArr = [];
      for (let no of this.delImgs) {
        console.log(this.delImgs);
        console.log(no);
        deleteArr.push(no);
      }
      const idxs = [];
      for (let idx of this.updateImgs) {
        console.log(idx.imgNo);
        idxs.push(idx.imgNo);
      }
      const deletejson = JSON.stringify({ indexs: deleteArr });
      const sortjson = JSON.stringify({ indexs: idxs });
      const infojson = JSON.stringify(this.info);
      const ctgrjson = JSON.stringify(this.ctgr);
      console.log(infojson);
      console.log(deletejson);
      console.log(sortjson);
      console.log(ctgrjson);
      const deleteblob = new Blob([deletejson], { type: "application/json" });
      const sortblob = new Blob([sortjson], { type: "application/json" });
      const infoblob = new Blob([infojson], { type: "application/json" });
      const ctgrblob = new Blob([ctgrjson], { type: "application/json" });
      // html의 form 태그를 이용해 submit하면 formData 객체와 multipart/form-data 형식으로 전달된다.
      // form 태그를 이용하고 있지 않지만 이용한 것처럼 요청하기 위해 새 formData 객체를 만든다.
      let formData = new FormData();
      formData.append("deleteDto", deleteblob);
      formData.append("sortDto", sortblob);
      formData.append("houseUpdateDto", infoblob);
      formData.append("ctgrDto", ctgrblob);
      const file = new File(["foo"], "foo.txt", {
        type: "text/plain",
      });
      if (this.addImages != 0) {
        for (let img of this.addImages) {
          console.log(img);
          formData.append("files", img);
        }
      } else {
        formData.append("files", file);
      }
      await axios
        .put("/houses/update/" + this.info.houseNo, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(({ data }) => {
          console.log(data);
          alert("수정 완료");
          this.isHide = false;
        });
    },
    imageUpload() {
      // $refs를 통해 DOM에 있는 input의 files에 직접 접근하면 FileList 객체가 반환된다.
      // FileList 객체는 read only이므로 다루기 어렵다.
      // 아래의 코드를 통해 this.itemImages 배열에 files에 있는 File 객체를 그대로 복사한다.
      Array.prototype.push.apply(this.addImages, this.$refs.addImage.files);
      console.log(this.addImages);
      this.$refs.addImage.value = "";
    },
    getItemImgsIdx(idxs) {
      let cnt = 0;
      console.log(idxs);
      for (let id of idxs.itemImgs) {
        console.log(id);
        console.log(id.main);
        if (id.main) {
          console.log(cnt);
          return cnt;
        }
        cnt++;
      }
      console.log(cnt);
      return 0;
    },
  },
});
</script>

<style>
.userImgContainer {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.sellImgContainer {
  width: 70%;
  height: 70%;
  object-fit: cover;
  /* object-fit: contain; */
}
.col-sm-6 {
  margin-top: 5px;
  margin-bottom: 5px;
}

.col-sm-12 {
  margin-top: 5px;
  margin-bottom: 5px;
}

.btn btn-light btn-sm col-sm-8 {
  border: solid grey;
}
.img-container {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
  margin: auto;
}
.sell-container {
  height: 300px;
  overflow: hidden;
  margin: auto;
}
.imgTag {
  display: block;
  margin: auto;
}

.togglebtn {
  width: 100px;
  height: 50px;
  border: 0;
  background-color: rgba(255, 169, 165, 0.549);
  border-radius: 45px;
  color: rgb(94, 94, 94);
  text-align: center;
  font-size: 18px;
}

.labelbutton {
  border: 0;
  background-color: rgb(255, 211, 182);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.btn_delete {
  width: 30px;
  height: 30px;
  border: 0;
  background-color: rgb(221, 221, 221);
  border-radius: 45px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.imgResize {
  width: 80%;
  height: 80%;
}

.btn_pink5 {
  width: 100px;
  height: 45px;
  border: 0;
  background-color: rgb(255, 170, 165);
  border-radius: 45px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.btn_yellow5 {
  width: 100px;
  height: 45px;
  border: 0;
  background-color: rgb(255, 211, 182);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.formLabel {
  margin-left: 5px;
  margin-right: 5px;
  height: 50px;
  width: 130px;
  margin-bottom: 20px;
  font-weight: bold;
}

.customInput {
  margin-left: 5px;
  margin-right: 5px;
  height: 50px;
  width: 400px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}

.box2 {
  margin-left: 0px;
  margin-top: 10px;
  margin-bottom: 20px;
  margin-right: 10px;
  padding: 5px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  width: 370px;
}

.form-check2 {
  margin-top: 0.5rem;
  margin-right: 0.5rem;
  font-size: 0.7rem;
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
</style>
