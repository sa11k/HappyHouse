<template>
  <div class="header">
    <div class="login">
      <div class="header_login_confirm_on" v-if="userInfo">
        <div class="header_login_menuitem">
          <img src="../../assets/img/people.png" alt="" />
          <p style="margin-left: 5px">{{ userInfo.name }}({{ userInfo.id }})님 환영합니다!</p>
        </div>
        <div class="header_login_menuitem">
          <img src="../../assets/img/people.png" alt="" />
          <a class="login_link" style="margin-left: 5px" @click="userinfo">회원정보</a>
        </div>
        <div class="header_login_menuitem" style="margin-left: 20px; font-weight: bolder">
          <a class="login_link" @click.prevent="onClickLogout">Logout</a>
        </div>
      </div>
      <div class="header_login_confirm_off" v-else>
        <img src="@/assets/img/people.png" alt="" />
        <a class="login_link" @click="login">Login</a>
        <div class="header_login_menuitem">
          <img src="../../assets/img/people.png" alt="" />
          <a class="login_link" @click="singup">회원가입</a>
        </div>
      </div>
    </div>
    <div class="nav">
      <img src="../../assets/img/logo.png" alt="홈으로가기" class="logo" usemap="#logo" />
      <map name="logo">
        <area shape="default" title="홈으로" href="/" />
      </map>
      <div class="header_nav">
        <div class="header_nav_menuitem" v-if="userInfo && userInfo.id == 'admin'">
          <a class="nav_link" href="/admin/userlist">사용자 관리</a>
        </div>
        <div class="header_nav_menuitem">
          <a class="nav_link" href="/notice">공지사항</a>
        </div>
        <div class="header_nav_menuitem">
          <a class="nav_link" href="/board">자유게시판</a>
        </div>
        <div class="header_nav_menuitem">
          <a class="nav_link" href="/interest/interestList">관심 지역</a>
        </div>
        <div class="header_nav_menuitem">
          <a class="nav_link" href="/interesthouse/interesthouseList">찜한 매물</a>
        </div>
        <div class="header_nav_menuitem" v-if="userInfo && userInfo.isAgent == 'Y'">
          <a class="nav_link" href="/agent/regist">중개사 매물 등록</a>
        </div>
        <login-view></login-view>
        <signup-view></signup-view>
        <userinfo-view></userinfo-view>
      </div>
    </div>
  </div>
</template>

<script>
import LoginView from "@/components/user/LoginModal.vue";
import SignupView from "@/components/user/SignUpModal.vue";
import UserinfoView from "@/components/user/UserInfoModal.vue";
import { mapState, mapMutations } from "vuex";
import store from "@/store/index.js";
import swal from "sweetalert";

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    swal("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

export default {
  name: "HeaderNav",
  components: {
    LoginView,
    SignupView,
    UserinfoView,
  },
  computed: {
    ...mapState("memberStore", ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations("memberStore", ["SET_IS_LOGIN", "SET_USER_INFO", "SET_SHOW_MODAL_LOGIN", "SET_SHOW_MODAL_SIGNUP", "SET_SHOW_MODAL_USERINFO"]),
    login() {
      this.SET_SHOW_MODAL_LOGIN();
    },
    singup() {
      this.SET_SHOW_MODAL_SIGNUP();
    },
    userinfo() {
      onlyAuthUser;
      this.SET_SHOW_MODAL_USERINFO();
    },
    clearInput() {
      this.user = "";
    },
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      swal("로그아웃 성공!", "", "success");
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
  },
};
</script>
<style>
body {
  margin: 0;
}

.header {
  height: 100px;
  position: relative;
  width: 100%;
  /* z-index: 1; */
  background: #ffffff;
  box-shadow: 0 5px 18px -7px rgba(0, 0, 0, 1);
}

/* 로그인 */
.header > .login {
  width: 100%;
  max-width: 100%;
  height: 30px;
  background-color: #000000;
  float: right;
}

.login > a {
  margin-left: 30px;
  line-height: 30px;
  height: 30px;
}

.login_link {
  font-size: 13px;
  text-decoration: none;
  color: white;
}

.header_login_menuitem {
  display: inline-block;
  height: 30px;
  line-height: 30px;
}

.header_login_menuitem p {
  display: inline;
  font-size: 13px;
  color: white;
}

.header_login_confirm_off,
.header_login_confirm_on {
  position: relative;
  max-width: 100%;
  height: 30px;
  float: right;
  right: 20px;
}

.login img {
  width: 10px;
  height: 10px;
  margin-left: 15px;
}

/* 네브바 */
.header > .nav {
  position: relative;
  right: 0;
  max-width: 100%;
  width: 100%;
  height: 70px;
  float: right;
  justify-content: space-between;
}

.nav > img {
  width: 130px;
  height: 60px;
  line-height: 80px;
  margin-top: 5px;
}

.nav > a {
  float: left;
  margin-left: 30px;
  line-height: 70px;
  height: 70px;
}

.nav_link {
  font-size: 13px;
  text-decoration: none;
  color: black;
  font-weight: bold;
}

.header_nav {
  margin-right: 20px;
}

.header_nav_menuitem {
  display: inline-block;
  margin-right: 20px;
  height: 70px;
  line-height: 70px;
}

.logo {
  width: 130px;
  height: 90px;
}

/* footer */
.find-us {
  margin-top: 30px;
  position: relative;
  bottom: 0;
  background-color: rgb(248, 248, 248);
  color: gray;
  width: 100%;
  height: 170px;
}

.findElements {
  display: inline-block;
  margin-left: 20px;
  margin-top: 15px;
}

.findtitle {
  font-size: 20px;
  text-decoration: dashed;
  margin-top: 10px;
}

.findadd {
  margin-top: 20px;
  font-size: 13px;
}

.find {
  margin-top: 5px;
  font-size: 13px;
}

.copyright {
  position: relative;
  bottom: 0;
  background-color: rgb(224, 223, 223);
  color: gray;
  width: 100%;
  height: 50px;
  line-height: 50px;
}

.copyright {
  font-size: 12px;
  text-align: center;
}

p.copyright {
  margin: 0;
}

.ssafy {
  width: 70px;
  height: 50px;
  margin: 10px 10px 180px 10px;
}

img.contactimg {
  width: 10px;
  height: 10px;
}

/* 추가 */
.masthead {
  position: relative;
  margin-bottom: 3rem;
  padding-top: calc(8rem + 57px);
  padding-bottom: 8rem;
  background: no-repeat center center;
  background-color: #6c757d;
  background-size: cover;
  background-attachment: scroll;
}

.headerName {
  text-align: center;
  color: white;
}
</style>
