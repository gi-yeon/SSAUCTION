<template>
  <div id="join">
    <!-- <div id="img-div"> -->
    <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" /> -->
    <!-- </div> -->
    <div id="join-dialog" class="vertical-center">
      <div class="welcome-message m-5"><h1>경매장에 입장합니다.</h1></div>
      <section
        class="form-group session-join-panel row d-flex justify-content-center"
      >
        <div class="" style="width: 50%">
          <div class="m-5">
            <button class="btn btn-light m-3 py-1" style="width: 10rem;" disabled>방 이름</button>
            <h1>{{ sessionName }}</h1>
          </div>
          <div class="m-5">
            <button class="btn btn-light my-3 py-1" style="width: 10rem;" disabled>닉네임</button>
            <input
              placeholder="다른 사람들에게 보여질 닉네임을 입력하세요(최대 10글자)"
              v-model="myUserName"
              class="form-control"
              style="text-align: center;"
              type="text"
              maxlength="10"
              required
            />
          <p class="my-3 invalid-input-text" v-if="!isValidUsername">닉네임을 입력하세요</p>
          <p class="my-3 invalid-input-text" style="color: greenyellow;" v-if="isValidUsername">사용 가능한 닉네임입니다</p>
          </div>
          <div class="session-setting">
            <div
              class="session-btn btn-toggle-audio m-4"
              @click="$emit('toggleAudio')"
            >
              <div v-if="isAudioOn" style="color: green">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-mic-fill"
                  viewBox="0 0 16 16"
                >
                  <path d="M5 3a3 3 0 0 1 6 0v5a3 3 0 0 1-6 0V3z" />
                  <path
                    d="M3.5 6.5A.5.5 0 0 1 4 7v1a4 4 0 0 0 8 0V7a.5.5 0 0 1 1 0v1a5 5 0 0 1-4.5 4.975V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 .5-.5z"
                  />
                </svg>
                <p>마이크 켜짐</p>
              </div>
              <div v-else style="color: red">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-mic-mute-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M13 8c0 .564-.094 1.107-.266 1.613l-.814-.814A4.02 4.02 0 0 0 12 8V7a.5.5 0 0 1 1 0v1zm-5 4c.818 0 1.578-.245 2.212-.667l.718.719a4.973 4.973 0 0 1-2.43.923V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 1 0v1a4 4 0 0 0 4 4zm3-9v4.879L5.158 2.037A3.001 3.001 0 0 1 11 3z"
                  />
                  <path
                    d="M9.486 10.607 5 6.12V8a3 3 0 0 0 4.486 2.607zm-7.84-9.253 12 12 .708-.708-12-12-.708.708z"
                  />
                </svg>

                <p>마이크 꺼짐</p>
              </div>
            </div>
            <div
              class="session-btn btn-toggle-camera m-4"
              @click="$emit('toggleVideo')"
            >
              <div v-if="isVideoOn" style="color: green">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-camera-video-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M0 5a2 2 0 0 1 2-2h7.5a2 2 0 0 1 1.983 1.738l3.11-1.382A1 1 0 0 1 16 4.269v7.462a1 1 0 0 1-1.406.913l-3.111-1.382A2 2 0 0 1 9.5 13H2a2 2 0 0 1-2-2V5z"
                  />
                </svg>
                <p>카메라 켜짐</p>
              </div>
              <div v-else style="color: red">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-camera-video-off-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M10.961 12.365a1.99 1.99 0 0 0 .522-1.103l3.11 1.382A1 1 0 0 0 16 11.731V4.269a1 1 0 0 0-1.406-.913l-3.111 1.382A2 2 0 0 0 9.5 3H4.272l6.69 9.365zm-10.114-9A2.001 2.001 0 0 0 0 5v6a2 2 0 0 0 2 2h5.728L.847 3.366zm9.746 11.925-10-14 .814-.58 10 14-.814.58z"
                  />
                </svg>

                <p>카메라 꺼짐</p>
              </div>
            </div>
          </div>
          <div class="text-center">
            <button
              class="btn btn-lg btn-success mx-3"
              @click="$emit('joinSession', myUserName)"
              :disabled="!isValidUsername"
            >
              입장하기
            </button>
            <button class="btn btn-lg btn-success mx-3" @click="backToMain">
              돌아가기
            </button>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  name: "JoinSession",
  props: {
    isAudioOn: Boolean,
    isVideoOn: Boolean,
    sessionName: String,
    isInvalidUsername: Boolean,
  },
  beforeMount() {
    this.store = useStore();
  },
  data() {
    return {
      myUserName: null,
      startTime: 30,
    };
  },

  computed: {
    isValidUsername() {
      return this.myUserName ? true : false;
    },
  },
  methods: {
    backToMain() {
      this.$store.dispatch("session/setIsInSession", false);
      this.$router.push({
        name: "Home",
      });
    },
  },
};
</script>

<style>
.session-btn {
  display: inline-block;
}
.invalid-input-text {
  color: red;
  font-weight: bolder;
}
.session-join-panel {
  justify-content: center;
  align-items: center;
}
</style>
