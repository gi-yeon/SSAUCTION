<template>
  <div class="sender">
    {{ sender }}
  </div>
  <div class="message">
    {{ message }}
  </div>
</template>

<script>
export default {
  name: "ChatMessage",
  props: {
    message: null,
    sender: null,
    timestamp: null,
  },
};
</script>

<style>
.sender {
  font-weight: bold;
  color: black;
}
.message {
  padding-left: 10px;
  color: black;
}
</style>
