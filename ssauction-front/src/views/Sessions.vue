<template>
  <div id="session-container" class="container" style="height: 90%">
    <join-session
      v-if="!sessionCamera"
      :isAudioOn="isAudioOn"
      :isVideoOn="isVideoOn"
      :sessionName="sessionName"
      :startTime="startTime"
      @joinSession="joinSession"
      @switchModal="switchModal"
      @toggleVideo="toggleVideo"
      @toggleAudio="toggleAudio"
    />
    <div class="row" id="session" style="height: 100%" v-if="sessionCamera">
      <div class="row" id="session-header" style="height: 8%">
        <div class="col">
          <h1 id="session-title">{{ sessionName }}</h1>
        </div>
      </div>
      <div class="row" style="height: 92%">
        <div class="col-md-9" id="session-main-container" style="height: 100%">
          <div class="row" style="height: 25%">
            <div id="session-video-container">
              <participant-video
                class="session-publisher float-left m-1"
                :stream-manager="publisher"
                :key="publisher"
                ref="publisher"
                @click="updateMainVideoStreamManager(publisher)"
                style="display: inline-block"
              />
              <participant-video
                class="session-participant"
                v-for="sub in subscribersCamera"
                :key="sub"
                :stream-manager="sub"
                ref="subscribersCamera"
                @click="updateMainVideoStreamManager(sub)"
              />
            </div>
          </div>
          <div class="row" style="height: 75%">
            <div class="session-main-video" style="height: 40%">
              <main-video
                :stream-manager="mainStreamManager"
                :key="mainStreamManager"
              />
            </div>
          </div>
        </div>
        <div class="col-md-3 session-side-container">
          <div class="row mb-3">
            <div class="row mb-3" id="timer">
              <session-timer
                ref="timer"
                :remainingTime="remainingTime"
                :isHost="isHost"
                :startTime="startTime"
                @finishAuction="finishAuctionFromHost"
                @tickTimer="tickTimer"
              ></session-timer>
              <div>
                <button
                  v-if="!isHost && isStarted"
                  @click="openBid = !openBid"
                  :disabled="isFinished"
                  class="btn btn-warning"
                >
                  입찰하기
                </button>
                <p>제한시간 : {{ startTime }}초</p>
                <el-input-number
                  v-if="isHost"
                  :disabled="isStarted"
                  v-model="startTime"
                  :min="1"
                  :max="999"
                  @change="handleChange"
                />
                <button
                  v-if="isHost"
                  class="btn btn-warning"
                  :disabled="isStarted"
                  @click="startAuctionFromHost"
                >
                  경매 시작
                </button>
              </div>
            </div>
            <div class="row">최고 입찰자 : {{ currentBidder }}</div>
            <div class="row">최고 금액 : {{ formatMoney(currentPrice) }}</div>
          </div>
          <div class="row" id="session-chat-panel">
            <div class="row mb-3" id="session-chat-history">
              <el-tabs type="border-card">
                <el-tab-pane label="채팅"
                  ><div id="chat-history">
                    <el-scrollbar max-height="13rem"
                      ><chat-message
                        class="chat-message"
                        v-for="(m, index) in messageHistory"
                        :key="index"
                        :sender="m.sender"
                        :message="m.message"
                    /></el-scrollbar></div
                ></el-tab-pane>
                <el-tab-pane label="참여자">
                  <div id="participant-list">
                    <div class="participant-name">{{ myUserName }} (나)</div>
                    <div v-for="sub in subscribersCamera" :key="sub + 'c'">
                      <div class="participant-name">{{ getClientId(sub) }}</div>
                      <div v-if="isHost">
                        <button
                          class="kickout"
                          @click="kickoutFromHost(sub.stream.connection)"
                        >
                          강퇴
                        </button>
                      </div>
                    </div>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="mb-1" id="chat-control-panel">
              <el-select
                v-model="toWhisper"
                placeholder="메시지를 보낼 상대를 선택하세요"
                clearable
              >
                <el-option
                  v-for="sub in subscribersCamera"
                  :key="sub"
                  :label="getClientId(sub)"
                  :value="sub.stream.connection"
                />
              </el-select>
              <!-- <button class="btn btn-primary" @click="resetToWhisper">
                모두에게
              </button> -->
            </div>
            <div class="input-group" id="chat-input">
              <textarea
                v-model="message"
                class="form-control"
                type="text"
                v-on:keyup.enter="submitMessage"
              />
              <button
                class="btn btn-outline-secondary"
                type="button"
                @click="submitMessage"
              >
                전송
              </button>
            </div>
          </div>
        </div>
        <footer>
          <in-session-panel
            :isVideoOn="isVideoOn"
            :isAudioOn="isAudioOn"
            :isHost="isHost"
            :isFreeze="isFreeze"
            :isFinished="isFinished"
            @leaveSession="leaveSession"
            @toggleVideo="toggleVideo"
            @toggleAudio="toggleAudio"
            @setFreeze="setFreezeFromHost"
            @undoFreeze="undoFreezeFromHost"
          />
        </footer>
      </div>
    </div>
    <!-- 입찰 모달 modal  -->
    <Teleport to="body">
      <div v-if="openBid && !isFinished" class="bid-modal">
        <div class="row modal-title" style="color: red"><h1>경고</h1></div>
        <div class="row modal-warn" style="color: red">
          <h3>입찰하면 되돌릴 수 없습니다.</h3>
        </div>
        <div>
          <!-- <div class="col"> -->
          <button
            class="btn btn-primary btn-price"
            @click="priceToBid = Number(priceToBid) + 1000 + ''"
          >
            1,000
          </button>
          <!-- </div>
          <div class="col"> -->
          <button
            class="btn btn-warning btn-price"
            @click="priceToBid = Number(priceToBid) + 5000 + ''"
          >
            5,000
          </button>
          <!-- </div>
          <div class="col"> -->
          <button
            class="btn btn-success btn-price"
            @click="priceToBid = Number(priceToBid) + 10000 + ''"
          >
            10,000
          </button>
          <!-- </div> -->
          <!-- <div class="col"> -->
          <button class="btn btn-danger btn-price" @click="resetPriceToBid">
            reset
          </button>
          <!-- </div> -->
          <!-- <div class="col-4"> -->
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="checkbox"
              id="manualInput"
              v-model="isManual"
            />
            <label class="form-check-label" for="manualInput"
              >직접 입력하기</label
            >
          </div>
          <!-- </div> -->
        </div>
        <div class="row">
          <input
            type="text"
            v-model="priceToBid"
            :disabled="!isManual"
            @input="checkMoney"
            maxlength="13"
          />
        </div>
        <div class="row">
          {{ priceToKor }}
        </div>
        <div class="row">
          <div class="col">
            <button
              class="btn btn-primary"
              @click="makeBid"
              :disabled="isFinished || isInvalidPrice"
            >
              입찰
            </button>
          </div>
          <div class="col">
            <button class="btn btn-danger" @click="closeModal">닫기</button>
          </div>
        </div>
      </div>
    </Teleport>
    <Teleport to="body">
      <div v-if="isFinished" class="result-modal" style="text-align: center">
        <div class="row">
          <h1>최종 가격 : {{ formatMoney(this.currentPrice) }}</h1>
        </div>
        <div class="row">
          <div class="result-text">
            <h3 v-if="this.currentBidder">
              {{ this.currentBidder }}에게 최종 낙찰 되었습니다!
            </h3>
            <h3 v-else>유찰되었습니다.</h3>
          </div>
          <div class="bidder-rank">
            <div v-for="(bidder, index) in this.top3List" :key="index">
              <div class="bidder-line">
                <div
                  style="display: inline-block; padding: 0.5em 0 0.5em 0"
                  :class="'bidder-line' + index"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="16"
                    height="16"
                    fill="currentColor"
                    class="bi bi-award-fill"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="m8 0 1.669.864 1.858.282.842 1.68 1.337 1.32L13.4 6l.306 1.854-1.337 1.32-.842 1.68-1.858.282L8 12l-1.669-.864-1.858-.282-.842-1.68-1.337-1.32L2.6 6l-.306-1.854 1.337-1.32.842-1.68L6.331.864 8 0z"
                    />
                    <path
                      d="M4 11.794V16l4-1 4 1v-4.206l-2.018.306L8 13.126 6.018 12.1 4 11.794z"
                    />
                  </svg>
                </div>
                <div style="display: inline-block; padding: 0.5em">
                  {{ index + 1 + "위" }}
                </div>
                <div style="display: inline-block; padding: 0.5em">
                  {{ bidder.sender }}
                </div>
                <div style="display: inline-block; padding: 0.5em">
                  {{ formatMoney(bidder.priceToBid) }}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row m-3">
          <button class="btn btn-warning" @click="leaveSession">
            경매 종료
          </button>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import ParticipantVideo from "@/components/Session/ParticipantVideo.vue";
import ChatMessage from "@/components/Session/ChatMessage.vue";
import InSessionPanel from "@/components/Session/InSessionPanel.vue";
import JoinSession from "@/components/Session/JoinSession.vue";
import SessionTimer from "@/components/Session/SessionTimer.vue";
import MainVideo from "@/components/Session/MainVideo.vue";
import VueCookies from "vue-cookies";
import { mapState } from "vuex";
import { useStore } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "SessionsView",
  components: {
    MainVideo,
    ParticipantVideo,
    ChatMessage,
    InSessionPanel,
    JoinSession,
    SessionTimer,
  },
  mounted() {
    this.store = useStore();
    this.mySessionId = this.store.state.session.sessionId;
    this.sessionName = this.store.state.session.sessionTitle;
    this.isHost = this.store.state.session.isHost == "true" ? true : false;
    this.userNo = this.store.state.session.userNo;
    this.currentPrice = this.store.state.session.startPrice + "";
    console.log(
      `sessionId : ${this.store.state.session.sessionId}, isHost : ${this.store.state.session.isHost}, sessionTitle : ${this.store.state.session.sessionTitle}, startPrice : ${this.store.state.session.startPrice}`
    );
    // 배경색 검정색으로 바꾸기
    document.body.classList.add("dark-mode");
  },

  unmounted() {
    document.body.classList.remove("dark-mode");
  },

  data() {
    return {
      store: null,
      OVCamera: undefined,
      sessionCamera: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      publisherId: undefined,
      publisherMonitor: undefined,
      subscribersCamera: [],

      cameraToken: null,
      mySessionId: null,
      sessionName: null,
      myUserName: null,
      userNo: null,
      mode: "basic",
      message: null,
      messageHistory: [],
      toWhisper: null,
      now: null,
      isHost: false,
      isFreeze: false,

      // 마이크, 카메라 설정
      isVideoOn: true,
      isAudioOn: true,
      openBid: false,
      isManual: false,

      top3List: [],
      currentBidder: null,
      currentPrice: "0",
      priceToBid: "0",
      isFinished: false,
      isStarted: false,
      hostId: null,
      remainingTime: 0,
      startTime: 30,
    };
  },
  computed: {
    ...mapState(["user"]),
    isInvalidPrice() {
      return (
        isNaN(this.priceToBid) ||
        Number(this.priceToBid) <= Number(this.currentPrice)
      );
    },
    priceToKor() {
      let number = this.priceToBid;

      const koreanNumber = [
        "",
        "일",
        "이",
        "삼",
        "사",
        "오",
        "육",
        "칠",
        "팔",
        "구",
      ];
      const tenThousandUnit = ["", "만", "억", "조"];
      const tenUnit = ["", "십", "백", "천"];
      let answer = "";
      let unit = 10000;
      let index = 0;
      let division = Math.pow(unit, index);

      while (Math.floor(number / division) > 0) {
        const mod = Math.floor((number % (division * unit)) / division);
        if (mod) {
          const modToArray = mod.toString().split("");
          const modLength = modToArray.length - 1;
          const toKorean = modToArray.reduce((a, v, i) => {
            if (!koreanNumber[v * 1] == "") {
              a += `${koreanNumber[v * 1]}${tenUnit[modLength - i]}`;
            }
            return a;
          }, "");
          if (toKorean == "") {
            answer = `${toKorean}` + answer;
          } else {
            answer = `${toKorean}${tenThousandUnit[index]}` + answer;
          }
        }
        division = Math.pow(unit, ++index);
      }
      return "금 " + answer + "원";
    },
  },

  methods: {
    setFreezeFromHost(connection) {
      let connections = [];
      if (connection) {
        connections.push(connection);
      }
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: "방장이 방을 얼렸습니다.",
        }),
        to: connections,
        type: this.mySessionId + "/setFreeze",
      });
    },

    undoFreezeFromHost() {
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: "음성 및 채팅 제한이 해제되었습니다.",
        }),
        to: [],
        type: this.mySessionId + "/undoFreeze",
      });
    },

    setFreezeInClient(data) {
      this.isFreeze = true;
      this.messageHistory.push(JSON.parse(data));
      if (this.publisher && !this.isHost) {
        this.publisher.publishAudio(false);
      }
    },

    undoFreezeInClient(data) {
      this.isFreeze = false;
      this.messageHistory.push(JSON.parse(data));
      if (this.publisher && !this.isHost) {
        this.publisher.publishAudio(this.isAudioOn);
      }
    },

    // 호스트 사이드 로직
    startAuctionFromHost() {
      axios
        .post(
          "/sessions/bidstart",
          JSON.stringify({ sessionName: this.mySessionId })
        )
        .then((data) => {
          console.log(data);
        });

      // 다른 클라이언트들에게 startAuction 메시지를 보내 isFinished를 false로 만든다.
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: "system :",
            message: "경매가 시작되었습니다.",
          }),
          to: [],
          type: this.mySessionId + "/startAuction",
        })
        .then(() => {
          console.log();
        });
      this.$refs.timer.timerReset();
      this.$refs.timer.timerStart();
    },

    finishAuctionFromHost() {
      // 다른 클라이언트들에게 endAuction 메시지를 보내 isFinished를 true로 만든다.
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: "경매가 종료되었습니다.",
        }),
        to: [],
        type: this.mySessionId + "/endAuction",
      });

      // 서버에 경매 종료 신호를 보낸다.
      axios
        .post(
          "/sessions/bid",
          JSON.stringify({ sessionName: this.mySessionId })
        )
        .then((data) => {
          console.log(data);
        });
    },

    // 경매 시작
    // 클라이언트 사이드 로직
    startAuctionInClient(data) {
      this.isStarted = true;
      this.isFinished = false;
      this.messageHistory.push(JSON.parse(data));
    },
    // 경매 종료
    // 클라이언트 사이드 로직
    finishAuctionInClient(data) {
      this.isFinished = true;
      this.messageHistory.push(JSON.parse(data));
    },

    // 호스트 로직
    // 다른 클라이언트에 현재 기준 시간(호스트 시간)을 전송한다.
    tickTimer(currentTime) {
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: this.myUserName,
            currentTime: currentTime,
          }),
          to: [],
          type: this.mySessionId + "/tick",
        })
        .then(() => {
          console.log();
        });
    },

    // css 작업용
    // joinSession() {
    //   this.sessionCamera = true;
    // },

    // 세션 연결
    joinSession(userName) {
      this.myUserName = userName;
      // --- Get an OpenVidu object ---
      this.OVCamera = new OpenVidu();
      // this.OVScreen = new OpenVidu();

      // 음성 인식 관련 설정
      this.OVCamera.setAdvancedConfiguration({
        publisherSpeakingEventsOptions: {
          interval: 100, // Frequency of the polling of audio streams in ms (default 100)
          threshold: -50, // Threshold volume in dB (default -50)
        },
      });

      // --- Init a session ---
      this.sessionCamera = this.OVCamera.initSession();
      // this.sessionScreen = this.OVScreen.initSession();

      // --- Specify the actions when events take place in the session ---
      // 세션과 연결이 되면 아래의 코드가 실행된다.
      this.sessionCamera.on("connectionCreated", (event) => {
        console.log("connection created");
        console.log(event.connection.data);
        let data = JSON.parse(event.connection.data);
        if (data.isHost) {
          this.hostId = event.connection.connectionId;
        }
      });

      // On every new Stream received...
      this.sessionCamera.on("streamCreated", ({ stream }) => {
        if (this.isHost && this.isFreeze) {
          this.setFreezeFromHost(stream.connection);
        }
        if (
          this.publisher &&
          stream.creationTime > this.publisher.stream.creationTime
        ) {
          this.messageHistory.push({
            sender: "system :",
            message:
              JSON.parse(stream.connection.data).clientData +
              "이(가) 입장했습니다.",
          });
        }

        const subscriber = this.sessionCamera.subscribe(stream);
        console.log("Camera Stream created");
        console.log(subscriber);
        this.subscribersCamera.push(subscriber); // 해당 스트림을 subscribe하고 video player를 삽입한다.
      });

      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        if (stream.typeOfVideo != "CAMERA") return;
        this.messageHistory.push({
          sender: "system :",
          message:
            JSON.parse(stream.connection.data).clientData +
            "이(가) 퇴장했습니다.",
        });
        const index = this.subscribersCamera.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribersCamera.splice(index, 1);
        }
      });

      this.sessionCamera.on("publisherStartSpeaking", (event) => {
        console.log(
          "User " + event.connection.connectionId + " start speaking"
        );
        this.sendHighlight(event.connection.connectionId);
      });

      this.sessionCamera.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
        this.sendUndoHighlight(event.connection.connectionId);
      });

      // 말하고 있는 사람 하이라이트 표시 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/highlight`, (event) => {
        console.log(event.data); // Message
        this.highlightSpeaker(event.data);
      });

      this.sessionCamera.on(
        `signal:${this.mySessionId}/undohighlight`,
        (event) => {
          console.log(event.data); // Message
          this.undoHighlightSpeaker(event.data);
        }
      );

      this.sessionCamera.on(
        `signal:${this.mySessionId}/setstarttime`,
        (event) => {
          console.log(event.data); // Message
          // if (!isHost) {
          //   this.setStartTime(event.data.message);
          // }
        }
      );

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 채팅 메시지 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/message`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.appendMessage(event);
      });

      // 방 얼리기
      this.sessionCamera.on(`signal:${this.mySessionId}/setFreeze`, (event) => {
        this.setFreezeInClient(event.data);
      });

      // 방 얼리기 해제
      this.sessionCamera.on(
        `signal:${this.mySessionId}/undoFreeze`,
        (event) => {
          this.undoFreezeInClient(event.data);
        }
      );

      // 경매 시작 신호 수신
      this.sessionCamera.on(
        `signal:${this.mySessionId}/startAuction`,
        (event) => {
          this.startAuctionInClient(event.data);
        }
      );
      // 현재 호스트 시간 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/tick`, (event) => {
        console.log(`currentTime: ${event.data}`);
        this.remainingTime = JSON.parse(event.data).currentTime;
      });
      // 입찰 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/bid`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.updateBid(event);
      });

      // 경매 종료 신호 수신
      this.sessionCamera.on(
        `signal:${this.mySessionId}/endAuction`,
        (event) => {
          console.log(event.data); // Message
          console.log(event.from); // Connection object of the sender
          console.log(event.type); // The type of message ("my-chat")
          this.finishAuctionInClient(event.data);
        }
      );

      // 경매 결과 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/result`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.showResult(event);
      });

      this.sessionCamera.on(`signal:${this.mySessionId}/kickout`, () => {
        this.kickoutInClient();
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken().then((token) => {
        this.cameraToken = token;
        console.log(`received token for Camera: ${token}`);
        this.sessionCamera
          .connect(token)
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: this.isAudioOn, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: this.isVideoOn, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;
            // --- Publish your stream ---
            this.sessionCamera.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    // 백엔드를 통해 토큰 받기
    getToken() {
      return new Promise((resolve, reject) => {
        axios
          .post(
            "/sessions/getToken",
            JSON.stringify({
              sessionName: this.mySessionId,
              loggedUser: this.myUserName,
              userNo: this.userNo,
              isHost: this.isHost,
            })
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    // 지금 말하고 있는 사람 주위에 테두리를 만든다.
    highlightSpeaker(data) {
      console.log(this.$refs);
      const speakerId = JSON.parse(data).connectionId;
      if (speakerId == this.publisher.stream.connection.connectionId) {
        this.$refs.publisher.$el.style.border = "2px solid white";
      }

      // $refs.subscribersCamera 배열을 선형순회하는 건 오래 걸리므로
      // 인덱스를 통해 접근하는 방법을 생각해봤지만,
      // v-for에 refs를 적용해 만들어진 배열이 원본 배열의 순서를 보장하지 않으므로
      // 일단 포기..
      if (
        this.$refs.subscribersCamera &&
        this.$refs.subscribersCamera.length > 0
      ) {
        for (let sub of this.$refs.subscribersCamera) {
          console.log("connectionId");
          if (speakerId == sub.streamManager.stream.connection.connectionId) {
            sub.$el.style.border = "2px solid white";
          }
        }
      }
    },

    // 말을 하다가 마친 사람 주위에 있던 테두리를 없앤다.
    undoHighlightSpeaker(data) {
      const speakerId = JSON.parse(data).connectionId;
      if (speakerId == this.publisher.stream.connection.connectionId) {
        this.$refs.publisher.$el.style.border = "";
      }

      if (
        this.$refs.subscribersCamera &&
        this.$refs.subscribersCamera.length > 0
      ) {
        for (let sub of this.$refs.subscribersCamera) {
          console.log("connectionId");
          if (speakerId == sub.streamManager.stream.connection.connectionId) {
            sub.$el.style.border = "";
          }
        }
      }
    },

    sendHighlight(connectionId) {
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            connectionId: connectionId,
          }),
          to: [],
          type: this.mySessionId + "/highlight",
        })
        .then(() => {
          console.log();
        });
    },

    sendUndoHighlight(connectionId) {
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            connectionId: connectionId,
          }),
          to: [],
          type: this.mySessionId + "/undohighlight",
        })
        .then(() => {
          console.log();
        });
    },
    // css 수정용
    // submitMessage() {
    //   this.appendMessage({
    //     sender: this.myUserName + "이(가) 모두에게",
    //     message: this.message,
    //   });
    // },

    // 채팅 보내기
    submitMessage() {
      if (!this.isHost && this.isFreeze) {
        alert(
          "호스트가 방을 얼렸습니다. 호스트가 해제하기 전까지 메시지를 보낼 수 없습니다."
        );
        return;
      }
      let toArray = [];
      let sender = this.myUserName;
      if (this.toWhisper) {
        toArray.push(this.toWhisper);
        toArray.push(this.publisher.stream.connection);
        sender +=
          "이(가) " + JSON.parse(this.toWhisper.data).clientData + "에게";
      } else {
        sender += "이(가) 모두에게";
      }
      sender += " : ";

      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: sender,
            message: this.message,
          }),
          to: toArray,
          type: this.mySessionId + "/message",
        })
        .then(() => {
          console.log();
        });
      this.message = null;
    },

    // css용
    // appendMessage(message) {
    //   this.messageHistory.push(message);
    // },

    // 채팅 받기
    appendMessage(event) {
      const message = JSON.parse(event.data);
      this.messageHistory.push(message);
    },

    // 입찰하기
    makeBid() {
      // 동일인 입찰 검증
      if (
        this.top3List.length != 0 &&
        this.top3List[0].sender == this.myUserName
      ) {
        alert("현재 최고가로 입찰하셨습니다.");
        return;
      }

      // 최고가 입찰 검증
      if (
        this.top3List.length != 0 &&
        Number(this.top3List[0].priceToBid) >= Number(this.priceToBid)
      ) {
        alert("현재 최고가보다 높은 값을 입력해야 합니다.");
        return;
      }

      axios
        .put(
          "/sessions/bid",
          JSON.stringify({
            bidder: this.myUserName,
            userNo: VueCookies.get("login.userNo"),
            priceToBid: this.priceToBid,
            sessionName: this.mySessionId,
          })
        )
        .then(() => {
          this.sessionCamera
            .signal({
              data: JSON.stringify({
                sender: this.myUserName,
                priceToBid: this.priceToBid,
              }),
              to: [],
              type: this.mySessionId + "/bid",
            })
            .then((data) => {
              console.log("after bid");
              console.log(data);
            });
          this.message = null;
          this.openBid = false;
        })
        .catch(() => {
          console.log("입찰에 실패했습니다.");
        });
    },

    // 타이머를 설정 시간으로 초기화하고 금액을 전달받은 금액으로 업데이트한다.
    // 만약 전달받은 금액이 이전 금액보다 작거나 같으면 업데이트하지 않는다.
    updateBid(event) {
      if (this.isHost) {
        this.$refs.timer.timerReset();
        this.$refs.timer.timerStart();
      }
      const data = JSON.parse(event.data);
      this.currentPrice = data.priceToBid;
      this.priceToBid = data.priceToBid;
      this.currentBidder = data.sender;
      this.top3List.push(data);
      this.top3List.sort((a, b) => {
        return Number(b.priceToBid) - Number(a.priceToBid);
      });
      if (this.top3List.length > 3) {
        this.top3List.splice(3, 1);
      }
    },

    // 영상 on/off
    toggleVideo() {
      this.isVideoOn = !this.isVideoOn;
      if (this.publisher) {
        this.publisher.publishVideo(this.isVideoOn);
      }
    },

    // 마이크 on/off
    toggleAudio() {
      if (!this.isHost && this.isFreeze) {
        alert("방장이 방을 얼렸습니다. 음성이 제한됩니다.");
        return;
      }
      this.isAudioOn = !this.isAudioOn;
      if (this.publisher) {
        this.publisher.publishAudio(this.isAudioOn);
      }
    },

    // },

    // 입찰 모달 닫기
    closeModal() {
      this.openBid = false;
      this.resetPriceToBid();
    },

    // 입찰 모달에 입력했던 입찰할 금액 초기화
    resetPriceToBid() {
      this.top3List.length == 0
        ? (this.priceToBid = "0")
        : (this.priceToBid = this.top3List[0].priceToBid);
    },

    // 입찰 모달 on/off
    switchModal() {
      this.openBid = !this.openBid;
    },

    // css 작업용
    // leaveSession(reason) {
    //   console.log(reason);
    //   this.sessionCamera = false;
    // },

    leaveSession(reason) {
      this.closeModal();
      console.log("leaveSession");
      console.log(this.cameraToken);
      let data = {
        sessionName: this.mySessionId,
        cameraToken: this.cameraToken,
        // screenToken: this.screenToken,
        userNo: this.userNo,
      };
      if (reason == "kick-out") {
        data.reason = reason;
        alert("퇴장당했습니다.");
      } else {
        data.reason = "normal";
      }
      this.$refs.timer.timerStop();

      if (this.isHost) {
        this.sessionCamera
          .signal({
            data: JSON.stringify({
              sender: "system : ",
              message: "방장이 방을 나갔습니다. 경매가 중단됩니다.",
            }),
            to: [],
            type: this.mySessionId + "/message",
          })
          .then(() => {
            console.log();
          });
        this.message = null;
        this.finishAuctionFromHost();
      }

      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();

      axios
        .post("sessions/removeUser", JSON.stringify(data))
        .then(() => {
          this.clearData();
        })
        .catch((e) => {
          console.log("에러 발생");
          console.log(e);
        });
      window.removeEventListener("beforeunload", this.leaveSession);
      this.$store.dispatch("session/setIsInSession", false);
      this.$router.push({
        name: "Home",
      });
    },

    clearData() {
      this.OVCamera = undefined;
      // this.OVScreen = undefined;
      this.sessionCamera = undefined;
      // this.sessionScreen = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.publisherId = undefined;
      // this.publisherMonitor = undefined;
      this.subscribersCamera = [];
      // this.subscribersScreen = [];

      this.cameraToken = null;
      // this.screenToken = null;
      this.mySessionId = null;
      this.myUserName = null;
      this.userNo = null;
      this.message = null;
      this.messageHistory = [];
      this.toWhisper = null;
      this.now = null;
      this.isHost = false;
      this.isFreeze = false;

      this.openBid = false;
      this.isManual = false;

      this.top3List = [];
      this.currentBidder = null;
      this.currentPrice = "0";
      this.priceToBid = "0";
      this.isFinished = false;
      this.hostId = null;
      this.remainingTime = 0;
    },
    updateMainVideoStreamManager(stream) {
      // if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    setWhisper(event) {
      console.log("setWhisper");
      console.log(event);
    },

    getClientId(sub) {
      return JSON.parse(sub.stream.connection.data).clientData;
    },

    kickoutFromHost(connection) {
      const kickoutList = [];
      kickoutList.push(connection);
      console.log("kickout");
      if (this.isHost) {
        // 서버에 블랙리스트 등록 먼저 해야 함
        this.sessionCamera.signal({
          data: JSON.stringify({
            sender: "system :",
            message: "강제 퇴장.",
          }),
          to: kickoutList,
          type: this.mySessionId + "/kickout",
        });
      }
    },

    kickoutInClient() {
      this.leaveSession("kick-out");
    },
    formatMoney(money) {
      let str = "";
      for (let i = 0; i < money.length; i++) {
        if (i != 0 && i % 3 == 0) str += ",";
        str += money.charAt(money.length - (i + 1));
      }
      return str.split("").reverse().join("") + "원";
    },
    checkMoney() {
      if (this.priceToBid.length > 13) {
        return this.priceToBid.slice(0, 13);
      }
    },
    setStartTime(startTime) {
      console.log("setStartTime");
      this.startTime = startTime;
    },
    handleChange(value) {
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: value,
        }),
        to: [],
        type: this.mySessionId + "/setstarttime",
      });
    },
    // resetToWhisper() {
    //   this.toWhisper = null;
    //   console.log(this.toWhisper);
    // },
  },
};
</script>
<style scoped>
#buttonLeaveSession {
  margin-top: 20px;
}
#session-video-container {
  overflow-x: auto;
  white-space: nowrap;
  height: auto;
  text-align: left;
}
#chat-history {
  height: 80%;
  background-color: white;
  overflow-y: auto;
}
.chat-message {
  color: black;
}

.participant-name {
  color: black;
}

.bid-modal {
  position: fixed;
  z-index: 999;
  top: 20%;
  left: 50%;
  margin-left: -150px;
  background-color: white;
  width: auto;
}
.result-modal {
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  width: auto;
}
.form-check {
  margin-top: 0.5rem;
  margin-right: 1.5rem;
  font-size: 1.3rem;
  width: auto;
}
.form-check-input {
  margin-top: 0;
  margin-right: 0.3rem;
  height: 1.8rem;
}

#session-container {
  background-color: black;
  color: white;
}

#session-chat-panel {
  height: 50%;
}

#session-chat-history {
  height: 80%;
}

#chat-control-panel {
  height: 8%;
}
#chat-input {
  height: 12%;
}

footer {
  bottom: 0;
  padding: 1em;
}
textarea {
  resize: none;
}
.row {
  --bs-gutter-x: 0rem;
  --bs-gutter-y: 0;
  display: flex;
  flex-wrap: wrap;
  margin-top: calc(var(--bs-gutter-y) * -1);
  margin-right: calc(var(--bs-gutter-x) * -0.5);
  margin-left: calc(var(--bs-gutter-x) * -0.5);
}

el-tabs {
  --el-bg-color-overlay: black;
  --el-fill-color-light: black;
}

.result-modal {
  color: black;
  padding: 2em;
}
.bid-modal {
  color: black;
  text-align: center;
  padding: 2em;
}

.btn-price {
  margin: 1%;
}

.bidder-line0 {
  color: gold;
}

.bidder-line1 {
  color: silver;
}

.bidder-line2 {
  color: brown;
}
</style>
