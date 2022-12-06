<template>
  <div>
    <input style="margin-top:20px" type="text" name="title" id="title" v-model="article.title" />
    <br>
    <br>

    <textarea
      name="content"
      id="content"
      cols="60"
      rows="10"
      v-model="article.content"
    >
    </textarea>
    <br>

    <div style="margin-top:20px">
      <button class="btn_pink" @click="registerArticle">글작성</button>
      <router-link to="/board">&nbsp;&nbsp;
        <button class="btn_yellow">목록</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "@/utils/axios";
import { useRouter } from "vue-router";
// npm install universal-cookie
import { useStore } from "vuex";
import { mapState } from "vuex";

export default {
  name: "BoardRegister",
  computed: {
    ...mapState(["user"]),
  },
  setup() {
    const router = useRouter();
    const store = useStore();

    const article = reactive({
      title: "",
      content: "",
    });

    const registerArticle = () => {
      //   console.log(store.state.user.accessToken);
      //   console.log(cookies.get("accessToken"));
      let params = {
        boardTitle: article.title,
        boardContent: article.content,
        boardType: store.state.board.boardType,
        userNo: store.getters["user/userNo"],
        userNickname: store.getters["user/userNickname"],
      };
      console.log(params);
      axios
        .post("board", JSON.stringify(params))
        .then(() => {})
        .catch(() => {
          alert("글 등록에 실패하였습니다.");
        });

      router.push({ path: "/board" });
    };

    return {
      article,
      registerArticle,
    };
  },
};
</script>


<style>
.title {
  width: 400px;
  border: 20px;
  background-color: rgba(255, 211, 182, 0.741);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left: 30px;
}

textarea {
  outline: none;
  resize: none;
  padding: 20px;
  background-color: rgba(158, 158, 158, 0.212);
  border: none;
  border-radius: 10px;
  width: 500px;
}

textarea:focus{
  outline: none;
}
.btn_pink {
  width: 70px;
  height: 40px;
  border: 0;
  background-color: rgba(255, 169, 165, 0.7);
  border-radius: 10px;
  color: rgb(94, 94, 94);
  text-align: center;
}

.btn_yellow {
  width: 70px;
  height: 40px;
  border: 0;
  background-color: rgb(255, 211, 182, 0.7);
  border-radius: 10px;
  color: rgb(94, 94, 94);
  text-align: center;
}
</style>