<template>
  <div>
    <br />
    <h1>로그인</h1>
    <br />
    <!-- <label for="user_email">이메일: </label> -->
    <input
      id="user_email"
      type="email"
      v-model="loginInfo.userEmail"
      placeholder="이메일"
      @keyup.enter="login"
    />
    <br />
    <br />
    <!-- <label for="user_pwd">비밀번호: </label> -->
    <input
      id="user_pwd"
      type="password"
      v-model="loginInfo.userPwd"
      placeholder="비밀번호"
      @keyup.enter="login"
    />
    <br />
    <br />
    <button @click="login" width="500px" class="btn_yellow3">로그인</button>
    <br />
    <br />
    <button @click="findid" class="btn_pink3">아이디 찾기</button> &nbsp;
    <button @click="resetpwd" class="btn_pink3">비밀번호 찾기</button>
    <br />
    <br />
    <router-link to="/signUp" class="toSignUp"
      >회원이 아니신가요? join us😊</router-link
    >
  </div>
</template>

<script>
// import router from '@/router'

export default {
  name: "SsauctionLogin",

  data() {
    return {
      //로그인 객체
      loginInfo: {
        userEmail: "",
        userPwd: "",
      },
    };
  },

  mounted() {},

  methods: {
    findid() {
      this.$router.push('/findid')
    },

    resetpwd() {
      this.$router.push('/resetpwd')
    },

    login() {
      //user 모듈의 userLogin으로 보낸다.
      this.$store.dispatch("user/userLogin", this.loginInfo);
    },
  },
};
</script>

<style>
/* h1 {
  margin-left:110px
} */

input {
  width: 500px;
  height: 50px;
  border: 0;
  background-color: rgb(230, 230, 230);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left: 30px;
}

input:focus {
  outline: 3px rgb(94, 94, 94);
}

.btn_pink3 {
  width: 150px;
  height: 50px;
  border: 0;
  background-color: rgb(255, 170, 165);
  border-radius: 45px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.btn_yellow3 {
  width: 150px;
  height: 45px;
  border: 0;
  background-color: rgb(255, 211, 182);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.logo_resize {
  width: 60px;
  height: 60px;
}

a:link {
  text-decoration: none;
  color: grey;
}
a:visited {
  text-decoration: none;
  color: grey;
}
a:hover {
  text-decoration: none;
  color: black;
}

.toSignUp {
  position: relative;
  left: 150px;
}
</style>
