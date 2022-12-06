// BoardDetail.vue

<template>
  <div>
    <input
      style="margin-top: 20px"
      type="text"
      name="title"
      id="title"
      v-model="state.boardTitle"
      readonly
    />

    <div></div>
    <textarea
      style="margin-top: 20px"
      name="boardContent"
      id="boardContent"
      cols="60"
      rows="10"
      v-model="state.boardContent"
      readonly
    >
    </textarea>

    <div style="margin-top: 20px">
      <button class="btn_pink" v-show="state.isWriter" @click="ModifyArticle">
        글수정</button
      >&nbsp;&nbsp;
      <button class="btn_pink" v-show="state.isWriter" @click="DeleteArticle">
        글삭제</button
      >&nbsp;&nbsp;
      <router-link to="/board">
        <button class="btn_yellow">목록</button>
      </router-link>
    </div>
    <div class="comment">
      <board-comment-item
        style="text-align: left"
        v-for="(comment, index) in state.comments"
        :key="index"
        v-bind="comment"
      ></board-comment-item>
    </div>

    <div>
      <textarea
        style="margin-top: 20px"
        name="commentRegister"
        id="commentRegister"
        cols="50"
        rows="2"
        v-model="state.commentContent"
      ></textarea
      >&nbsp;&nbsp;
      <button class="btn_pink2" @click="registerComment">✉</button>
    </div>
    <div></div>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import { reactive } from "vue";
import { useRouter, useRoute } from "vue-router";
import BoardCommentItem from "@/components/Board/item/BoardCommentItem";
// npm install universal-cookie

export default {
  name: "BoardDetail",
  components: {
    BoardCommentItem,
  },
  mounted() {
    this.state.userNo = this.$store.getters["user/userNo"];
  },

  setup() {
    const router = useRouter();
    const route = useRoute();

    const state = reactive({
      boardNo: Number,
      boardTitle: String,
      boardContent: String,
      userNo: Number,
      userNickname: String,
      comments: [],
      commentContent: "",
      isWriter: Boolean,
      // hasComment: Boolean,
    });

    const ModifyArticle = () => {
      router.push({ name: "boardModify", params: { id: state.boardNo } });
    };

    const DeleteArticle = () => {
      axios
        .delete("board", {
          params: {
            boardNo: route.params.id,
            userNo: state.userNo,
          },
        })
        .then(() => {
          alert("게시글을 삭제하였습니다.");
        });

      router.push({ path: "/board" });
    };

    const registerComment = () => {
      let params = {
        boardNo: state.boardNo,
        userNo: state.userNo,
        commentContent: state.commentContent,
      };
      console.log("comment");
      console.log(state.commentContent);
      axios.post("comment", JSON.stringify(params)).then(() => {
        alert("댓글을 등록하였습니다.");
        router.go();
      });
    };

    axios.get("board/detail/" + route.params.id).then(({ data }) => {
      state.boardNo = data.board.boardNo;
      state.boardTitle = data.board.boardTitle;
      state.boardContent = data.board.boardContent;
      state.userNickname = data.board.userNickname;
      state.comments = data.board.comments;

      // console.log(state.comments.length)

      // if(state.comments.length > 0){
      //   state.hasComment = true;
      //   console.log(state.hasComment)
      // }

      if (state.userNo != null && data.board.userNo == state.userNo)
        state.isWriter = true;
      else state.isWriter = false;
    });

    return {
      state,
      ModifyArticle,
      DeleteArticle,
      registerComment,
    };
  },
};
</script>

<style>
.title {
  width: 400px;
  border: 20px;
  /* background-color: rgba(255, 211, 182, 0.741); */
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
}

textarea:focus {
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
  margin-bottom: 20px;
  width: 60px;
  height: 40px;
  border: 0;
  background-color: rgba(255, 169, 165, 0.7);
  border-radius: 10px;
  color: rgb(94, 94, 94);
  text-align: center;
  position: relative;
  bottom: 40px;
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

.comment {
  width: 550px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 1rem;
  padding: 20px;
  /* background-color: rgba(158, 158, 158, 0.212); */
  border: none;
  border-radius: 10px;
}
</style>