<template>
  <div id="app" class="common-layout">
    <!-- 전체 레이아웃을 el-header, el-aside, el-container로 나눈 뒤, 각각에 navbar.vue, sidebar.vue, router vue를 배치함 -->
    <el-container>
      <el-header>
        <nav-bar id="navbar"></nav-bar>
      </el-header>

      <side-bar id="sidebar"></side-bar>

      <el-main id="container">
        <router-view></router-view>
      </el-main>

      <!-- <el-footer id="footer">SSAFY 7th Seoul A806</el-footer> -->
    </el-container>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import SideBar from "@/components/SideBar.vue";
import { useStore } from "vuex";

export default {
  name: "App",
  components: { NavBar, SideBar },
  data() {
    return {
      isInSession: false,
      store: null,
    };
  },
  beforeMount() {
    this.store = useStore();
  },
  mounted() {
    // console.log(this.store.state.session.isInSession);
    // this.isInSession = !this.store.state.session.isInSession;

    //쿠키에서 logininfo를 가져온다.
    this.$store.dispatch("user/getLoginInfo");
  },
  methods: {},
};
</script>

<style>
@font-face {
  font-family: "NanumSquareRoundB";
  src: url("assets/font/NanumSquareRoundB.ttf") format("truetype");
  font-weight: 300;
  color: #f6f6f6;
}

#app {
  font-family: NanumSquareRoundB, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: rgb(65, 65, 65);
}

#footer {
  position: fixed;
  text-align: center;
  left: 0;
  right: 0;
  bottom: 0;
  height: 40px;
}

#navbar {
  position: fixed;
  left: 0;
  right: 0;
  height: 70px;
  background-color: rgb(255, 170, 165);
  display: flex;
  justify-content: space-between;
  margin: 0;
}

#sidebar {
  position: fixed;
  left: 0;
  right: 0;
  top: 70px;
  overflow-x: hidden;
  bottom: 0;
  width: 80px;
  height: 100%;
  background-color: rgb(255, 211, 182);
}

#container {
  position: fixed;
  left: 70px;
  right: 0px;
  top: 70px;
  bottom: 0;
}
</style>
