<template>
  <div class="map_wrap">
    <div id="map" style="width: 100%; height: 100%; position: relative; overflow: hidden"></div>
  </div>
</template>

<script>
import geojson from "@/assets/TL_SCCO_CTPRVN";
export default {
  name: "MainMap",
  data() {
    return {
      geojson: geojson,
    };
  },
  created() {
    this.initMap();
  },
  mounted() {
    console.log("mounted");
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
      console.log("init map");
      const mapContainer = document.getElementById("map");
      const mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 15,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(mapContainer, mapOption);

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.showPolygon();
    },
    showPolygon() {
      console.log("showpolygon");
      // 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다
      //var polygonPath = [
      // new kakao.maps.LatLng(33.45133510810506, 126.57159381623066),
      // new kakao.maps.LatLng(33.44955812811862, 126.5713551811832),
      // new kakao.maps.LatLng(33.449986291544086, 126.57263296172184),
      // new kakao.maps.LatLng(33.450682513554554, 126.57321034054742),
      // new kakao.maps.LatLng(33.451346760004206, 126.57235740081413),
      //];

      // var polygonPath = [];

      const data = geojson.features;
      const coordinates = data[0].geometry.coordinates[0];
      // console.log("data :", data[0].geometry.coordinates[0]);
      console.log("data: ", data);

      // for (var i = 0; i < data.length; i++) {
      var polygonPath = [];
      //   const LocationArray = data[i].geometry.coordinates[0];

      //   for (var j = 0; j < LocationArray.length; i++) {
      //     var coordinate = LocationArray[j];
      //     polygonPath.push(new kakao.maps.LatLng(coordinate[0], coordinate[1]));
      //   }

      //   var polygon = new kakao.maps.Polygon({
      //     path: polygonPath, // 그려질 다각형의 좌표 배열입니다
      //     strokeWeight: 3, // 선의 두께입니다
      //     strokeColor: "#39DE2A", // 선의 색깔입니다
      //     strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
      //     strokeStyle: "longdash", // 선의 스타일입니다
      //     fillColor: "#A2FF99", // 채우기 색깔입니다
      //     fillOpacity: 0.7, // 채우기 불투명도 입니다
      //   });

      //   // 지도에 다각형을 표시합니다
      //   polygon.setMap(this.map);
      // }

      // console.log(coordinates[0]);
      coordinates.forEach((element) => {
        console.log(element[0], element[1]);
        polygonPath.push(new kakao.maps.LatLng(element[0], element[1]));
      });

      // 지도에 표시할 다각형을 생성합니다
      var polygon = new kakao.maps.Polygon({
        path: polygonPath, // 그려질 다각형의 좌표 배열입니다
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: "#39DE2A", // 선의 색깔입니다
        strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: "longdash", // 선의 스타일입니다
        fillColor: "#A2FF99", // 채우기 색깔입니다
        fillOpacity: 0.7, // 채우기 불투명도 입니다
      });

      // 지도에 다각형을 표시합니다
      polygon.setMap(this.map);
      this.map.setCenter(polygonPath[0]);
    },
  },
};
</script>

<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 500px;
}
</style>
