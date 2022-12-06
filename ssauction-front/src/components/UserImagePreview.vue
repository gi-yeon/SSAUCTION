<template>
  <div class="wrapper">
    <div
      class="imagePreview"
      :style="{ 'background-image': `url(${previewimage})` }"
    ></div>
    <div>{{ this.previewfile.name }}</div>
    <div><button @click="$emit('deleteImg', this.index)">삭제</button></div>
  </div>
</template>

<script>
export default {
  data() {
    return { previewimage: null };
  },
  props: {
    previewfile: File,
    index: Number,
  },
  mounted() {
    this.setImage();
  },
  methods: {
    setImage() {
      let reader = new FileReader();
      reader.onload = (e) => {
        this.previewimage = e.target.result;
      };
      reader.readAsDataURL(this.previewfile);
    },
  },
};
</script>

<style scoped>
.wrapper {
  display: inline-block;
}
.imagePreview {
  width: 200px;
  height: 200px;
  float: none;
  margin: 0 auto 30px;
  background-size: cover;
  background-position: center center;
}
</style>