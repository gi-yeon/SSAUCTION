<template>
  <video ref="videoel" autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$refs.videoel);
  },
};
</script>
<style scoped>
video {
  width: auto;
}
</style>
