<template>
  <div>
    <br />
    <br />
    <h2>비밀번호 변경</h2>
    <br />
    <br />

    <div>
      <!-- 기존 비밀번호  -->
      <label for="userPwd" class="pwdUpdateLabel"
        ><button class="pwdLabelBtn" disabled>기존 비밀번호</button></label
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
      <!-- 새 비밀번호 -->
      <label for="newPwd" class="pwdUpdateLabel"
        ><button class="pwdLabelBtn" disabled>새로운 비밀번호</button></label
      >
      <input
        class="pwdInput"
        type="password"
        name="newPwd"
        id="newPwd"
        v-model="newPwd"
        placeholder="8~20자의 새로운 비밀번호를 입력해주세요."
        :state="pwdValidation"
      />
      <br />
      <br />

      <!-- 비밀번호 확인 -->
      <label for="newPwdCheck" class="pwdUpdateLabel"
        ><button class="pwdLabelBtn" disabled>비밀번호 확인</button></label
      >
      <input
        class="pwdInput"
        type="password"
        name="newPwdCheck"
        id="newPwdCheck"
        v-model="newPwdCheck"
        placeholder="새 비밀번호를 다시 입력해주세요"
        :state="pwdCheckValidation"
      />
      <br />
      <br />

      <button @click="updatePwd" width="500px" class="btn_update">확인</button>
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
      newPwd: "",
      newPwdCheck: "",
    };
  },
  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
  },
  computed: {
    pwdValidation() {
      return this.newPwd.length >= 8 && this.newPwd.length <= 20;
    },
    pwdCheckValidation() {
      return this.newPwd == this.newPwdCheck;
    },
  },
  methods: {
    updatePwd() {
      const obj = {
        userNo: this.userNo,
        userPwd: this.userPwd,
      };

      //validation 모두 만족했다면
      if (this.pwdValidation && this.pwdCheckValidation) {
        console.log(obj);
        //기존 비밀번호 올바르게 입력했는지 확인 후 진행
        axios.post("/users/pwdCheck", JSON.stringify(obj)).then((res) => {
          console.log(res);
          if (res.data === "success") {
            axios
              .put(
                "/users/profile/resetPwd/" + this.userNo,
                JSON.stringify(this.newPwd)
              )
              .then((res) => {
                if (res.data === "success") {
                  alert("비밀번호가 변경되었습니다.");
                  this.$router.push("/");
                } else {
                  alert("실패");
                }
              });
          }
          //비밀번호가 올바르지 않다면
          else {
            alert("비밀번호를 올바르게 입력해주세요.");
          }
        });
      }
      //validation 만족하지 않는 경우
      else if (!this.pwdValidation) {
        alert("8~20자의 비밀번호를 입력해주세요.");
      } else if (!this.pwdCheckValidation) {
        alert("비밀번호 재입력을 확인해주세요.");
      }
    },
  },
};
</script>

<style>
.pwdLabelBtn {
  width: 130px;
  border-radius: 4px;
}
.pwdInput {
  margin-left: 10px;
  margin-right: 10px;
  height: 50px;
  width: 400px !important;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.pwdUpdateLabel {
  width: 150px;
}
</style>