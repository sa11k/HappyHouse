<template>
  <div class="content">
    <div class="rounded-3 bg-white mb-2 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col-sm-12 mt-4 mb-0 text-center">
          <label class="h2">관심지역 둘러보기</label>
        </div>
      </div>
      <b-container class="bv-example-row" style="border-top: 1px grey solid; margin-top: 25px">
        <b-row style="margin-bottom: 10px; margin-top: 10px">
          <b-col class="box" cols="5" style="margin-top: 19px">
            <interest-register-view></interest-register-view>
            <div class="mb-3 mt-3 row">
              <div class="text-center">
                <router-view></router-view>
              </div>
            </div>
          </b-col>
          <b-col class="boxMap" cols="7">
            <interest-map-view class="boxMap"></interest-map-view>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import InterestRegisterView from "@/components/interest/InterestRegister.vue";
import InterestMapView from "@/components/interest/InterestMap.vue";

export default {
  name: "InterestView",
  components: {
    InterestRegisterView,
    InterestMapView,
  },
};
</script>

<style>
.addInterest {
  display: inline-block;
}
.box {
  height: 450px;
  margin-top: 30px;
}
.boxMap {
  height: 450px;
}
.mapWrap {
  height: 450px !important;
}
th > div {
  text-align: center;
}

tr > td {
  text-align: center;
}
</style>
