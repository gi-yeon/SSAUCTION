// 비밀번호 수정 전 (휴대폰 인증 전)

<template>
  <div style="margin-top: 100px">
  <h1>비밀번호 재설정</h1>
  <p>
    휴대폰 인증을 통해 비밀번호를 변경할 수 있습니다. 
  </p>

  <div class="box" style="margin-top: 50px">
    <div>
      <input id="user_email" type="string" v-model="userEmail" placeholder="이메일">
      <br>
      <br>
      <input id="user_phoneNo" type="string" v-model="phoneNo" placeholder="전화번호 (- 제외)">
    </div>
      <br>
      <button class="btn_pink3" @click="ResetPwd">인증번호 전송</button>
      
    <div v-if="modalSmsCode == true">
    <br/>
    <br/>
      <div>
        <input id="user_smsCode" type="string" v-model="smsCode" placeholder="인증번호 입력">
      </div>
      <br>
        <button class="btn_pink3" @click="verifyCode">비밀번호 재설정하기</button>
    </div>
  </div>
  </div>
</template>

<script>
  import axios from "@/utils/axios";
  export default {
  name: "SsauctionResetPwd",

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
    ResetPwd: function () {
      // console.log("ResetPwd start");
      // console.log(this.phoneNo);
      // console.log(this.userEmail);
      
      // console.log("start putting");
      // 전화번호 & 아이디 검사하여 일치하는 유저정보를 ResetPwd2로 router 이용하여 보내기
      axios.get("/users/findId/" + this.phoneNo).then(({ data }) => {
        // console.log(data.userPhoneNo);
        // console.log(data.userEmail);
        this.returnValue = data;
        // console.log(this.returnValue);
        if (this.returnValue !== "") {
          if (this.userEmail === data.userEmail) {
            // 인증번호 입력창 띄워주기 + axios.post 요청으로 sms 발송
            this.modalSmsCode = true;
            // console.log("인증창");
            axios.post("/users/sendSMS/" + this.phoneNo).then((smsData) => {
              // console.log("인증번호");
              // console.log(smsData);
              this.randCode = smsData.data;
              // console.log(`randCode: ${this.randCode}`);
            });
          } else {
            alert("아이디(이메일 주소) 정보가 일치하지 않습니다.");
          }
        } else {
          alert("전화번호 정보가 없습니다.");
        }
      });
    },
    verifyCode: function() {
      // console.log(parseInt(this.smsCode));
      // console.log(parseInt(this.randCode));
      // if (인증번호 === 사용자입력값) { ResetPwd2로 리다이렉트 }
      if (parseInt(this.smsCode) === parseInt(this.randCode)) {
        this.$router.push({name: 'ResetPwd2', params: {phoneNo: this.phoneNo, email: this.userEmail}})
      } else {
        alert("인증번호가 올바르지 않습니다.");
      }
    },
  },
};
</script>

// 220802
// 휴대폰 번호와 이메일이 유저 테이블과 일치함을 확인하면,
// 포스트 axios로 ('/users/sendSMS') 연동해서
// 해당 휴대폰번호에 sms로 인증번호 보내고
// 인증번호를 백에서 받아와서 입력한 번호와 일치할 경우 ResetPwd2로 리다이렉트