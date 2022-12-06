<template>
  <div>
    <input style="margin-top:20px"
    type="text" name="title" id="title" v-model="state.boardTitle" />
    <div></div>
    <textarea
      style="margin-top:20px"
      name="content"
      id="content"
      cols="60"
      rows="10"
      v-model="state.boardContent"
    >
    
    </textarea>

    <div style="margin-top:20px">
      <button class="btn_pink" @click="ModifyArticle">수정</button>&nbsp;&nbsp;
      <router-link to="/board">
        <button class="btn_yellow">목록</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import { reactive } from "vue";
import { useRouter, useRoute } from "vue-router";

export default {
  name: "BoardModify",
  setup() {
    const router = useRouter();
    const route = useRoute();

    const state = reactive({
      boardNo: Number,
      boardTitle: String,
      boardContent: String,
      userNo: Number,
      userNickname: String,
    });

    const ModifyArticle = () => {
      let params = {
        boardNo: state.boardNo,
        boardTitle: state.boardTitle,
        boardContent: state.boardContent,
      };

      axios.put("board", JSON.stringify(params)).then(() => {
        alert("수정이 완료되었습니다.");
      });
      router.push({ name: "boardDetail", params: { id: state.boardNo } });
    };

    axios
      .get("board/detail", {
        params: {
          boardNo: route.params.id,
        },
      })
      .then(({ data }) => {
        state.boardNo = data.board.boardNo;
        state.boardTitle = data.board.boardTitle;
        state.boardContent = data.board.boardContent;
        state.userNo = data.board.userNo;
        state.userNickname = data.board.userNickname;
      });

    return {
      state,
      ModifyArticle,
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

textarea{
  outline: none;
  resize: none;
  padding: 20px;
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

.btn_pink2 {
  width: 80px;
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