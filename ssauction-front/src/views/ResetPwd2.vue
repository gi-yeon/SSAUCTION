// 비밀번호 수정 (휴대폰 인증 후)

<template>
  <div style="margin-top: 100px">
  <h1>비밀번호 재설정</h1>
  <br />
  <p>
    새로운 비밀번호를 입력해주세요.
  </p>
<!-- 
  <label for="user_password">새 비밀번호: </label>
  <input id="user_password" type="password" v-model="password" />
  <h1>{{ password }}</h1>
  <label for="user_password2">비밀번호 확인: </label>
  <input id="user_password2" type="password" v-model="password2" />
  <h1>{{ password2 }}</h1>
  <button @click="ResetPwd2">Reset Password</button> -->

 <div class="box" style="margin-top: 50px">
    <div>
      <input id="user_password" type="password" v-model="password" placeholder="새 비밀번호">
      <br>
      <br>
      <input id="user_password2" type="password" v-model="password2" placeholder="새 비밀번호 확인">
    </div>
      <br>
      <button class="btn_pink" @click="ResetPwd2">비밀번호 재설정</button>
      
    <div v-if="modalSmsCode == true">
    <br/>
    <br/>
      <div>
        <input id="user_smsCode" type="string" v-model="smsCode" placeholder="인증번호 입력">
      </div>
      <br>
        <button class="btn_pink" @click="verifyCode">비밀번호 재설정하기</button>
    </div>
  </div>





  </div>
</template>

<script>
  import axios from "@/utils/axios";

  export default {
  name: "ResetPwd2",
  props: {
    phoneNo: {
      type: String,
      default: ''
    },
    email: {
      type: String,
      default: ''
    },
  },

  data() {
    return {
      password: "",
      password2: "",
      returnValue: {},
    };
  },

  mounted() {},

  methods: {
    ResetPwd2: function () {
      // console.log("ResetPwd start");
      // console.log(this.password);
      // console.log(this.password2);
      
      if (this.password === this.password2) {
        const obj = {
          userPwd: this.password,
        };
        // console.log("start putting");
        axios.put("/users/resetPwd/" + this.phoneNo + "/" + this.email, JSON.stringify(obj),).then(({ data }) => {
        // console.log(data.userPhoneNo);
        // console.log(data.userEmail);
        this.returnValue = data;
        // console.log(this.returnValue);
          if (this.returnValue !== "") {
            alert("비밀번호가 재설정되었습니다.");
            this.$router.push('/login')
          } else {
            alert("에러: 다시 시도해주세요.");
            this.$router.push('/ResetPwd')
          }
        });
      } else {
        alert("새 비밀번호가 일치하지 않습니다.");
      }
    },
  },
};
</script>