<template>
  <div>
    <br />
    <br />
    <h2>회원탈퇴</h2>
    <br />
    <br />

    <div>
      <!-- 기존 비밀번호  -->
      <label for="userPwd" class="profileLabel"
        ><button class="pwdLabelBtn" disabled>비밀번호</button></label
      >
      <input
        class="pwdInput"
        type="password"
        name="userPwd"
        id="userPwd"
        v-model="userPwd"
        placeholder="비밀번호를 입력해주세요."
      />
      <br />
      <br />

      <button @click="deleteUser" width="500px" class="btn_update">탈퇴</button>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";

export default {
  name: "PwdUpdate",
  data() {
    return {
      userNo: 0,
      userPwd: "",
    };
  },
  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
  },

  methods: {
    deleteUser() {
      const obj = {
        userNo: this.userNo,
        userPwd: this.userPwd,
      };

      console.log(obj);
      //기존 비밀번호 올바르게 입력했는지 확인 후 진행
      axios.post("/users/pwdCheck", JSON.stringify(obj)).then((res) => {
        console.log(res);
        if (res.data === "success") {
          axios.put("/users/deleteUser/" + this.userNo).then(() => {
            this.$store.dispatch("user/deleteUserLogout");
            alert("성공적으로 탈퇴되었습니다.");
            this.$router.push("/");
          });
        }
        //비밀번호가 올바르지 않다면
        else {
          alert("비밀번호를 올바르게 입력해주세요.");
        }
      });
    },
  },
};
</script>

<style>
.pwdLabelBtn {
  margin: 10px;
  width: 130px;
  border-radius: 4px;
}
.pwdInput {
  margin-left: 10px;
  margin-right: 10px;
  height: 50px;
  width: 200px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
</style>