<template>
  <div class="container">
    <div style="margin-top: 150px">
      <div align="center" class="mb-4">
        <label style="font-size: 25px">쉽고 빠르게 동으로 매물을 검색하세요!</label>
      </div>
      <b-row>
        <b-col cols="3"></b-col>
        <b-col cols="6">
          <b-row>
            <b-col cols="10" style="height: 50px; line-height: 50px; margin-left: 20px">
              <house-search-bar></house-search-bar>
            </b-col>
            <b-col style="width: 20%; margin-left: -20px; height: 50px; line-height: 50px">
              <img src="@/assets/btn/search.png" @click="goSearch" class="link" style="width: 30px; height: 30px; cursor: pointer" />
            </b-col>
          </b-row>
        </b-col>
        <b-col cols="3"> </b-col>
      </b-row>
    </div>
    <!--이곳에 나중에 차트가 들어가면 됩니당!-->
    <!-- <div style="text-align: center; padding: 30px; height: 90px"> -->
    <b-row>
      <b-col cols="2"></b-col>
      <b-col cols="8" class="mt-2 mb-2">
        <!-- <img src="@/assets/img/chart.jpeg" style="width: 600px; height: 400px; margin-top: 50px" /> -->
        <!-- <vue-charts></vue-charts> -->
        <line-chart-2> </line-chart-2>
      </b-col>
      <b-col cols="2"></b-col>
    </b-row>

    <div class="mt-5">
      <section class="main-container">
        <div class="container">
          <div class="row grid-space-10" style="margin-top: 15px; margin-bottom: 40px">
            <div class="col-md-6">
              <div class="col left-section" style="text-align: center; font-size: 25px; margin-bottom: 5px">
                <a style="font-weight: bold">공지사항</a>
              </div>
              <div style="text-align: right; margin-right: 60px; margin-bottom: 5px; font-size: 15px">
                <a href="/notice" style="text-decoration: none; color: rgb(61, 61, 61); margin-right: 20px">더보기</a>
              </div>
              <main-notice-item class="py-1" v-for="index in 6" :key="index" :notice="notices[index]" style="padding-left: 15px"></main-notice-item>
            </div>

            <div class="col-md-6">
              <div class="col right-section" style="text-align: center; font-size: 25px; margin-bottom: 5px">
                <a style="font-weight: bold">부동산 뉴스</a>
              </div>
              <div style="text-align: right; margin-right: 60px; margin-bottom: 5px; font-size: 15px; text-decoration: none">
                <a href="https://land.naver.com/news/" style="text-decoration: none; color: rgb(61, 61, 61); margin-right: 20px">더보기</a>
              </div>
              <main-news-item class="py-1" v-for="(news, index) in newses" :key="index" :news="news" style="padding-left: 15px"></main-news-item>
            </div>
          </div>
          <!-- main end -->
        </div>
      </section>
    </div>
    <!-- main-container end -->
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import MainNoticeItem from "@/components/main/MainNoticeItem.vue";
import MainNewsItem from "@/components/main/MainNewsItem.vue";
// import VueCharts from "@/components/main/VueCharts.vue";
// import LineChart from "@/components/main/LineChart.vue";
import LineChart2 from "@/components/main/LineChart2.vue";
// import MainMap from "@/components/main/MainMap.vue";

const houseStore = "houseStore";

export default {
  name: "HomeView",
  components: {
    HouseSearchBar,
    MainNoticeItem,
    MainNewsItem,
    // LineChart,
    LineChart2,
    // MainMap,
    // VueCharts,
  },
  computed: {
    ...mapState("newsStore", ["newses"]),
    ...mapState("noticeStore", ["notices"]),
  },

  methods: {
    ...mapMutations(houseStore, ["CLEAR_SI_GUN_DONG"]),
    ...mapActions("newsStore", ["getNews"]),
    ...mapActions("noticeStore", ["getnoticeList"]),
    goSearch() {
      this.$router.push({ name: "houseList" });
    },
  },
  created() {
    this.CLEAR_SI_GUN_DONG();
    this.getNews();
    this.getnoticeList();
  },
};
</script>
<style>
.headerName {
  font-weight: bold;
}

.headerName > h1 {
  font-weight: bold;
  font-size: 60px;
}

.headerName > span {
  font-weight: bold;
  font-size: 30px;
}

p {
  margin: 0;
}

a {
  text-decoration: none;
  color: grey;
}

/* searchSection */
.searchSection {
  width: 100%;
  height: 80px;
  background-color: rgb(61, 61, 61);
  text-align: center;
  line-height: 75px;
}

.dropdown {
  display: inline-block;
}
</style>
