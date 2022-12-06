<template>
  <h1>게시판</h1>
  <board-choice></board-choice>
  <router-view></router-view>
</template>

<script>
import BoardChoice from "@/components/Board/BoardChoice.vue";
export default {
  name: "BoardView",
  components: {
    BoardChoice,
  },
};
</script>