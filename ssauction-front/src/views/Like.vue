<template>
  <br />
  <br />
  <h1>찜 목록</h1>
  <br />
  <like-houses v-for="(like, index) in likes" :key="index" :like="like">
  </like-houses>
</template>

<script>
import axios from "@/utils/axios";
import LikeHouses from "@/components/LikeHouses.vue";

export default {
  name: "SsauctionZzim",
  components: {
    LikeHouses,
  },
  data() {
    return {
      userNo: null,

      likes: [],
    };
  },

  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    this.getLikes();
  },

  methods: {
    getLikes: async function () {
      console.log("start getLikes");
      axios
        .get("/likes/" + this.userNo)
        .then(({ data }) => {
          console.log("success");
          console.log(data);
          this.likes = data;
        })
        .catch(({ res }) => {
          console.log(res);
        });
    },
  },
};
</script>

<style></style>
