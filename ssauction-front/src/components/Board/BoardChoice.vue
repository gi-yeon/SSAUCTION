<template>
  <div class="board">
    <div class="justify-content-center margin-top:15px">
      <div class="choice">
        <span @click="setNotice" style="cursor: pointer">공지사항</span>
        &nbsp; &nbsp; | &nbsp; &nbsp;
        <span @click="setFree" style="cursor: pointer">자유 게시판</span>
      </div>
    </div>
    <div class="line"></div>
  </div>
</template>

<script>
export default {
  name: "BoardChoice",
  methods: {
    setNotice() {
      this.$store.dispatch("board/setBoardType", 1);
      console.log(this.$store.state.board.boardType);
      this.$router.go();
    },

    setFree() {
      this.$store.dispatch("board/setBoardType", 0);
      console.log(this.$store.state.board.boardType);
      this.$router.go();
    },
  },
};
</script>

<style>
.btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>