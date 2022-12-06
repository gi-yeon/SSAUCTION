<template>
  <div>
    <h1>{{ timerStr }}</h1>
  </div>
</template>

<script>
export default {
  name: "SessionTimer",
  data() {
    return {
      timer: null,
      pauseTime: null,
      timeCounter: null,
      isPaused: false,
    };
  },
  props: {
    remainingTime: Number,
    isHost: Boolean,
    startTime: Number,
  },
  mounted() {
    this.timerStop(this.timer);
    this.timer = null;
  },
  computed: {
    timerStr() {
      if (this.remainingTime == 0 || this.remainingTime == undefined) {
        return "00:00";
      }
      let time = this.remainingTime;
      let minutes = parseInt(time / 60);
      let seconds = parseInt(time % 60);
      return (
        minutes.toString().padStart(2, "0") +
        ":" +
        seconds.toString().padStart(2, "0")
      );
    },
  },
  methods: {
    timerStart() {
      if (this.isHost) {
        if (this.timer !== null) {
          console.log("이미 카운트다운이 실행중입니다.");
          return;
        }
        console.log("start of timerStart()");

        if (this.isPaused) {
          this.timeCounter = this.pauseTime;
        } else {
          this.timeCounter = this.startTime;
        }
        this.$emit("tickTimer", this.timeCounter);

        this.timer = setInterval(() => {
          this.timeCounter--;
          console.log(`timeCounter: ${this.timeCounter}`);

          this.$emit("tickTimer", this.timeCounter);

          if (this.timeCounter <= 0) {
            this.timeCounter = 0;
            this.$emit("tickTimer", this.timeCounter);
            console.log("카운트다운이 종료되었습니다.");
            this.timerStop();
            this.$emit("finishAuction");
          }
        }, 1000);
      }
    },
    timerPause() {
      if (this.isHost) {
        clearInterval(this.timer);
        this.pauseTime = this.timeCounter;
        this.isPaused = true;
        this.timer = null;
      }
    },
    timerStop() {
      if (this.isHost) {
        if (this.timer !== null) {
          clearInterval(this.timer);
        }
        this.timeCounter = 0;
        this.isPaused = false;
        this.timer = null;
      }
    },
    timerReset() {
      if (this.isHost) {
        this.timeCounter = this.startTime;

        console.log(`timeCounter: ${this.timeCounter}`);
      }
    },
    formatTime() {
      console.log(this.remainingTime);
      let time = this.remainingTime;
      let minutes = parseInt(time / 60);
      let seconds = parseInt(time % 60);
      return (
        minutes.toString().padStart(2, "0") +
        ":" +
        seconds.toString().padStart(2, "0")
      );
    },
  },
};
</script>

<style></style>
