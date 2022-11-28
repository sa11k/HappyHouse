<template>
  <div class="map_wrap mt-3">
    <ul id="category">
      <li id="BK9" data-order="0">
        <!-- <span class="category_bg bank"></span> -->
        <div class="category_bg bank"></div>
        은행
      </li>
      <li id="PM9" data-order="1">
        <div class="category_bg pharmacy"></div>
        약국
      </li>
      <li id="MT1" data-order="2">
        <div class="category_bg mart"></div>
        마트
      </li>
      <li id="CE7" data-order="3">
        <div class="category_bg cafe"></div>
        카페
      </li>
      <!-- <li id="OL7" data-order="3">
        <div class="category_bg oil"></div>
        주유소
      </li> -->
      <li id="CS2" data-order="4">
        <div class="category_bg store"></div>
        편의점
      </li>
    </ul>
    <div id="map"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  data() {
    return {};
  },
  computed: {
    ...mapState(houseStore, ["house"]),
  },
  mounted() {
    console.log("mounted");

    if (window.kakao && window.kakao.maps) {
      console.log("if");
      this.initMap();
    } else {
      console.log("else");
      const script = document.createElement("script");
      /* global kakao */
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ad9f987344bc71ba15bbda2b09d4c4d7&libraries=services";
      document.head.appendChild(script);
      script.onload = () => kakao.maps.load(this.initMap());
    }
  },

  methods: {
    initMap() {
      console.log("initMap");
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 }); // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      this.contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      this.markers = []; // 마커를 담을 배열입니다

      this.currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다
      const mapContainer = document.getElementById("map");
      const mapOption = {
        center: new kakao.maps.LatLng(this.house.lat, this.house.lng),
        level: 3,
      };

      this.map = new kakao.maps.Map(mapContainer, mapOption);
      this.displayHouseMarker();
      this.ps = new kakao.maps.services.Places(this.map); // 장소 검색 객체를 생성합니다
      // 지도에 idle 이벤트를 등록합니다
      //지도에 'idle' 이벤트를 등록해서 지도의 중심이나 확대수준이 변경되었을 때 마다 사용자가 선택한 카테고리에 해당하는 장소를 검색하여 검색결과를 마커로 표시합니다. 마커를 클릭하면 장소정보를 커스텀 오버레이로 표시합니다.
      // kakao.maps.event.addListener(this.map, "idle", this.searchPlaces());
      kakao.maps.event.addListener(
        this.map,
        "idle",
        function () {
          this.searchPlaces();
        }.bind(this)
      );
      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, "mousedown", kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, "touchstart", kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      this.addCategoryClickEvent();
    },
    displayHouseMarker() {
      var placePosition = new kakao.maps.LatLng(this.house.lat, this.house.lng);
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        // var imageSrc = require("../../assets/img/free-icon-convenience-store.png"),
        imageSize = new kakao.maps.Size(24, 35), // 마커 이미지의 크기
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
        marker = new kakao.maps.Marker({
          position: placePosition, // 마커의 위치
          image: markerImage,
          title: this.house.apartmentName,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
    },
    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },
    // 각 카테고리에 클릭 이벤트를 등록합니다
    addCategoryClickEvent() {
      var category = document.getElementById("category"),
        children = category.children;

      console.log("addcategoryclickevet");
      console.log(children.length);

      for (var i = 0; i < children.length; i++) {
        (function (id, className) {
          children[i].onclick = function () {
            this.onClickCategory(id, className);
          }.bind(this);
        }.bind(this)(children[i].id, children[i].className));
      }
    },
    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(id, className) {
      console.log(id, className);
      console.log("clicked");

      this.placeOverlay.setMap(null);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        this.currCategory = id;
        this.changeCategoryClass(id);
        this.searchPlaces();
      }
    },
    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(id) {
      var category = document.getElementById("category"),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (id) {
        var selected = document.getElementById(id);
        selected.className = "on";
      }
    },
    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      // this.ps.categorySearch(this.currCategory, this.placesSearchCB, { useMapBounds: true });
      this.ps.categorySearch(
        this.currCategory,
        (data, status) => {
          if (status === kakao.maps.services.Status.OK) {
            // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
            this.displayPlaces(data);
          } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
          } else if (status === kakao.maps.services.Status.ERROR) {
            // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
          }
        },
        { useMapBounds: true }
      );
    },
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    // placesSearchCB(data, status, pagination) {
    // placesSearchCB(data, status) {
    //   if (status === kakao.maps.services.Status.OK) {
    //     // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
    //     this.displayPlaces(data);
    //   } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    //     // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
    //   } else if (status === kakao.maps.services.Status.ERROR) {
    //     // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
    //   }
    // },
    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document.getElementById(this.currCategory).getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (marker, place) {
          kakao.maps.event.addListener(
            marker,
            "mouseover",
            function () {
              this.displayPlaceInfo(place);
            }.bind(this)
          );

          kakao.maps.event.addListener(
            marker,
            "mouseout",
            function () {
              this.placeOverlay.setMap(null);
            }.bind(this)
          );
        }.bind(this)(marker, places[i]));
      }
    },
    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, order) {
      var imageSrc = "";
      console.log(order);
      if (order == 0) {
        imageSrc = "http://drive.google.com/uc?export=view&id=1aVnWN91513fPYoCn-RCyPlc6nD6heknR";
      } else if (order == 1) {
        imageSrc = "http://drive.google.com/uc?export=view&id=1AizhDCxe_NDRFUg96rURJsvmFWjK9k_T";
      } else if (order == 2) {
        imageSrc = "http://drive.google.com/uc?export=view&id=1f4yvGsPq8v45D8TahgRrmpWj6s2_jxdZ";
      } else if (order == 3) {
        imageSrc = "http://drive.google.com/uc?export=view&id=1FrUV8TWLhSCtkbnaSyU1Qic0NpS87Q6s";
      } else if (order == 4) {
        imageSrc = "http://drive.google.com/uc?export=view&id=1hG7VyYyqS7QmnDMb1H8PM3ZR16sx9oK8";
      }
      // var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
      var imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        // imgOptions = {
        //   spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
        //   spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
        //   offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        // },
        // markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content = `
      <div class="placeinfo">
          <a class="title" href="${place.place_url}" target="_blank" title="${place.place_name}">${place.place_name}</a>
      `;

      if (place.road_address_name) {
        content += `
          <span title="${place.road_address_name}">${place.road_address_name}</span>
          <span class="jibun" title="${place.address_name}">(지번 : ${place.address_name}</span>
        `;
      } else {
        content += `
        <span title="${place.address_name}">${place.address_name}</span>
        `;
      }

      content += `
        <span class="tel">${place.phone}</span>
      </div>
      <div class="after"></div>
      `;

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
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
.map_wrap {
  position: relative;
  width: 100%;
  height: 450px;
}
#map {
  /* width: 500px;
  height: 500px;
  line-height: 100vh;
  margin: 0 auto; */

  top: 100px;
  left: 0px;
  width: 800px;
  height: 500px;
}
#category {
  position: absolute;
  top: 10px;
  left: 250px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 55px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
/* #category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
} */
#category li div {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  /* background: url(https://www.flaticon.com/kr/free-icons/) no-repeat; */
  /* background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat; */
  /* width: "30px"; */
  /* height: "30px"; */
  /* background-image: require url("../../assets/img/free-icon-convenience-store.png") center no-repeat; */
  /* background-image: require("../../assets/img/test.jpg") center no-repeat; */
  width: 35px;
  height: 30px;
}
#category li .bank {
  background: url("http://drive.google.com/uc?export=view&id=1aVnWN91513fPYoCn-RCyPlc6nD6heknR") center no-repeat;
  /* background-position: -10px 0;
  background-position: -10px 0; */
}
#category li .mart {
  background: url("http://drive.google.com/uc?export=view&id=1f4yvGsPq8v45D8TahgRrmpWj6s2_jxdZ") center no-repeat;
  /* background: url("../../assets/img/category/mart.png") center no-repeat; */
  /* background-position: -10px -36px; */
}
#category li .pharmacy {
  background: url("http://drive.google.com/uc?export=view&id=1AizhDCxe_NDRFUg96rURJsvmFWjK9k_T") center no-repeat;
  /* background-position: -10px -72px; */
}
#category li .oil {
  background: url("http://drive.google.com/uc?export=view&id=1hvftfBDd0nu-gUMeODw1_gUHkVT5wir0") center no-repeat;
  /* background-position: -10px -108px; */
}
#category li .cafe {
  background: url("http://drive.google.com/uc?export=view&id=1FrUV8TWLhSCtkbnaSyU1Qic0NpS87Q6s") center no-repeat;
  /* background-position: -10px -144px; */
}
#category li .store {
  background: url("http://drive.google.com/uc?export=view&id=1hG7VyYyqS7QmnDMb1H8PM3ZR16sx9oK8") center no-repeat;
  /* background-position: -10px -180px; */
}
#category li.on .category_bg {
  /* background-position-x: -46px; */
}

.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  /* background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png"); */
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  /* background: #d95050 url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png") no-repeat right 14px center; */
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
