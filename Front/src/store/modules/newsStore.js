import { getNews } from "@/api/news.js";

const newsStore = {
  namespaced: true,
  state: {
    newses: [],
  },
  getters: {},
  mutations: {
    SET_NEWSES: (state, newses) => {
      state.newses = newses;
    },
  },
  actions: {
    getNews({ commit }) {
      getNews(
        ({ data }) => {
          commit("SET_NEWSES", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default newsStore;
