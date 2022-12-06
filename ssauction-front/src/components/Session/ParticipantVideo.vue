<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div>
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "ParticipantVideo",

  components: {
    OvVideo,
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
      const d = JSON.parse(connection.data).clientData;
      console.log(d);
      return d;
    },
  },
};
</script>
<style scoped>
p {
  display: inline-block;
  background: #f8f8f8;
  padding-left: 5px;
  padding-right: 5px;
  color: #777777;
  font-weight: bold;
  border-bottom-right-radius: 4px;
}
video + div {
  width: 50%;
  position: relative;
  margin-left: -50%;
}
video {
  position: relative;
  width: 100%;
  cursor: pointer;
}
div {
  display: inline-block;
  width: 20%;
}
</style>
