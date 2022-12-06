const sessionStore = {
  namespaced: true,
  state: {
    isHost: false,
    sessionId: null,
    sessionTitle: null,
    startPrice: 0,
    isInSession: false,
  },
  getters: {
    isHost(state) {
      return state.isHost;
    },
    sessionId(state) {
      return state.sessionId;
    },
    sessionTitle(state) {
      return state.sessionTitle;
    },
    startPrice(state) {
      return state.startPrice;
    },
    isInSession(state) {
      return state.isInSession;
    },
  },
  mutations: {
    SET_IS_HOST: (state, isHost) => {
      state.isHost = isHost;
    },
    SET_SESSION_ID: (state, sessionId) => {
      state.sessionId = sessionId;
    },
    SET_SESSION_TITLE: (state, sessionTitle) => {
      state.sessionTitle = sessionTitle;
    },
    SET_START_PRICE: (state, startPrice) => {
      state.startPrice = startPrice;
    },
    SET_IS_IN_SESSION: (state, isInSession) => {
      state.isInSession = isInSession;
    },
  },
  actions: {
    setIsHost({ commit }, isHost) {
      commit("SET_IS_HOST", isHost);
    },
    setSessionId({ commit }, sessionId) {
      commit("SET_SESSION_ID", sessionId);
    },
    setSessionTitle({ commit }, sessionTitle) {
      commit("SET_SESSION_TITLE", sessionTitle);
    },
    setStartPrice({ commit }, startPrice) {
      commit("SET_START_PRICE", startPrice);
    },
    setIsInSession({ commit }, isInSession) {
      commit("SET_IS_IN_SESSION", isInSession);
    },
  },
};
export default sessionStore;
