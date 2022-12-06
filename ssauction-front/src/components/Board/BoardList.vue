<template>
  <!-- 게시판 조회 화면 -->
  <div class="table" style="border-color: rgb(94, 94, 94)">
    <div class="register">
      <div>
        <select v-model="state.searchType">
          <option disabled selected value="">선택</option>
          <option value="title">제목</option>
          <option value="userNickname">작성자</option></select
        >&nbsp; &nbsp;
        <input
          type="text"
          name="search"
          id="search"
          v-model="state.search"
        />&nbsp; &nbsp;
        <button class="btn-search" @click="getArticles(0)">검색</button>&nbsp;
        &nbsp;
        <button class="btn-search" @click="mvBoardRegister">글쓰기</button>
      </div>
    </div>
    <br />
    <table
      class="
        boardtable
        table-hover table-sm
        align-center
        caption-top
        table-responsive
      "
      style="
        margin-left: auto;
        margin-right: auto;
        bordercolor: rgb(94, 94, 94);
      "
    >
      <colgroup style="align: center">
        <col style="width: 10%" />
        <col style="width: 50%" />
        <col style="width: 20%" />
        <col style="width: 20%" />
      </colgroup>
      <thead>
        <tr>
          <th>글번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
        </tr>
      </thead>
      <tbody style="cursor: pointer">
        <board-list-item
          v-for="(article, index) in state.articles"
          :key="index"
          v-bind="article"
        ></board-list-item>
      </tbody>
    </table>
    <br />

    <!-- 페이지네이션 / 10개당 1페이지로 구성 -->
    <div class="btnGroup">
      <button class="btn-arrow" @click="getArticles(state.startPage - 10)">
        〈〈
      </button>
      <span v-for="(p, index) in state.pages" :key="index">
        <button style="border: 0px" @click="getArticles(p - 1)">
          {{ p }}
        </button>
      </span>
      <button class="btn-arrow" @click="getArticles(state.totalPages + 10)">
        〉〉
      </button>
    </div>
  </div>
</template>

<script>
import BoardListItem from "@/components/Board/item/BoardListItem";
import axios from "@/utils/axios";
import { useRouter } from "vue-router";
import { reactive } from "vue";
// npm install universal-cookie

import { useStore } from "vuex";

export default {
  name: "BoardList",
  components: {
    BoardListItem,
  },
  setup() {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      startPage: Number,
      endPage: Number,
      articles: [],
      currentPage: Number,
      totalPages: Number,
      numberOfElements: Number,
      pages: [],
      first: Boolean,
      last: Boolean,
      searchType: "",
      search: "",
      isUser: Boolean,
    });

    const getArticles = (p) => {
      getPage(p, 10, state.searchType, state.search);
    };

    const getPage = (p, s, searchT, search) => {
      if (p < 0) {
        alert("첫 페이지 리스트 입니다");
        return;
      }
      if (p >= state.totalPages) {
        alert("마지막 페이지 리스트 입니다");
        return;
      }
      console.log(store.state.board.boardType);
      axios
        .get("board", {
          params: {
            page: p,
            size: s,
            boardType: store.state.board.boardType,
            searchType: searchT,
            search: search,
          },
        })
        .then(({ data }) => {
          // console.log(data);
          state.currentPage = data.list.number;
          state.totalPages = data.list.totalPages;
          state.numberOfElements = data.list.numberOfElements;
          state.startPage = Math.floor((1 + state.currentPage) / 10) * 10 + 1;
          state.endPage =
            Math.floor((1 + state.currentPage) / 10) * 10 + 10 >
            state.totalPages
              ? state.totalPages
              : Math.floor((1 + state.currentPage) / 10) * 10 + 10;

          state.pages.length = 0;
          for (let i = state.startPage; i <= state.endPage; i++) {
            state.pages.push(i);
          }
          state.articles = data.list.content;
        });
    };

    const mvBoardRegister = () => {
      router.push({ path: "/board/regist" });
    };

    getPage(0, 10);
    state.searchType = "";

    if (state.userNo != null) state.isUser = true;
    else state.isUser = false;

    return {
      state,
      getArticles,
      mvBoardRegister,
    };
  },
};
</script>


<style scoped>
.btn-arrow {
  background-color: #f8e6e0;
  color: #6e6e6e;
  position: relative;
  border: none;
  display: inline-block;
  padding: 10px 15px;
  border-radius: 15px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin-top: 10px;
}

.btn-search {
  background-color: rgb(255, 211, 182);
  color: #6e6e6e;
  position: relative;
  border: none;
  display: inline-block;
  padding: 10px 20px;
  border-radius: 15px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 600;
  transition: 0.25s;
  margin-top: 10px;
}

input {
  width: 400px;
  height: 40px;
  border: 0;
  background-color: rgb(230, 230, 230);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left: 30px;
}

input:focus {
  outline: 3px rgb(94, 94, 94);
}

select {
  -o-appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: 100px;
  height: 35px;
  background: rgb(255, 211, 182);
  background-size: 20px;
  padding: 5px 30px 5px 10px;
  border-radius: 4px;
  outline: 0 none;
  border: 0;
  color: rgb(94, 94, 94);
}
.select option {
  background: rgb(255, 211, 182);
  color: rgb(255, 211, 182);
  padding: 3px 0;
  border: 0px;
}
</style>