import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import memberStore from "@/store/modules/memberStore.js";
import houseStore from "@/store/modules/houseStore.js";
import storeStore from "@/store/modules/storeStore.js";
import newsStore from "@/store/modules/newsStore.js";
import noticeStore from "@/store/modules/noticeStore.js";
import houseInterestStore from "@/store/modules/houseInterestStore.js";
import safetyStore from "@/store/modules/safetyStore";

const store = new Vuex.Store({
  modules: {
    memberStore,
    houseStore,
    storeStore,
    newsStore,
    noticeStore,
    houseInterestStore,
    safetyStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

export default store;
