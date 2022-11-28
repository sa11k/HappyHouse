import { apiInstance } from "@/api/index.js";

const api = apiInstance();

const houseStore = {
  namespaced: true,

  state: {
    sidos: [{ value: null, text: "시/도" }],
    guguns: [{ value: null, text: "구/군" }],
    dongs: [{ value: null, text: "동" }],
    sido: null,
    gugun: null,
    dong: null,
    address: null,
    houses: [],
    house: null,
    detail: {},
  },
  mutations: {
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dong });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시/도" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구/군" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_SI_GUN_DONG(state) {
      state.sido = null;
      state.gugun = null;
      state.dong = null;
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },
    SET_SIDO_CODE(state, sidoCode) {
      state.sido = sidoCode;
    },
    SET_GUGUN_CODE(state, gugunCode) {
      state.gugun = gugunCode;
    },
    SET_DONG_CODE(state, dongCode) {
      state.dong = dongCode;
    },
    SET_ADDRESS(state, address) {
      state.address = address;
    },
    SET_DETAIL(state, detail) {
      state.detail = detail;
    },
  },
  actions: {
    getSido({ commit }) {
      api
        .get(`/map/sido`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      const params = { sido: sidoCode };
      api
        .get(`/map/gugun`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDong({ commit }, gugunCode) {
      const params = { gugun: gugunCode };
      api
        .get(`/map/dong`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAddress({ commit }, dongCode) {
      const params = { dong: dongCode };
      api
        .get(`/map/address`, { params })
        .then((data) => {
          commit("SET_ADDRESS", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDetail({ commit }, aptCode) {
      const params = { aptCode: aptCode };
      api
        .get("/map/detail", { params })
        .then(({ data }) => {
          commit("SET_DETAIL", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getHouseList({ commit }, dongCode) {
      // 우리 DB 사용
      const params = { dong: dongCode };
      api
        .get(`/map/apt`, { params })
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_HOUSE_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });

      //공공데이터 포털 사용
      // vue cli enviroment variables 검색
      //.env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      // const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      // const SERVICE_KEY =
      //   "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
      // const SERVICE_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
      // const params = {
      //   LAWD_CD: dongCode,
      //   DEAL_YMD: "202203",
      //   serviceKey: decodeURIComponent(SERVICE_KEY),
      // };
      // http
      //   .get(SERVICE_URL, { params })
      //   .then(({ data }) => {
      //     // console.log(commit, data);
      //     commit("SET_HOUSE_LIST", data.response.body.items.item);
      //   })
      //   .catch((error) => {
      //     console.log(error);
      //   });
    },
    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      // console.log(commit, house);
      commit("SET_DETAIL_HOUSE", house);
    },
    /////////////////////////////// House end /////////////////////////////////////
  },
};

export default houseStore;
