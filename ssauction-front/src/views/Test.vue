<template>
  <div id="app">
    <h2>{{ name }}</h2>
    <p>{{ personInfo }}</p>
    <input
      type="text"
      :value="name"
      @input="changeName"
      placeholder="이름을 작성해 주세요."
    />
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";

function useUser() {
  const store = useStore();

  const name = computed(() => store.state.user.name);
  const personInfo = computed(() => store.getters["user/userInfo"]);
  const changeName = (e) => store.dispatch("user/changeName", e.target.value);

  return {
    name,
    personInfo,
    changeName,
  };
}

export default {
  name: "App",
  setup() {
    return {
      ...useUser(),
    };
  },
};
</script>
