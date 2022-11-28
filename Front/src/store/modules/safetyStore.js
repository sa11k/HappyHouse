import { apiInstance } from "@/api/index.js";
const api = apiInstance();
// import axios from "axios";

const safetyStore = {
  namespaced: true,

  state: {
    safetys: [],
  },
  mutations: {
    SET_SAFETY_LIST(state, safetys) {
      state.safetys = safetys;
    },
  },
  actions: {
    getSafetyList({ commit }) {
      // 우리 DB로 변경
      api
        .get(`/safety/all`)
        .then(({ data }) => {
          // console.log(commit, response);
          console.log(data);
          commit("SET_SAFETY_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });

      // 공공데이터 포털 사용
      // vue cli enviroment variables 검색
      // .env.local file 생성.
      // 반드시 VUE_APP으로 시작해야 한다.
      // const SERVICE_KEY = process.env.VUE_APP_SAFETY_API_KEY;
      // const SERVICE_URL = "http://apis.data.go.kr/1741000/RegionalSafetyGrade/getRegionalSafetyGradeList";
      // axios
      //   .get(SERVICE_URL, {
      //     params: {
      //       ServiceKey: decodeURIComponent("ERMzmnp35tT02hXTuzDWyQF7PgjRNrJ%2B0eWTQcsPKC90MH4WHOJrqbzrVJ6RGNXMTiI6yMwmvRblzS%2BMKJ%2Fwzw%3D%3D"),
      //       type: "xml",
      //       pageNo: "1",
      //       numOfRows: "17",
      //       base_yy: "2020",
      //     },
      //   })
      //   .then(({ data }) => {
      //     // console.log(commit, data);
      //     console.log("data : ");
      //     console.log(commit, data);
      //     // console.commit(data.RegionalSafetyGrade.rows.row);
      //     // commit("SET_HOUSE_LIST", data.response.body.items.item);
      //   })
      //   .catch((error) => {
      //     console.log(error.response.data);
      //   });
    },
  },
};

export default safetyStore;
