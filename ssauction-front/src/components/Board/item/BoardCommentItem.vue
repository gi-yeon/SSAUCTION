// BoardCommentItem.vue

<template>
  <div>
    <div class="commentContainer">
      {{ commentContent }}

      <p style="float: right">
        <span>{{ userNickname }}</span> &nbsp;
        <span>{{ commentRegDate }}</span>
        &nbsp;

        <button
          class="btn_delete2"
          v-show="state.isWriter"
          @click="deleteComment()"
        >
          X
        </button>
      </p>
    </div>
    <br />
  </div>
</template>

<script>
import axios from "@/utils/axios";
import { reactive } from "vue";
import { useRouter } from "vue-router";
// npm install universal-cookie
import { useCookies } from "@vueuse/integrations/useCookies";

export default {
  name: "BoardCommentItem",
  props: {
    commentNo: Number,
    commentContent: String,
    commentRegDate: String,
    boardNo: Number,
    userNo: Number,
    userNickname: String,
  },
  setup(props) {
    const router = useRouter();
    const cookies = useCookies(["login.userNo"]);

    const state = reactive({
      isWriter: Boolean,
    });
    if (
      cookies.get("login.userNo") != null &&
      props.userNo == cookies.get("login.userNo")
    )
      state.isWriter = true;
    else state.isWriter = false;

    const deleteComment = () => {
      axios
        .delete("comment", {
          params: {
            commentsNo: props.commentNo,
            userNo: cookies.get("login.userNo"),
          },
        })
        .then(() => {
          alert("댓글을 삭제하였습니다.");
          router.go();
        });
    };

    return {
      state,
      deleteComment,
    };
  },
};
</script>


<style scoped>
.btn_delete2 {
  width: 25px;
  height: 25px;
  padding: 0.1rem;
  border: 0;
  background-color: rgb(221, 221, 221);
  border-radius: 45px;
  color: rgb(94, 94, 94);
  text-align: center;
}
.commentContainer {
  border-top-style: none;
  border-bottom-style: dotted;
  border-left-style: none;
  border-right-style: none;
}
</style>