import { getNotice } from "@/api/notice.js";

const noticeStore = {
  namespaced: true,
  state: {
    notices: [],
  },
  getters: {},
  mutations: {
    SET_NOTICES: (state, notices) => {
      state.notices = notices;
    },
  },
  actions: {
    getnoticeList({ commit }) {
      getNotice(
        ({ data }) => {
          commit("SET_NOTICES", data.notices);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default noticeStore;
