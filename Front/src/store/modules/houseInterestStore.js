import { listinteresthouse } from "@/api/interesthouse.js";

const houseInterestStore = {
  namespaced: true,
  state: {
    houses: [],
  },
  getters: {
    houseCnt: function (state) {
      return state.houses.length;
    },
  },
  mutations: {
    SET_HOUSES(state, houses) {
      state.houses = houses;
    },
  },
  actions: {
    async sethouselist({ commit }, user_id) {
      await listinteresthouse(
        user_id,
        (response) => {
          if (response.data.interesthouselist.length >= 1) {
            commit("SET_HOUSES", response.data.interesthouselist);
          } else {
            commit("SET_HOUSES", "");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseInterestStore;
