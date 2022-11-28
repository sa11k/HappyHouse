<template>
  <div class="container" style="margin-top: 130px">
    <div class="numberofinterest">
      <!-- <img src="@/assets/img/test.jpg" /> -->
      <div style="margin-top: 30px; margin-left: 40px">
        <h5><b>매물 관심 현황</b></h5>
      </div>
      <span style="margin-left: 40px"><img src="@/assets/img/search.png" style="width: 30px; height: 30px" />&nbsp;</span
      ><span style="text-size: 10px"
        ><b>{{ detail[0].hit }}</b></span
      ><span> 명이 조회했습니다.</span>
      <br />
      <!-- <span style="margin-left: 55px">(오늘기준)</span> -->
    </div>
    <div class="app">
      <carousel-view>
        <!-- <carousel-slide v-for="slide in slides" :key="slide" class="carousel-slider"> -->
        <carousel-slide v-for="(slide, index) in slides" :key="index" class="carousel-slider">
          <!-- <img :src="slide" :alt="slide" /> -->
          <img :src="slide.imageUrl" :alt="slide.imageUrl" />
        </carousel-slide>
      </carousel-view>
    </div>
    <div class="row">
      <!-- <div style="text-align: center">
        <img src="@/assets/img/apt.png" style="width: 800px; height: 400px" />
      </div> -->
      <div class="mt-5" style="text-align: left">
        <div class="container" style="padding: 0">
          <b-row>
            <b-col style="text-align: left">
              <h4>
                <b>{{ house.apartmentName }}</b>
              </h4>
              <h4>
                <b>전세 {{ house.recentPrice }}만원</b>
              </h4>
            </b-col>
            <b-col cols="2" style="margin-top: -5px; text-align: center; margin-right: 50px">
              <h5><b>찜</b></h5>
              <div v-if="heart">
                <img src="@/assets/btn/heart.png" @click="deleteInterest(house.housedeal_no)" style="width: 40px; height: 40px; cursor: pointer" />
              </div>
              <div v-else>
                <img src="@/assets/btn/like.png" @click="registInterest(userInfo.id, house.housedeal_no)" style="width: 40px; height: 40px; cursor: pointer" />
              </div>
            </b-col>
          </b-row>
        </div>
      </div>
      <div class="mt-3"></div>
      <div class="mt-5" style="text-align: left">
        <h4><b>상세 정보</b></h4>
        <hr />
        <br />
        <h5>건물 이름 &nbsp;&nbsp;&nbsp;&nbsp; {{ house.apartmentName }}</h5>
        <h5>해당층 &nbsp;&nbsp;&nbsp;&nbsp; {{ house.floor }}</h5>
        <h5>전용면적 &nbsp;&nbsp;&nbsp;&nbsp; {{ house.area }}</h5>
        <h5>등록일 &nbsp;&nbsp;&nbsp;&nbsp; {{ house.buildYear }}</h5>
      </div>
      <div class="mt-3"></div>
      <div class="mt-5" style="text-align: left">
        <h4><b>위치 및 주변시설</b></h4>
        <div class="jibun gray">{{ house.sidoName }} {{ house.gugunName }} {{ house.dong }} {{ house.jibun }}</div>
        <hr />
        <div style="height: 600px">
          <!-- <house-detail-map></house-detail-map> -->
          <house-detail-mapv-2></house-detail-mapv-2>
        </div>
      </div>
      <div class="mt-3"></div>
      <div class="mt-5" style="text-align: left">
        <h4><b>상세 설명</b></h4>
        <hr />
        <b-row class="description mt-2" style="text-size: 20; margin-left: 0px">
          <b-col sm="10">
            <b-form-textarea id="textarea-large" size="lg" plaintext :value="detail[0].description" style="width: 700px; height: 300px; border: 1px solid gainsboro"></b-form-textarea>
          </b-col>
        </b-row>
      </div>
      <div class="mt-5" style="text-align: left"></div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import { mapState, mapActions } from "vuex";
// import HouseDetailMap from "@/components/house/HouseDetailMap.vue";
import HouseDetailMapv2 from "@/components/house/HouseDetailMapv2.vue";
import CarouselSlide from "@/components/slider/CarouselSlide";
import CarouselView from "@/components/slider/CarouselView.vue";
import swal from "sweetalert";

const houseStore = "houseStore";

export default {
  name: "HouseDetail",
  data() {
    return {
      heart: false,
      slides: [
        {
          imageUrl: require("@/assets/img/room/room2.jpg"),
        },
        {
          imageUrl: require("@/assets/img/room/room4.jpg"),
        },
        {
          imageUrl: require("@/assets/img/room/room3.jpg"),
        },
        {
          imageUrl: require("@/assets/img/room/room1.jpg"),
        },
        {
          imageUrl: require("@/assets/img/room/room5.jpg"),
        },
        {
          imageUrl: require("@/assets/img/room/room6.jpg"),
        },

        // {
        //   imageUrl: "https://picsum.photos/id/236/600/300",
        // },
      ],
      // slides: [
      //   "../../static/apt.png",
      //   "https://picsum.photos/id/230/600/300",
      //   "https://picsum.photos/id/231/600/300",
      //   "https://picsum.photos/id/232/600/300",
      //   "https://picsum.photos/id/233/600/300",
      //   "https://picsum.photos/id/234/600/300",
      //   "https://picsum.photos/id/235/600/300",
      //   "https://picsum.photos/id/236/600/300",
      // ],
    };
  },
  components: {
    // HouseDetailMap,
    HouseDetailMapv2,
    CarouselView,
    CarouselSlide,
  },
  computed: {
    ...mapState(houseStore, ["house", "detail"]),
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    ...mapActions(houseStore, ["getDetail"]),
    getUserInterest(id, no) {
      http.get(`/map/interest/${id}/${no}`).then(({ data }) => {
        if (data === "success") {
          this.heart = true;
        }
      });
    },
    registInterest(id, no) {
      http.get(`/interesthouse/regist/${id}/${no}`).then(({ data }) => {
        let msg = "찜 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "찜하기 성공";
        }
        swal(msg);
        this.getUserInterest(id, no);
        this.$router.go();
      });
    },
    deleteInterest(no) {
      console.log(typeof no);
      swal({
        title: "정말로 삭제할까요?",
        text: "",
        icon: "info",
        buttons: ["NO", "YES"],
      }).then((YES) => {
        if (YES) {
          http.delete(`/interesthouse/detail/${no}`).then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "삭제가 완료되었습니다.";
            }
            swal(msg);
            this.$router.go();
          });
        }
      });
    },
  },
  created() {
    this.getDetail(this.house.aptCode);
    console.log(this.detail[0]);
    console.log("hit: ", this.detail[0].hit);
    this.getUserInterest(this.userInfo.id, this.house.housedeal_no);
    window.scrollTo({ top: 0, behavior: "smooth" });
  },
  mounted() {},
};
</script>

<style>
.container {
  width: 800px;
}
.row {
  text-align: center;
}
.app {
  display: flex;
  justify-content: center;
}
.carousel {
  position: relative;
  overflow: hidden;
  width: 800px;
  height: 500px;
  z-index: 10;
}
.btn {
  padding: 5px 10px;
  background-color: rgba(0, 0, 0, 0.5);
  border: 1px solid transparent;
  margin: 5px 10px;
  color: #fff;
  height: 50px;
  width: 50px;
  position: absolute;
  margin-top: -25px;
  z-index: 2;
}
.btn:hover {
  cursor: pointer;
}
.btn:focus {
  outline: none;
}
.btn-next {
  top: 50%;
  right: 0;
}
.btn-prev {
  top: 50%;
  left: 0;
}
.carousel-slider {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
}
.carousel-slider img {
  width: 100%;
  height: 100%;
}
.numberofinterest {
  width: 300px;
  height: 120px;
  position: fixed;
  right: 10%;
  /* bottom: 50px; */
  top: 450px;
  z-index: 9999;
  background-color: white;
  border-radius: 2%;
  /* border: 1px solid gray; */
  box-shadow: 0.5px 0.5px 1px 3px gainsboro;
}
.description {
  margin-top: 10px;
  width: 800px;
  height: 400px;
  background-color: #fff;
  border-radius: 2%;
  /* border: 1px solid gainsboro; */
}
</style>
