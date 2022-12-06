<template>
  <div v-if="streamManager">
    <main-ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
import MainOvVideo from "@/components/Session/MainOvVideo.vue";

export default {
  name: "MainVideo",

  components: {
    MainOvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    clientData() {
      const clientData = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      const d = JSON.parse(connection.data.split("%/%")[0]).clientData;
      console.log(d);
      return d;
    },
  },
};
</script>
<style scoped>
p {
  position: absolute;
  display: inline-block;
  background: #f8f8f8;
  padding-left: 5px;
  padding-right: 5px;
  font-size: 22px;
  color: #777777;
  font-weight: bold;
  border-bottom-right-radius: 4px;
}
</style>
