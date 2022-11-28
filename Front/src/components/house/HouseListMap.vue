<template>
  <div class="map_wrap">
    <div id="map" style="width: 100%; height: 100%; position: relative; overflow: hidden"></div>

    <div v-if="houses && houses.length != 0">
      <div id="apt_wrap" class="bg_white">
        <div class="option">
          <div>
            <house-search-bar class="searchbar" style="margin-bottom: 4px; margin-right: 6px"></house-search-bar>
            <!-- <form onsubmit="searchPlaces(); return false;"> -->
            <b-row>
              <b-col cols="2">
                <p style="margin: 0; height: 20px; line-height: 20px; text-align: left; width: 40px">키워드 :</p>
              </b-col>
              <b-col cols="8">
                <input style="height: 20px; line-height: 20px; width: 150px; margin-left: 10px" size="20" type="text" id="apartmentName" @keyup.enter="searchPlaces" />
              </b-col>
              <b-col cols="2">
                <button style="height: 20px; line-height: 20px" type="submit">검색</button>
              </b-col>
            </b-row>
            <!-- </form> -->
          </div>
        </div>
        <div style="margin-top: 4px; text-align: right; margin-right: 6px">
          <b-form-select :options="sortingOptions" v-model="selected" @change="sortingApt"></b-form-select>
        </div>

        <hr />
        <ul id="aptList"></ul>
        <div id="pagination"></div>
      </div>
    </div>
    <div v-else>
      <div>주택 목록이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
// import http from "@/api/http";
import axios from "axios";
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";

const houseStore = "houseStore";
const storeStore = "storeStore";

export default {
  name: "HouseListMap",
  components: { HouseSearchBar },
  data() {
    return {
      markerPositions: [],
      markers: [],
      listSorted: [],
      selected: null,
      sortingOptions: [
        { value: null, text: "인기순" },
        { value: "lower", text: "가격 낮은 순" },
        { value: "higher", text: "가격 높은 순" },
      ],
    };
  },
  computed: {
    ...mapState(houseStore, ["houses", "dong", "address"]),
  },
  watch: {
    houses: function (n) {
      console.log("watch");
      this.initMap();
      this.displayMarker(n);
      var location = document.querySelector(".main-container").offsetTop - 100;
      window.scrollTo({ top: location, behavior: "smooth" });
    },
  },
  created() {
    this.getHouseList(this.dong); // store houses 다시 load
    this.getAddress(this.dong); // store address load
  },
  mounted() {
    console.log("mounted");
    if (window.kakao && window.kakao.maps) {
      console.log("if");
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ad9f987344bc71ba15bbda2b09d4c4d7&libraries=services";
      document.head.appendChild(script);
      console.log("else");
    }
  },
  methods: {
    ...mapActions(storeStore, ["getCafe", "getRestaurant"]),
    ...mapActions(houseStore, ["getHouseList", "getAddress"]),
    ...mapMutations(houseStore, ["SET_DETAIL_HOUSE"]),

    sortingApt() {
      console.log("selected: ", this.selected);

      if (this.selected == "lower") {
        // this.houseList = this.mergeSortLower(this.houseList);
        if (this.listSorted.length == 0) {
          this.listSorted = this.mergeSortLower(this.houses);
        } else {
          this.listSorted = this.mergeSortLower(this.listSorted);
        }
      } else if (this.selected == "higher") {
        if (this.listSorted.length == 0) {
          this.listSorted = this.mergeSortHigher(this.houses);
        } else {
          this.listSorted = this.mergeSortHigher(this.listSorted);
        }
      }

      this.displayMarker(this.listSorted);
    },
    mergeSortHigher(array) {
      // ending condition: length === 1 인 배열이 들어올 때, 정렬이 끝난 것.
      if (array.length === 1) return array;

      // 2로 나누고 내림을 해야
      // length 가 2일 때도 안전하게 배열을 slice 할 수 있다.
      const middleIndex = Math.floor(array.length / 2);
      const left = array.slice(0, middleIndex);
      const right = array.slice(middleIndex);

      // 재귀로 계속해서 반으로 나누면서 length 가 1이 될때까지 쪼개고,
      // 거꾸로 올라오면서 순수한 함수인 merge에 인자로 넣어서 다시 병합되어서 최종값을 리턴한다.
      return this.mergeHighter(this.mergeSortHigher(left), this.mergeSortHigher(right));
    },
    mergeHighter(left, right) {
      // left, right already sorted
      const result = [];
      while (left.length !== 0 && right.length !== 0) {
        left[0].recentPrice >= right[0].recentPrice ? result.push(left.shift()) : result.push(right.shift());
      }

      return [...result, ...left, ...right]; // 아래 세줄과 같은 역할을 하는 코드
      // if(left.length === 0) results.push(...right);
      // if(right.length === 0) results.push(...left);
      // return results;
    },
    mergeSortLower(array) {
      // ending condition: length === 1 인 배열이 들어올 때, 정렬이 끝난 것.
      if (array.length === 1) return array;

      // 2로 나누고 내림을 해야
      // length 가 2일 때도 안전하게 배열을 slice 할 수 있다.
      const middleIndex = Math.floor(array.length / 2);
      const left = array.slice(0, middleIndex);
      const right = array.slice(middleIndex);

      // 재귀로 계속해서 반으로 나누면서 length 가 1이 될때까지 쪼개고,
      // 거꾸로 올라오면서 순수한 함수인 merge에 인자로 넣어서 다시 병합되어서 최종값을 리턴한다.
      return this.mergeLower(this.mergeSortLower(left), this.mergeSortLower(right));
    },
    mergeLower(left, right) {
      // left, right already sorted
      const result = [];
      while (left.length !== 0 && right.length !== 0) {
        left[0].recentPrice <= right[0].recentPrice ? result.push(left.shift()) : result.push(right.shift());
      }

      return [...result, ...left, ...right]; // 아래 세줄과 같은 역할을 하는 코드
      // if(left.length === 0) results.push(...right);
      // if(right.length === 0) results.push(...left);
      // return results;
    },

    initMap() {
      console.log("init map");
      const mapContainer = document.getElementById("map");
      const mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(mapContainer, mapOption);
      // 장소 검색 객체를 생성합니다
      // this.ps = new kakao.maps.services.Places();

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      // 키워드로 장소를 검색합니다
      // this.searchPlaces();
      this.displayMarker(this.houses);
    },

    // 키워드 검색을 요청하는 함수입니다
    searchPlaces(e) {
      console.log("address: ", this.address.data);
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(
        this.address.data,
        function (result, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            console.log(result[0].y, result[0].x);
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            // // 결과값으로 받은 위치를 마커로 표시합니다
            // var marker = new kakao.maps.Marker({
            //   map: this.map,
            //   position: coords,
            // });
            // // 인포윈도우로 장소에 대한 설명을 표시합니다
            // var infowindow = new kakao.maps.InfoWindow({
            //   content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>',
            // });
            // infowindow.open(map, marker);
            // // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            this.map.setCenter(coords);
          }
        }.bind(this)
      );

      // const aptName = document.getElementById("aptName").value;
      // console.log(aptName);
      // if (!aptName.replace(/^\s+|\s+$/g, "")) {
      //   alert("키워드를 입력해주세요!");
      //   return false;
      // }
      // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
      //this.ps.keywordSearch(aptName, this.placesSearchCB);

      const keyword = e.target.value.trim();
      if (keyword.length === 0) {
        alert("검색 결과가 없습니다.");
        return;
      }

      const ps = new window.kakao.maps.services.Places();
      ps.keywordSearch(keyword, (data, status, pagination) => {
        console.log(data);
        // console.log(status);
        console.log(pagination);
        if (status === kakao.maps.services.Status.OK) {
          this.displayMarker2(data);
          return;
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          alert("검색 결과가 존재하지 않습니다.");
          return;
        } else if (status === kakao.maps.services.Status.ERROR) {
          alert("검색 결과 중 오류가 발생했습니다.");
          return;
        }
      });
    },

    // 검색 결과 목록과 마커를 표출하는 함수입니다
    displayMarker2(places) {
      var listEl = document.getElementById("aptList"),
        aptEl = document.getElementById("apt_wrap"),
        fragment = document.createDocumentFragment();
      // bounds = new kakao.maps.LatLngBounds();

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      this.removeAllChildNods(listEl);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
          marker = this.addMarker(placePosition, i),
          itemEl = this.getListItem2(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        // bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function (marker, place) {
          // (function (marker, title, price) {
          kakao.maps.event.addListener(
            marker,
            "mouseover",
            function () {
              this.displayInfowindow(marker, place.place_name, place.address_name);
            }.bind(this)
          );

          kakao.maps.event.addListener(
            marker,
            "mouseout",
            function () {
              this.infowindow.close();
            }.bind(this)
          );

          kakao.maps.event.addListener(
            marker,
            "click",
            function () {
              alert("매매 상세 결과가 없습니다.");
            }.bind(this)
          );

          itemEl.onmouseover = function () {
            this.displayInfowindow(marker, place.place_name, place.address_name);
          }.bind(this);

          itemEl.onmouseout = function () {
            this.infowindow.close();
          }.bind(this);

          itemEl.onclick = function () {
            alert("매매 상세 결과가 없습니다.");
          }.bind(this);
        }.bind(this)(marker, places[i]));

        fragment.appendChild(itemEl);
      }

      // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
      listEl.appendChild(fragment);
      aptEl.scrollTop = 0;

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      // this.map.setBounds(bounds);
    },

    // 검색결과 항목을 Element로 반환하는 함수입니다
    getListItem2(index, places) {
      var el = document.createElement("li");
      var itemStr = `
        <span class="markerbg marker_${index + 1}"></span>
        <div class="info">
          <h5>${places.place_name}</h5>
      `;

      if (places.road_address_name) {
        itemStr += `
          <span>${places.road_address_name}</span>
        `;
      } else {
        itemStr += `
          <span class="jibun gray">${places.address_name}</span>
        `;
      }

      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },

    // //장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    // placesSearchCB(data, status, pagination) {
    //   if (status === kakao.maps.services.Status.OK) {
    //     // 정상적으로 검색이 완료됐으면
    //     // 검색 목록과 마커를 표출합니다
    //     this.displayMarker(data);

    //     // 페이지 번호를 표출합니다
    //     this.displayPagination(pagination);
    //   } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    //     alert("검색 결과가 존재하지 않습니다.");
    //     return;
    //   } else if (status === kakao.maps.services.Status.ERROR) {
    //     alert("검색 결과 중 오류가 발생했습니다.");
    //     return;
    //   }
    // },

    // 검색 결과 목록과 마커를 표출하는 함수입니다
    displayMarker(places) {
      var listEl = document.getElementById("aptList"),
        aptEl = document.getElementById("apt_wrap"),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds();
      // listStr = "";

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      this.removeAllChildNods(listEl);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].lat, places[i].lng),
          marker = this.addMarker(placePosition, i),
          itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function (marker, place, aptCode) {
          // (function (marker, title, price) {
          kakao.maps.event.addListener(
            marker,
            "mouseover",
            function () {
              this.displayInfowindow(marker, place.apartmentName, place.recentPrice);
            }.bind(this)
          );

          kakao.maps.event.addListener(
            marker,
            "mouseout",
            function () {
              this.infowindow.close();
            }.bind(this)
          );

          kakao.maps.event.addListener(
            marker,
            "click",
            function () {
              this.getCafe(this.dong); //동에 해당하는 카페정보를 store에 저장
              this.getRestaurant(this.dong);
              this.SET_DETAIL_HOUSE(place); // house 셋팅
              // console.log("updateHit", aptCode);
              this.updateHit(aptCode); //hit 1 증가
            }.bind(this)
          );

          itemEl.onmouseover = function () {
            this.displayInfowindow(marker, place.apartmentName, place.recentPrice);
          }.bind(this);

          itemEl.onmouseout = function () {
            this.infowindow.close();
          }.bind(this);

          itemEl.onclick = function () {
            this.getCafe(this.dong); //동에 해당하는 카페정보를 store에 저장
            this.getRestaurant(this.dong);
            this.SET_DETAIL_HOUSE(place); // house 셋팅
            // console.log("updateHit", aptCode);
            this.updateHit(aptCode); //hit 1 증가
          }.bind(this);
        }.bind(this)(marker, places[i], places[i].aptCode));

        fragment.appendChild(itemEl);
      }

      // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
      listEl.appendChild(fragment);
      aptEl.scrollTop = 0;

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },

    updateHit(aptCode) {
      console.log("updateHit=", aptCode);
      const params = { aptCode: aptCode };
      //http.put(`/map/updatehit`, params).then(({ data }) => {
      axios({
        url: "http://localhost:9090/hh/map/updatehit",
        method: "put",
        params: params,
      }).then(({ data }) => {
        let msg = "수정 처리 시 문제가 발생했습니다.";
        if (data === "fail") {
          msg = "문제가 발생했습니다.";
        }
        if (data === "success") {
          msg = "조회수 수정 완료되었습니다.";
        }
        console.log(msg);
      });

      // http.get(`/map/detail`, { params }).then(({ data }) => {
      //   let msg = "수정 처리 시 문제가 발생했습니다.";
      //   console.log("data: ", data);
      //   if (data === "fail") {
      //     msg = "문제가 발생했습니다.";
      //   }
      //   if (data === "success") {
      //     msg = "조회수 수정 완료되었습니다.";
      //   }
      //   console.log(msg);
      // });

      this.$router.push({
        name: "houseDetail",
      });
    },

    // 검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, places) {
      console.log("getListItem");
      var el = document.createElement("li");
      var itemStr = `
        <span class="markerbg marker_${index + 1}"></span>
        <div class="info">
          <h5>${places.apartmentName}</h5>
          <span class="price">${places.recentPrice}만원</span>
      `;

      if (places.road_address_name) {
        itemStr += `
          <span>${places.road_address_name}</span>
          <span class="jibun gray">${places.address_name}</span>
        `;
      } else {
        itemStr += `
          <span class="jibun gray">${places.sidoName} ${places.gugunName} ${places.dong} ${places.jibun}</span>
        `;
      }

      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, idx) {
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
    displayPagination(pagination) {
      var paginationEl = document.getElementById("pagination"),
        fragment = document.createDocumentFragment(),
        i;

      // 기존에 추가된 페이지번호를 삭제합니다
      while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
      }

      for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.onclick = (function (i) {
            return function () {
              pagination.gotoPage(i);
            };
          })(i);
        }

        fragment.appendChild(el);
      }
      paginationEl.appendChild(fragment);
    },
    // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
    // 인포윈도우에 장소명을 표시합니다
    displayInfowindow(marker, title, price) {
      var content = `
        <div style="padding:5px;z-index:1;">${title}</div>
        <div style="padding:5px;z-index:1;">${price}만원</div>
      `;
      // var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

      this.infowindow.setContent(content);
      this.infowindow.open(this.map, marker);
    },
    // 검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },
  },
};
</script>

<style>
.searchbar .custom-select {
  height: 20px;
  width: 95%;
}
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
  height: 100%;
  margin-top: 20px;
}
#apt_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 250px;
  margin: 10px 0 30px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
}
.bg_white {
  background: #fff;
}
#apt_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#apt_wrap .option {
  text-align: center;
}
#apt_wrap .option p {
  margin: 10px 0;
}
#apt_wrap .option button {
  margin-left: 5px;
}
#aptList li {
  list-style: none;
}
#aptList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
}
#aptList .item span {
  display: block;
  margin-top: 4px;
}
#aptList .item h5,
#aptList .item .info {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
#aptList .item .info {
  padding: 10px 0 10px 55px;
}
#aptList .info .gray {
  color: #8a8a8a;
}
#aptList .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;
}
#aptList .info .price {
  color: #009900;
}
#aptList .item .markerbg {
  float: left;
  position: absolute;
  width: 36px;
  height: 37px;
  margin: 10px 0 0 10px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;
}
#aptList .item .marker_1 {
  background-position: 0 -10px;
}
#aptList .item .marker_2 {
  background-position: 0 -56px;
}
#aptList .item .marker_3 {
  background-position: 0 -102px;
}
#aptList .item .marker_4 {
  background-position: 0 -148px;
}
#aptList .item .marker_5 {
  background-position: 0 -194px;
}
#aptList .item .marker_6 {
  background-position: 0 -240px;
}
#aptList .item .marker_7 {
  background-position: 0 -286px;
}
#aptList .item .marker_8 {
  background-position: 0 -332px;
}
#aptList .item .marker_9 {
  background-position: 0 -378px;
}
#aptList .item .marker_10 {
  background-position: 0 -423px;
}
#aptList .item .marker_11 {
  background-position: 0 -470px;
}
#aptList .item .marker_12 {
  background-position: 0 -516px;
}
#aptList .item .marker_13 {
  background-position: 0 -562px;
}
#aptList .item .marker_14 {
  background-position: 0 -608px;
}
#aptList .item .marker_15 {
  background-position: 0 -654px;
}
#pagination {
  margin: 10px auto;
  text-align: center;
}
#pagination a {
  display: inline-block;
  margin-right: 10px;
}
#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}
</style>
