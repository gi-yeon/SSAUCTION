<template>
  <div class="container main-house" @click="joinSession">
    <div class="row">
      <div class="col-6">
        <img
          class="itemimg d-block m-3"
          :src="'data:image/png;base64,' + this.like.itemImgList[0]"
          alt=""
        />
      </div>
      <div class="col-6">
        <div class="row">{{ this.like.house.houseTitle }}</div>
        <div class="row">{{ this.like.item.sellerNickname }}</div>
        <div class="row" v-if="isSelling">{{ participantNum }}</div>
        <div class="row" v-if="!isSelling">
          {{ this.like.house.houseAucTime }}
        </div>
        <div class="row" v-if="isSelling">
          <div class="col">{{ houseAuctionCurrentPrice }}</div>
          <div class="col">{{ houseAuctionRemainingTime }}</div>
        </div>
        <div class="row" v-if="!isSelling">
          <div>{{ this.like.house.houseDesc }}</div>
        </div>
        <div><button @click="like">찜 취소</button></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  name: "LikeHouses",

  data() {
    return {
      participantNum: 123,
      houseAuctionCurrentPrice: 100,
      houseAuctionRemainingTime: "01:00",
      isSelling: false,
    };
  },
  props: {
    like: Object,
  },
  mounted() {},
  methods: {
    joinSession() {
      console.log(`houseNo : ${this.like.house.houseNo}`);
      this.$router.push({
        name: "Sessions",
        params: { sessionId: this.like.house.houseNo },
      });
    },
  },
};
</script>

<style>
.main-house {
  display: inline-block;
  width: 25%;
  height: 100%;
}
.itemimg {
  height: auto;
  width: 90%;
}
</style>
