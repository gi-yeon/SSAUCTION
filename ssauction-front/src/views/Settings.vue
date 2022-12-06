<template>
  <div>
    <br />
    <h2>계정 정보 설정</h2>
    <br />
    <!-- 로그인 상태에서만 볼 수 있다 -->
    <div v-show="isLogin">
      <label for="userImg" class="profileLabel" enctype="multipart/form-data"
        ><button class="labelBtn" disabled>프로필 사진</button></label
      >

      <!-- <div class="mb-3">
        <el-upload
          class="avatar-uploader"
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="userImg" :src="userImg" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
        <input
          type="file"
          ref="itemImage"
          @change="imageUpload"
          multiple
          style="margin-bottom: 0.5rem"
        />
      </div>
      <br />
      <br /> -->

      <input
        class="profileInput"
        type="file"
        name="userEmail"
        id="userEmail"
        ref="userImg"
        placeholder="프로필 사진을 등록해주세요."
        @change="imgUpload"
      />
      <br />
      <br />

      <!-- 이메일 -->
      <label for="userEmail" class="profileLabel"
        ><button class="labelBtn" disabled>이메일</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userEmail"
        id="userEmail"
        v-model="userEmail"
        disabled
      />
      <br />
      <br />
      <!-- 닉네임 -->
      <label for="userNickname" class="profileLabel"
        ><button class="labelBtn" disabled>닉네임</button></label
      >
      <input
        class="nicknameInput"
        type="text"
        name="userNickname"
        id="userNickname"
        v-model="userNickname"
      /><button class="updateCheckBtn" @click="nicknameCheck">
        닉네임 중복 체크
      </button>
      <br />
      <br />

      <!-- 휴대폰 번호 -->
      <label for="userPhoneNo" class="profileLabel"
        ><button class="labelBtn" disabled>전화번호</button></label
      >
      <input
        class="phoneInput"
        type="text"
        name="userPhoneNo"
        id="userPhoneNo"
        v-model="userPhoneNo"
      />
      <button class="updateCheckBtn" @click="smsAuthCheck">
        휴대폰 인증 전송
      </button>
      <div v-if="smsAuthInput == true">
        <br />
        <br />
        <div>
          <input
            class="joinInput"
            id="signUp_smsCode"
            type="string"
            v-model="smsCode"
            placeholder="인증번호 입력"
            :state="smsAuthValidation"
          />
        </div>
        <br />
        <button class="joinCheckBtn" @click="smsVerifyCheck">
          휴대폰 인증 확인
        </button>
      </div>
      <br />
      <br />
      <!-- 회원 Desc -->
      <label for="userDesc" class="profileLabel"
        ><button class="labelBtn" disabled>자기소개</button></label
      >
      <input
        class="descInput"
        type="text"
        name="userDesc"
        id="userDesc"
        v-model="userDesc"
      />
      <br />
      <br />
      <!-- 회원 comment -->
      <label for="userComment" class="profileCommentLabel"
        ><button class="labelBtn" disabled>상태메시지</button></label
      >
      <input
        class="commentInput"
        type="text"
        name="userComment"
        id="userComment"
        v-model="userComment"
      />
      <br />
      <br />

      <button @click="putUserInfo" width="500px" class="btn_update">
        수정
      </button>
      <button @click="toPwdUpdate" width="500px" class="btn_update">
        비밀번호 변경
      </button>
      <br />
      <router-link to="/deleteUser" class="toDeleteUser">회원탈퇴</router-link>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState } from "vuex";

export default {
  name: "SsauctionSettings",
  compputed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      originNickname: "",
      originPhoneNo: "",
      originDesc: "",
      originComment: "",
      originImg: "",
      userEmail: "",
      userNickname: "",
      userGrade: 0,
      userPhoneNo: "",
      userNo: 0,
      userDesc: "",
      userComment: "",
      isLogin: false,
      userImg: null,
      nicknameChecked: false,
      smsAuthChecked: false,
      smsVerifyChecked: false,
      smsAuthInput: false,
      randCode: null,
      smsCode: "",
      returnValue: {},
      isChanged: false,
    };
  },

  mounted() {
    this.userNickname = this.$store.getters["user/userNickname"];
    this.originNickname = this.$store.getters["user/userNickname"];
    this.userGrade = this.$store.getters["user/userGrade"];
    this.userNo = this.$store.getters["user/userNo"];
    this.isLogin = this.$store.getters["user/isLogin"];
    this.getUserInfo();
    this.getUserImg();
  },

  methods: {
    toPwdUpdate() {
      this.$router.push("/pwdUpdate");
    },
    toDeleteUser() {
      this.$router.push("/deleteUser");
    },

    imgUpload() {
      this.userImg = [];
      Array.prototype.push.apply(this.userImg, this.$refs.userImg.files);
      console.log(this.userImg);
    },

    putUserInfo: async function () {
      //닉네임 변경되었을 경우 (중복체크 미완료시)
      if (this.originNickname != this.userNickname && !this.nicknameChecked) {
        alert("닉네임 중복체크를 진행해주세요.");
      } else if (
        this.originPhoneNo != this.userPhoneNo &&
        (!this.smsAuthChecked || !this.smsVerifyChecked)
      ) {
        alert("휴대폰 인증을 진행해주세요.");
      }
      //변동사항 있을 시
      else if (
        (this.originNickname != this.userNickname && this.nicknameChecked) ||
        (this.originPhoneNo != this.userPhoneNo &&
          this.smsAuthChecked &&
          this.smsVerifyChecked) ||
        this.originDesc != this.userDesc ||
        this.originComment != this.userComment
      ) {
        const obj = {
          userNickname: this.userNickname,
          userPhoneNo: this.userPhoneNo,
          userDesc: this.userDesc,
          userComment: this.userComment,
        };

        await axios
          .put("/users/profile/info/" + this.userNo, JSON.stringify(obj))
          .then(() => {
            this.$store.dispatch("user/getUpdateInfo", this.userNickname);
            this.isChanged = true;
          })
          .catch(({ data }) => {
            console.log(data);
          });
      }
      //이미지 변경되었을 경우
      if (this.originImg != this.userImg) {
        let formData = new FormData();
        for (let img of this.userImg) {
          formData.append("file", img);
        }

        await axios
          .put("/users/profile/img/" + this.userNo, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then(() => {
            this.isChanged = true;
          })
          .catch(({ data }) => {
            console.log(data);
          });
      }
      if (this.isChanged) {
        alert("회원 정보가 변경되었습니다.");
        this.$router.push("/");
      }
      //바뀐 것이 없다면
      else if (
        this.originNickname == this.userNickname &&
        this.originPhoneNo == this.userPhoneNo &&
        this.originDesc == this.userDesc &&
        this.originComment == this.userComment &&
        this.originImg == this.userImg
      ) {
        console.log(this.userImg);
        alert("변동사항이 없습니다.");
      }
    },

    //처음에 기존 프로필 정보 가져옴
    getUserInfo: async function () {
      await axios.get("/users/profile/" + this.userNo).then(({ data }) => {
        console.log(data);
        this.userEmail = data.userEmail;
        this.userPhoneNo = data.userPhoneNo;
        this.originPhoneNo = data.userPhoneNo;
        this.userDesc = data.userDesc;
        this.originDesc = data.userDesc;
        this.userComment = data.userComment;
        this.originComment = data.userComment;
      });
    },
    getUserImg: async function () {
      await axios.get("/users/profile/img/" + this.userNo).then((data) => {
        console.log(data);
        this.userImg = data.userImgUri;
        this.originImg = data.userImgUri;
      });
    },
    nicknameCheck() {
      if (this.userNickname != "") {
        axios
          .get("users/checkNickname/" + this.userNickname)
          .then(({ data }) => {
            if (data === "success") {
              this.nicknameChecked = true;
              alert("사용 가능한 닉네임입니다.");
            } else {
              alert("이미 사용중인 닉네임입니다.");
            }
          });
      } else {
        alert("닉네임을 입력해주세요");
      }
    },
    smsAuthCheck() {
      // 휴대폰 인증 절차 진행
      axios.get("/users/findId/" + this.userPhoneNo).then(({ data }) => {
        this.returnValue = data;
        if (this.returnValue == "") {
          // 인증번호 입력창 띄워주기 + axios.post 요청으로 sms 발송
          this.smsAuthInput = true;
          this.smsAuthChecked = true;
          axios.post("/users/sendSMS/" + this.userPhoneNo).then((smsData) => {
            this.randCode = smsData.data;
          });
        } else {
          alert("해당 번호로 가입된 계정이 있습니다.\n" + data.userEmail);
        }
      });
    },

    smsVerifyCheck() {
      // 입력한 인증번호가 전송한 번호와 같은지 확인
      if (parseInt(this.smsCode) === parseInt(this.randCode)) {
        alert("인증번호 확인 완료");
        this.smsVerifyChecked = true;
      } else {
        alert("인증번호가 올바르지 않습니다.");
      }
    },
  },
};
</script>


<style>
.profileInput {
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
.nicknameInput {
  margin-left: 5px;
  margin-right: 5px;
  height: 50;
  width: 290px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.phoneInput {
  margin-left: 5px;
  margin-right: 5px;
  height: 50;
  width: 285px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.descInput {
  margin-left: 5px;
  margin-right: 5px;
  height: 50;
  width: 395px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.commentInput {
  margin-left: 5px;
  margin-right: 5px;
  height: 50;
  width: 390px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.labelBtn {
  margin: 5px;
  height: 45px;
  border-radius: 4px;
}
.btn_update {
  margin: 10px;
  width: 200px;
  height: 50px;
  border: 0;
  background-color: rgb(255, 211, 182);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  text-align: center;
}
.profileLabel {
  margin: 5px;
  width: 120px;
}
.profileCommentLabel {
  margin-left: 10px;
  margin-right: 5px;
  width: 120px;
}
.updateCheckBtn {
  margin-left: 5px;
  margin-right: 5px;
  font-size: 0.8rem;
  font-weight: 700;
  color: orange;

  text-align: center;

  display: inline-block;
  width: auto;
  height: 40px;

  background-color: bisque;
  border-color: bisque;
  border-radius: 4px;
  border: none;
  box-shadow: none;
  outline: none;
}
</style>;
