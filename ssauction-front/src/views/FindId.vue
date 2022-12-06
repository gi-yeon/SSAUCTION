<template>
<div style="margin-top: 100px">
  <h1>아이디 찾기</h1>
  <p>
    휴대폰 인증을 통해 아이디(이메일)을 찾을 수 있습니다. 
  </p>

  <div class="box">
    <div>
    <input id="user_phoneNo" type="string" v-model="phoneNo" placeholder="전화번호 (- 제외)">
    </div>
    <br>
    <br>
    <!-- <h1>{{ phoneNo }}</h1> -->
    <button class="btn_pink3" @click="findId">인증번호 전송</button>

    <div v-if="modalSmsCode == true">
      <br/>
      <br/>
        <div>
          <input id="user_smsCode" type="string" v-model="smsCode" placeholder="인증번호 입력">
        </div>
      <br>
        <button class="btn_pink3" @click="verifyCode">아이디 찾기</button>
    </div>
  </div>
  </div>
</template>

<script>
  import axios from "@/utils/axios";

  export default {
  name: "SsauctionFindId",

  data() {
    return {
      phoneNo: "",
      userEmail: "",
      returnValue: {},
      modalSmsCode : false,
      randCode: null,
      smsCode: ""
    };
  },

  mounted() {},

  methods: {
    findId: function () {
      // console.log("FindId start");
      // console.log(this.phoneNo);
      // console.log("start getting");
      axios.get("/users/findId/" + this.phoneNo).then(({ data }) => {
        // console.log(data.userPhoneNo);
        // console.log(data.userEmail);
        this.userEmail = data.userEmail;
        this.returnValue = data;
        // console.log(this.returnValue);
        if (this.returnValue !== "") {
          // 인증번호 입력창 띄워주기 + axios.post 요청으로 sms 발송
          this.modalSmsCode = true;
          axios.post("/users/sendSMS/" + this.phoneNo).then((smsData) => {
            // console.log(smsData);
            this.randCode = smsData.data;
            // console.log(`randCode: ${this.randCode}`);
            });
          } else {
          alert("해당 번호로 가입된 이메일이 없습니다.");
        }
      });
    },
    verifyCode: function() {
      // console.log(parseInt(this.smsCode));
      // console.log(parseInt(this.randCode));
      // if (인증번호 === 사용자입력값) { 아이디 정보 반환 }
      if (parseInt(this.smsCode) === parseInt(this.randCode)) {
        alert("아이디(이메일 주소)는 다음과 같습니다.\n" + this.userEmail);
        this.$router.push('/login')
      } else {
        alert("인증번호가 올바르지 않습니다.");
      }
    },
  },
};
</script>

// 220729
// 휴대폰 번호가 유저 테이블에 있음을 확인하면,
// 포스트 axios로 ('/users/sendSMS') 연동해서
// 해당 휴대폰번호에 sms로 인증번호 보내고
// 인증번호를 백에서 받아와서 입력한 번호와 일치할 경우 아이디 정보 반환


<style>
</style>