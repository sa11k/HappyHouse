import { apiInstance } from "@/api/index.js";

const api = apiInstance();

const storeStore = {
  namespaced: true,

  state: {
    cafes: [],
    restaurant: [],
  },
  mutations: {
    SET_CAFE_LIST(state, cafes) {
      state.cafes = cafes;
    },
    SET_RESTAURANT_LIST(state, restaurants) {
      state.restaurants = restaurants;
    },
  },
  actions: {
    getCafe({ commit }, dongCode) {
      const params = { dong: dongCode };
      api
        .get("/store/cafe", { params })
        .then(({ data }) => {
          commit("SET_CAFE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getRestaurant({ commit }, dongCode) {
      const params = { dong: dongCode };
      api
        .get("/store/restaurant", { params })
        .then(({ data }) => {
          commit("SET_RESTAURANT_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default storeStore;
