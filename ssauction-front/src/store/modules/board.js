const boardStore = {
  namespaced: true,
  state: {
    boardType: 0,
    boardContent: "",
  },
  getters: {
    boardType(state) {
      return state.boardType;
    },
  },
  mutations: {
    SET_BOARD_TYPE: (state, boardType) => {
      state.boardType = boardType;
    },
  },
  actions: {
    setBoardType({ commit }, boardType) {
      commit("SET_BOARD_TYPE", boardType);
    },
  },
};
export default boardStore;
