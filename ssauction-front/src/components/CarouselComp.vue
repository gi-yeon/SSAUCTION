<template>
<div>
  <div class="container" style="position:relative" >
    <div class="clipping-container">
      <div class="pages" :style="{ left: currentLeft }" >
        <div v-for="index in images" :key="index" >

          <div class="card page" >
            <div style="margin:auto;">
              <div>
                <img
                 class="imgResize"
                 style="margin-left:auto; margin-right:auto;"
                  v-bind:src="'data:image/png;base64,' + index.img"
                  @click="clickimg"
                />
                <br />
              </div>
            </div>
          </div>
          </div>

        </div>
      </div>
    </div>
    <div class="dots">
      <span
        v-for="index in totalPage"
        :class="{ dot: true, 'dot-current': isCurrentPage(index) }"
        :key="index"
      >
      </span>
    </div>
    <div>
      <button class="btn-arrow2" @click="backwardPage">←</button>&nbsp;&nbsp;
      <button class="btn-arrow2" @click="forwardPage">→</button>
    </div>
</div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  name: "HelloWorld",
  props: { images: Array },
  data() {
    return {
      currentPage: 1,
      totalPage: 1,
      pageWidth: 200,
    };
  },

  mounted() {
    console.log("carousel")
    console.log(this.images)
  },

  methods: {
    forwardPage() {
      console.log(this.totalPage);
      if (this.currentPage === this.totalPage) {
        return;
      }
      this.currentPage += 1;
    },

    backwardPage() {
      if (this.currentPage === 1) {
        return;
      }
      this.currentPage -= 1;
    },

    pageToPosition(): number {
      return -this.pageWidth * (this.currentPage - 1);
    },

    isCurrentPage(page: number) {
      return this.currentPage === page;
    },
    clickimg() {
      console.log(this.images);
    },
  },
  watch: {
    images() {
      const vm = this;
      vm.totalPage =
        vm.images?.length == null ? vm.totalPage : vm.images?.length;
      console.log("totalPage");
      console.log(vm.totalPage);
    },
  },
  computed: {
    currentLeft(): string {
      return String(this.pageToPosition()) + "px";
    },
  },
});
</script>

<style scoped>
.clipping-container {
  clip-path: inset(0);
  position: relative;
  height: 200px;
  width: 200px;
  display: inline-block;
}
.pages {
  display: flex;
  /* ここにtransitionをつけることでpagesのleftを変更した場合にアニメーションさせることができる */
  /* 이 트랜지션은 페이지를 왼쪽으로 넘길때 애니메이션 효과가 나타나도록 합니다 */
  transition: left 0.5s ease;
  position: absolute;
  left: 0;
}
.page {
  width: 200px;
  height: 200px;
  background-color: white;
  border: 1px solid white;
  box-sizing: border-box;
}
.dots {
  width: 30px;
  display: flex;
  justify-content: space-between;
  margin: auto;
  margin-bottom: 16px;
}
.dot {
  height: 8px;
  width: 8px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
.dot-current {
  background-color: rgba(255, 169, 165, 0.549);
}

.imgTag {
  display: block;
  margin: auto;
}
.imgResize {
  width: 100%;
  height: 100%;
}

.btn-arrow2{
  background-color: #f8e6e0;
  color: #6e6e6e;
  position: relative;
  border: none;
  display: inline-block;
  padding: 10px 15px;
  border-radius: 15px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin-top: 10px;
}


</style>
