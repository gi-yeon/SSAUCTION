import { USER } from "../mutation-types";
import axios from "axios";
import router from "@/router";
import VueCookies from "vue-cookies";



const state = {
  //로그인 한 유저의 정보
  loginUser: {
    userNo: "",
    userNickname: "",
    userGrade: ""
  },
  // accessToken: "",
  // refreshToken: "",
  //로그인 여부
  isLogin: false,
};

const getters = {
  userNickname: (state) => {
    return `${state.loginUser.userNickname}`;
  },
  userNo: (state) => {
    return `${state.loginUser.userNo}`;
  },
  userGrade: (state) => {
    return `${state.loginUser.userGrade}`;
  },
  isLogin: (state) => {
    return `${state.isLogin}`;
  }
};

const actions = {
  //로그인
  userLogin({ commit }, loginInfo) {
    axios.post("/users/login", JSON.stringify(loginInfo)).then((res) => {
      console.log(res)
      console.log(res.data.userAuthority)
      //success 메시지가 떴다면
      if (res.data.message === "success" && (res.data.userAuthority === "ROLE_USER" || res.data.userAuthority === "ROLE_ADMIN")) {
        commit('USER_LOGIN', res);
        commit('SAVE_LOGIN_INFO');
        alert("로그인 되었습니다!");
        router.push('/'); //홈으로 이동
      }
      //success 메시지가 뜨지 않았다면 (fail)
      else {
        alert("이메일 또는 비밀번호가 일치하지 않습니다.");
        router.push({ name: 'userLogin' });
      }
    })
  },

  //로그아웃
  userLogout({ commit }) {
    axios.post("/users/logout").then(() => {
      commit('USER_LOGOUT')
      commit('RESET_LOGIN_INFO')

      alert("로그아웃되었습니다. 다음에 또 만나요 ^____^")
      router.push('/'); //홈으로 이동

    })

  },


  //회원탈퇴 후 로그아웃
  deleteUserLogout({ commit }) {
    axios.post("/users/logout").then(() => {
      commit('USER_LOGOUT')
      commit('RESET_LOGIN_INFO')

      router.push('/'); //홈으로 이동

    })

  },

  //정보 수정 후 쿠키와 state 정보 업데이트
  getUpdateInfo({ commit }, userNickname) {
    commit('USER_UPDATE', userNickname);
    commit('SAVE_UPDATE_INFO');
    router.push('/'); //홈으로 이동
  },

  //로그인 정보 가져오기
  getLoginInfo({ commit }) {
    commit('GET_LOGIN_INFO')
  },

  //토큰 재발급 후 state에 다시 넣기
  setLoginState({ commit }, res) {
    commit('USER_LOGIN', res)
  },
  //토큰 재발급 후 state로 쿠키생성
  setLoginCookie({ commit }) {
    commit('SAVE_LOGIN_INFO')
  },

  setNickname({ commit }, value) {
    commit(USER.SET_NICKNAME, value);
  },
  setGrade({ commit }, value) {
    commit(USER.SET_GRADE, value);
  },
  setUserNo({ commit }, value) {
    commit(USER.SET_USERNO, value);
  },
  setIsLogin({ commit }, value) {
    commit(USER.SET_ISLOGIN, value);
  }
};

const mutations = {

  [USER.SET_NICKNAME](state, value) {
    state.nickname = value;
  },
  [USER.SET_GRADE](state, value) {
    state.grade = value;
  },
  [USER.SET_USERNO](state, value) {
    state.userNo = value;
  },

  //로그인
  USER_LOGIN(state, payload) {
    state.isLogin = true;
    state.loginUser.userNo = payload.data.userNo;
    state.loginUser.userNickname = payload.data.userNickname;
    state.loginUser.userGrade = payload.data.userGrade;
    // state.accessToken = payload.data.accessToken;
    // state.refreshToken = payload.data.refreshToken;
  },

  //닉네임 업데이트
  USER_UPDATE(state, payload) {
    state.loginUser.userNickname = payload;
  },


  //로그아웃
  USER_LOGOUT(state) {
    state.isLogin = false;
    // state.accessToken = null;
    // state.refreshToken = null;
    state.loginUser = {
      userNo: "",
      userNickname: "",
      userGrade: ""
    }
  },
  //쿠키에 로그인 정보 저장
  SAVE_LOGIN_INFO(state) {
    VueCookies.set("login.userNo", state.loginUser.userNo, '30min');
    VueCookies.set("login.userNickname", state.loginUser.userNickname, '30min');
    VueCookies.set("login.userGrade", state.loginUser.userGrade, '30min');
    VueCookies.set("isLogin", state.isLogin, '30min');
    // VueCookies.set("accessToken", state.accessToken, '30min');
    // VueCookies.set("refreshToken", state.refreshToken, '3d');
  },


  //쿠키에 업데이트된 닉네임 정보 저장
  SAVE_UPDATE_INFO(state) {
    VueCookies.set("login.userNickname", state.loginUser.userNickname, '30min');
  },

  //쿠키에 있는 로그인 정보 읽어오기
  GET_LOGIN_INFO(state) {
    if (VueCookies.get("login.userNo") != null) {
      state.loginUser.userNo = VueCookies.get("login.userNo")
    }
    if (VueCookies.get("login.userNickname") != null) {
      state.loginUser.userNickname = VueCookies.get("login.userNickname")
    }
    if (VueCookies.get("login.userGrade") != null) {
      state.loginUser.userGrade = VueCookies.get("login.userGrade")
    }
    if (VueCookies.get("isLogin") != null) {
      state.isLogin = VueCookies.get("isLogin")
    }
  },

  //쿠키에 있는 로그인 정보 삭제
  RESET_LOGIN_INFO() {
    VueCookies.remove("login.userNo");
    VueCookies.remove("login.userNickname");
    VueCookies.remove("login.userGrade");
    VueCookies.remove("isLogin");
    // VueCookies.remove("accessToken");
    // VueCookies.remove("refreshToken");

  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
