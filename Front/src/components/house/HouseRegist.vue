<template>
  <div class="container" style="border-top: 1px grey solid; margin-top: 10px">
    <div class="form-group container">
      <div class="row mt-2">
        <div class="col-2" style="padding: 0; text-align: left">
          <label class="label-id">이름</label>
        </div>
        <div class="col" style="padding: 0">
          <div class="col" style="padding-right: 0">
            <b-form-input id="input-liveid-aptName" v-model="info.aprtmentName" placeholder="매물이름" trim @keyup.enter="confirm"></b-form-input>
          </div>
        </div>
      </div>
    </div>
    <div class="form-group container-fluid">
      <div class="row">
        <div class="col-2" style="padding: 0; text-align: left">
          <label class="label-id">주소</label>
        </div>
        <div class="col" style="padding: 0">
          <b-row>
            <b-col cols="11">
              <b-form-input id="input-liveid-area" v-model="address" placeholder="시/도  구/군  동  상세주소" trim @keyup.enter="confirm"></b-form-input>
            </b-col>
            <b-col>
              <img src="@/assets/btn/placeholder.png" @click="getInfo" style="width: 40px; height: 40px; cursor: pointer" />
            </b-col>
          </b-row>
        </div>
      </div>
    </div>
    <div class="form-group container-fluid">
      <div class="row">
        <div id="map" style="width: 100%; height: 500px; position: relative; overflow: hidden"></div>
      </div>
    </div>

    <div class="form-group container-fluid">
      <div class="row">
        <div class="col-2" style="padding: 0; text-align: left">
          <label class="label-id">건축년도</label>
        </div>
        <div class="col" style="padding: 0">
          <b-form-input id="input-liveid-buildYear" v-model="info.buildYear" placeholder="건축년도" trim @keyup.enter="confirm"></b-form-input>
        </div>
      </div>
    </div>

    <div class="form-group container-fluid">
      <div class="row">
        <div class="col-2" style="padding: 0; text-align: left">
          <label class="label-id" for="input-live-dealAmout">거래 희망 가격</label>
        </div>
        <div class="col" style="padding: 0">
          <b-form-input id="input-liveid-dealAmout" v-model="deal.dealAmount" placeholder="거래희망가격(3자리수마다 ,로 구분하여 작성)" trim @keyup.enter="confirm"></b-form-input>
        </div>
      </div>
    </div>

    <div class="form-group container-fluid">
      <div class="row">
        <div class="col-2" style="padding: 0; text-align: left">
          <label class="label-id" for="input-live-area">면적</label>
        </div>
        <div class="col" style="padding: 0">
          <b-form-input id="input-liveid-area" v-model="deal.area" placeholder="면적" trim @keyup.enter="confirm"></b-form-input>
        </div>
      </div>
    </div>

    <div class="form-group container-fluid">
      <div class="row">
        <div class="col-2" style="padding: 0; text-align: left; margin-bottom: 0px">
          <label class="label-id" for="input-live-floor">층수</label>
        </div>
        <div class="col" style="padding: 0">
          <b-form-input id="input-liveid-floor" v-model="deal.floor" placeholder="층수" trim @keyup.enter="confirm"></b-form-input>
        </div>
      </div>
    </div>

    <div class="form-group container-fluid">
      <div class="row">
        <div class="col-2" style="padding: 0; text-align: left; margin-bottom: 0px">
          <label class="label-id" for="input-live-description">설명</label>
        </div>
        <div class="col" style="padding: 0">
          <b-form-input id="input-liveid-description" v-model="detail.description" placeholder="설명" trim @keyup.enter="confirm"></b-form-input>
        </div>
      </div>
    </div>
    <div class="mt-2 mb-4" style="text-align: right">
      <!-- <img src="@/assets/btn/cloud-computing.png" @click="regist" style="width: 60px; height: 60px; cursor: pointer" /> -->
      <img src="@/assets/btn/submit3.jpg" @click="regist" style="width: 80px; height: 50px; cursor: pointer" />
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import swal from "sweetalert";
export default {
  data() {
    return {
      deal: {
        dealAmount: "",
        area: "",
        floor: "",
      },
      detail: {
        description: "",
      },
      info: {
        buildYear: "",
        dong: "",
        sigunguCode: "",
        eubmyundongCode: "",
        dongCode: "",
        aprtmentName: "",
        lng: "",
        lat: "",
      },
      address: "",
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ad9f987344bc71ba15bbda2b09d4c4d7&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const mapContainer = document.getElementById("map");
      const mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(mapContainer, mapOption);
    },

    getInfo() {
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      let base = this.info;
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(this.address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          base.lat = result[0].y;
          base.lng = result[0].x;

          const mapContainer = document.getElementById("map");
          const mapOption = {
            center: coords,
            level: 3,
          };

          var marker = new kakao.maps.Marker({
            // 지도 중심좌표에 마커를 생성합니다
            position: coords,
          });
          // 지도에 마커를 표시합니다
          marker.setMap(new kakao.maps.Map(mapContainer, mapOption));
        }
      });
    },
    async regist() {
      await http
        .post(`/map/registInfo/`, null, {
          params: { address: this.address, buildYear: this.info.buildYear, dong: this.info.dong, apartmentName: this.info.aprtmentName, lng: this.info.lng, lat: this.info.lat },
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "판매 등록이 완료되었습니다.";
          }
          swal(msg);
        });
      await http.post(`/map/registDeal/`, null, { params: { dealAmount: this.deal.dealAmount, area: this.deal.area, floor: this.deal.floor } }).then(({ data }) => {
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "판매 등록이 완료되었습니다.";
        }
        swal(msg);
      });
      await http.post(`/map/registDetail/`, null, { params: { description: this.detail.description } }).then(({ data }) => {
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "판매 등록이 완료되었습니다.";
        }
        swal(msg);
        this.$router.push({ name: "home" });
      });
    },
  },
};
</script>

<style></style>
