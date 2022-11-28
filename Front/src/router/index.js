import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

import store from "@/store/index.js";
import swal from "sweetalert";

Vue.use(VueRouter);

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
    swal("서비스를 이용하시려면 로그인이 필요합니다.");
    next({ name: "home" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/admin/userlist",
    name: "userlist",
    component: () => import("@/views/ListUserView.vue"),
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/MemberView.vue"),
  },
  {
    path: "/houseList",
    name: "houseList",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/HouseListView.vue"),
  },
  {
    path: "/houseDetail",
    name: "houseDetail",
    component: () => import("@/views/HouseDetailView.vue"),
  },
  {
    path: "/interest",
    name: "interest",
    component: () => import("@/views/InterestView.vue"),
    redirect: "/interest",
    children: [
      {
        path: "interestList",
        name: "interestList",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/interest/InterestList.vue"),
      },
    ],
  },
  {
    path: "/interesthouse",
    name: "interesthouse",
    component: () => import("@/views/InteresthouseView.vue"),
    redirect: "/interesthouse",
    children: [
      {
        path: "interesthouseList",
        name: "interesthouseList",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/interesthouse/InteresthouseList.vue"),
      },
    ],
  },
  {
    path: "/agent/regist",
    name: "houseRegist",
    component: () => import("@/views/HouseRegistView.vue"),
  },
  {
    path: "/notice",
    name: "notice",
    component: () => import("@/views/NoticeView.vue"),
    redirect: "/notice/index",
    children: [
      {
        path: "index",
        name: "noticeList",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeList.vue"),
      },
      {
        path: "regist",
        name: "noticeRegister",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeRegister.vue"),
      },
      {
        path: "detail/:no",
        name: "noticeDetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeDetail.vue"),
      },
      {
        path: "update/:no",
        name: "noticeModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeModify.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
    redirect: "/board/index",
    children: [
      {
        path: "index",
        name: "boardList",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "regist",
        name: "boardRegister",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardRegister.vue"),
      },
      {
        path: "detail/:no",
        name: "boardDetail",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "update/:no",
        name: "boardModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
