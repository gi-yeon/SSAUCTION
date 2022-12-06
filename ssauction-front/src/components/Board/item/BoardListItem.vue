<template>
  <tr class="up">
    <td>{{boardNo}}</td>
    <td @click="boardDetail()">{{boardTitle}}</td>
    <td>{{userNickname}}</td>
    <td>{{boardRegDate}}</td>
    
  </tr>
</template>

<script>
import {useRouter } from 'vue-router';

export default {
  name: "BoardListItem",
  props: {
    boardNo: Number,
    boardTitle: String,
    boardRegDate: String,
    boardReply: Number,
    boardType: String,
    userNickname: String,
    userNo: Number,
  },
  setup(props) {
    const router = useRouter();

    const boardDetail = () => {
      router.push({name: "boardDetail", params: {id: props.boardNo}});
    }

    return {
      boardDetail
    }
  }
}
</script>