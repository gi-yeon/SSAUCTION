<template>
  <div>
    <router-link to="/"
      ><div
        class="brand"
        style="d-flex justify-content-start padding-left:15px"
      >
        SSAUCTION
      </div></router-link
    >

    <!--로그인 한 상태에서 보여주는 인사메시지, 로그아웃버튼-->
    <div v-if="user.isLogin" style="padding-top: 20px; padding-right: 5px">
      <p style="float: left">
        {{ user.loginUser.userNickname }}님 반갑습니다💸
      </p>
      &nbsp; &nbsp;
      <img
        v-if="user.isLogin"
        @click="logout"
        src="@\assets\imgs\logout.png"
        alt="Logout"
        class="icon2"
      />&nbsp; &nbsp;
    </div>

    <!-- 로그아웃 한 상태에서는 로그인, 회원가입 버튼만 노출됨 -->
    <div
      v-else
      style="d-flex justify-content-end; padding-top: 20px; padding-right:5px;"
    >
      <router-link to="/login"
        ><img src="@\assets\imgs\login.png" alt="Login" class="icon2"
      /></router-link>
      &nbsp; &nbsp;
      <router-link to="/signup"
        ><img src="@\assets\imgs\signup.png" alt="Signup" class="icon3"
      /></router-link>
      &nbsp; &nbsp;
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "NavBar",
  computed: {
    ...mapState(["user"]),
  },
  methods: {
    logout() {
      this.$store.dispatch("user/userLogout");
    },
  },
};
</script>

<style>
.brand {
  /* display: inline; */
  font-size: 50px;
  color: rgb(94, 94, 94);
  padding-left: 15px;
}

.icon2 {
  height: 30px;
  width: 30px;
}

.icon3 {
  height: 35px;
  width: 35px;
}
</style>