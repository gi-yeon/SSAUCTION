import { createWebHistory, createRouter } from "vue-router";
// import { useCookies } from "vue3-cookies";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import Test from "@/views/Test.vue";
import FindId from "@/views/FindId.vue";
import ResetPwd from "@/views/ResetPwd.vue";
import ResetPwd2 from "@/views/ResetPwd2.vue";
import House from "@/views/House.vue";
import Profile from "@/views/Profile.vue";
import Like from "@/views/Like.vue";
import Settings from "@/views/Settings.vue";
import Sessions from "@/views/Sessions.vue";
import PwdUpdate from "@/views/PwdUpdate.vue";
import DeleteUser from "@/views/DeleteUser.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: "/test",
    name: "Test",
    component: Test,
  },
  {
    path: "/findid",
    name: "FindId",
    component: FindId,
  },
  {
    path: "/resetpwd",
    name: "ResetPwd",
    component: ResetPwd,
  },
  {
    path: "/resetpwd2",
    name: "ResetPwd2",
    component: ResetPwd2,
    props: true,
  },
  {
    path: "/house",
    name: "House",
    component: House,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/sessions",
    name: "Sessions",
    component: Sessions,
  },
  {
    path: "/pwdUpdate",
    name: "PwdUpdate",
    component: PwdUpdate,
  },
  {
    path: "/deleteUser",
    name: "DeleteUser",
    component: DeleteUser,
  },
  {
    path: "/zzim",
    name: "Zzim",
    component: Like,
  },
  {
    path: "/settings",
    name: "Settings",
    component: Settings,
  },
  {
    path: "/board",
    name: "Board",
    component: () => import("@/views/BoardView.vue"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("@/components/Board/BoardList.vue"),
      },
      {
        path: "detail/:id",
        name: "boardDetail",
        component: () => import("@/components/Board/BoardDetail.vue"),
      },
      {
        path: "modify/:id",
        name: "boardModify",
        component: () => import("@/components/Board/BoardModify.vue"),
      },
      {
        path: "regist",
        name: "boardRegister",
        component: () => import("@/components/Board/BoardRegister.vue"),
      },
    ],
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;
