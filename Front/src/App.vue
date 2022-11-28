<template>
  <div id="app">
    <header-nav class="nav" />
    <div style="min-height: 592px; margin-top: 110px">
      <router-view />
    </div>
    <div @click="gotop" style="cursor: pointer">
      <img src="@/assets/img/upload.png" class="up" />
    </div>
    <footer-info />
  </div>
</template>

<script>
import HeaderNav from "@/components/common/HeaderNav.vue";
import FooterInfo from "@/components/common/FooterInfo.vue";
export default {
  name: "App",
  components: {
    HeaderNav,
    FooterInfo,
  },
  methods: {
    gotop() {
      window.scrollTo(0, 0);
    },
  },
};
</script>
<style>
#app {
  font-family: "Pretendard-Regular", Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
@font-face {
  font-family: "Pretendard-Regular";
  src: url("@/common/font/Pretendard-Regular.ttf");
}

@font-face {
  font-family: "Pretendard-SemiBold";
  src: url("@/common/font/Pretendard-SemiBold.ttf");
}
/* * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style: none;
} */
.text-center {
  text-align: center;
}
.container {
  margin: 0px 30px;
}
.App {
  text-align: center;
  font-size: 50px;
  font-family: this_is_font_name;
}
.up {
  width: 40px;
  height: 40px;
  position: fixed;
  right: 50px;
  bottom: 50px;
  z-index: 9999;
  background-color: rgba(255, 255, 255, 0.644);
  border-radius: 50%;
}

.nav {
  position: fixed;
  top: 0;
  /* width: 100% */
  left: 0;
  right: 0;
  z-index: 999;
}

.row.footer {
  padding: 0;
}
</style>
