<template>
  <div style="margin-top: 40px">
    <h1>회원가입</h1>
    <br />
  </div>

  <!--이메일-->
  <div>
    <input
      class="joinInput"
      id="user_email"
      type="email"
      v-model="userEmail"
      placeholder="이메일"
      :state="emailValidation"
    />
    <button class="joinCheckBtn" @click="emailCheck">이메일 중복 체크</button>
    <br />
    <br />

    <!--비밀번호-->
    <input
      id="user_pwd"
      type="password"
      v-model="userPwd"
      placeholder="8~20자의 비밀번호를 입력해주세요."
      :state="pwdValidation"
    />
    <br />
    <br />
    <input
      id="user_pwd2"
      type="password"
      v-model="pwdCheck"
      placeholder="비밀번호 확인"
      :state="pwdCheckValidation"
    />
    <br />
    <p v-if="pwdCheck.length == 0" style="color: grey; margin-top:10px; font-size: 13px;">
      비밀번호를 입력해주세요.
    </p>
    <p v-else-if="userPwd === pwdCheck" style="color: grey; margin-top:10px; font-size: 13px;">비밀번호가 일치합니다.</p>
    <p v-else style="color: red; margin-top:10px; font-size: 13px;">비밀번호가 일치하지 않습니다.</p>

    <!--닉네임--->
    <input
      class="joinInput"
      id="user_nickname"
      type="String"
      v-model="userNickname"
      placeholder="닉네임"
      :state="nicknameValidation"
    /><button class="joinCheckBtn" @click="nicknameCheck">
      닉네임 중복 체크
    </button>
    <br />
    <br />

    <!--휴대폰 번호-->
    <input
      class="joinInput"
      id="user_phone_no"
      type="String"
      v-model="userPhoneNo"
      placeholder="휴대폰 번호 ex) 01012345678"
      :state="phoneValidation"
    /><button class="joinCheckBtn" @click="smsAuthCheck">휴대폰 인증 전송</button>
    <div v-if="smsAuthInput == true">
      <br/>
      <br/>
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
      <br/>
        <button class="joinCheckBtn" @click="smsVerifyCheck">휴대폰 인증 확인</button>
    </div>
    <br />
    <br />
    <button @click="submit" width="500px" class="btn_yellow3">회원가입</button>
    <br />
    <br />
  </div>
</template>

<script lang="ts" setup>
</script>

<script>
import axios from "@/utils/axios";

export default {
  name: "SsauctionSignup",
  data() {
    return {
      userEmail: "",
      userPwd: "",
      pwdCheck: "",
      userPhoneNo: "",
      userNickname: "",
      emailChecked: false,
      nicknameChecked: false,
      smsAuthChecked: false,
      smsVerifyChecked: false,
      smsAuthInput: false,
      randCode: null,
      smsCode: "",
      returnValue: {},
      buttonSignUp: false,
    };
  },

  computed: {
    emailValidation() {
      let re =
        /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return this.emailChecked && re.test(this.userEmail);
    },
    pwdValidation() {
      return this.userPwd.length >= 8 && this.userPwd.length <= 20;
    },
    pwdCheckValidation() {
      return (
        this.userPwd == this.pwdCheck &&
        this.pwdCheck.length >= 8 &&
        this.pwdCheck.length <= 20
      );
    },
    nicknameValidation() {
      return this.nicknameChecked;
    },
    phoneValidation() {
      return this.userPhoneNo != "";
    },
    smsAuthValidation() {
      return this.smsAuthChecked;
    },
    smsVerifyValidation() {
      return this.smsVerifyChecked;
    },
  },

  mounted() {},

  methods: {
    submit: function () {
      if (
        this.emailValidation &&
        this.pwdValidation &&
        this.pwdCheckValidation &&
        this.nicknameValidation &&
        this.phoneValidation &&
        this.smsAuthValidation &&
        this.smsVerifyValidation
      ) {
        const obj = {
          userEmail: this.userEmail,
          userPwd: this.userPwd,
          userPhoneNo: this.userPhoneNo,
          userNickname: this.userNickname,
        };
        axios.post("/users/join", JSON.stringify(obj)).then(({ data }) => {
          if (data === "OK") {
            alert("SSAUCTION에 오신 것을 환영합니다.");
            this.$router.push({ name: "Login" });
          } else {
            alert("입력 정보를 확인해주세요.");
          }
        });
      } else if (!this.emailValidation) {
        alert("이메일을 확인해주세요.");
      } else if (!this.pwdValidation) {
        alert("8~20자의 비밀번호를 입력해주세요.");
      } else if (!this.pwdCheckValidation) {
        alert("비밀번호 확인란을 확인해주세요.");
      } else if (!this.nicknameValidation) {
        alert("닉네임을 확인해주세요.");
      } else if (!this.phoneValidation) {
        alert("휴대폰 번호를 확인해주세요.");
      } else if (!this.smsAuthValidation) {
        alert("휴대폰 인증을 진행해주세요.")
      } else if (!this.smsVerifyValidation) {
        alert("인증번호를 확인해주세요.")
      } else {
        alert("입력 정보를 확인해주세요.");
      }
    },

    emailCheck() {
      if (this.userEmail != "") {
        axios.get("users/checkEmail/" + this.userEmail).then(({ data }) => {
          if (data === "success") {
            this.emailChecked = true;
            alert("사용 가능한 이메일입니다.");
          } else {
            alert("이미 사용중인 이메일입니다.");
          }
        });
      } else {
        alert("이메일을 입력해주세요");
      }
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
.joinInput {
  width: 390px;
}

.joinCheckBtn {
  margin-left: 10px;
  margin-right: 0px;
  font-size: 0.8rem;
  font-weight: 700;
  color: rgba(228, 148, 0, 0.885);

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
</style>
