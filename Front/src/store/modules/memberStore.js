import jwt_decode from "jwt-decode";
import { login, idCheck, findById } from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    showModal: false,
    showModalSignup: false,
    showModalUserInfo: false,
    isCheckId: false,
    isAgent: "",
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_SHOW_MODAL_LOGIN: (state) => {
      state.showModal = !state.showModal;
    },
    SET_SHOW_MODAL_SIGNUP: (state) => {
      state.showModalSignup = !state.showModalSignup;
    },
    SET_SHOW_MODAL_USERINFO: (state) => {
      state.showModalUserInfo = !state.showModalUserInfo;
    },
    SET_IS_CHECK_ID: (state, isCheckId) => {
      state.isCheckId = isCheckId;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
            console.log(token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async idCheck({ commit }, id) {
      await idCheck(
        id,
        ({ data }) => {
          if (data === "success") {
            commit("SET_IS_CHECK_ID", true);
          } else {
            commit("SET_IS_CHECK_ID", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default memberStore;
