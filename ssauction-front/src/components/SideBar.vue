<template>
  <el-menu
    class="el-menu-vertical-demo"
    :collapse="isCollapse"
    @open="handleOpen"
    @close="handleClose"
  >
    <!-- <el-sub-menu index="1">
        <router-link to="/"
        ><img src="@\assets\imgs\home.png" alt="Home" class="icon"
      /></router-link>&nbsp;
        <span>메인</span>
    </el-sub-menu> -->

    <el-menu-item index="2" style="height: 80px">
      <router-link to="/"
        ><img
          src="@\assets\imgs\home.png"
          alt="Home"
          class="icon" /></router-link
      >&nbsp;
      <span>메인</span>
    </el-menu-item>

    <el-menu-item index="2" style="height: 80px">
      <router-link to="/profile"
        ><img
          src="@\assets\imgs\profile.png"
          alt="Profile"
          class="icon" /></router-link
      >&nbsp;
      <span>나의 프로필</span>
    </el-menu-item>

    <!-- <el-menu-item index="3" style="height:80px;">
      <router-link to="zzim"
        ><img src="@\assets\imgs\zzim.png" alt="Zzim" class="icon"
      /></router-link>&nbsp;
      <span>찜 목록</span>
    </el-menu-item> -->

    <el-menu-item index="4" style="height: 80px">
      <router-link to="/house"
        ><img
          src="@\assets\imgs\auction.png"
          alt="MakeHouse"
          class="icon" /></router-link
      >&nbsp;
      <span>경매방</span>
    </el-menu-item>

    <el-menu-item index="6" style="height: 80px">
      <router-link to="/settings"
        ><img
          src="@\assets\imgs\settings.png"
          alt="Settings"
          class="icon" /></router-link
      >&nbsp;
      <span>환경설정</span>
    </el-menu-item>
  </el-menu>
</template>

<script lang="ts" setup>
import { ref } from "vue";

const isCollapse = ref(true);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
<style>
.icon {
  height: 40px;
  width: 40px;
  margin-left: auto;
  margin-right: auto;
  display: block;
}
.icon4 {
  height: 45px;
  width: 40px;
  margin-left: auto;
  margin-right: auto;
  display: block;
}
.target {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
.target::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
