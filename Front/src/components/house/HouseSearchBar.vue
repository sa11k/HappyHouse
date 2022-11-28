<template>
  <div class="dark-bj section" style="padding: 0">
    <div class="container-fluid">
      <!-- filters start -->
      <div class="sorting-filters sigundong text-center mb-20 d-flex justify-content-center">
        <div class="form-gruop md" style="float: left; width: 200px">
          <b-form-select class="selectBox" v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
        </div>
        <div class="form-gruop md" style="float: left; width: 200px">
          <b-form-select class="selectBox" v-model="gugunCode" :options="guguns" @change="dongList"></b-form-select>
        </div>
        <div class="form-gruop md" style="float: left; width: 200px">
          <b-form-select class="selectBox" v-model="dongCode" :options="dongs" @change="searchApt"></b-form-select>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses", "sido", "gugun", "dong"]),
  },

  created() {
    this.sidoCode = this.sido;
    this.gugunCode = this.gugun;
    this.dongCode = this.dong;

    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getDong", "getHouseList"]),
    ...mapMutations(houseStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "SET_SIDO_CODE", "SET_GUGUN_CODE", "SET_DONG_CODE"]),

    gugunList() {
      this.SET_SIDO_CODE(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      // console.log(this.gugunCode);
      this.SET_GUGUN_CODE(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.sidoCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      this.SET_DONG_CODE(this.dongCode);
      if (this.dongCode) this.getHouseList(this.dongCode);
    },
  },
};
</script>

<style>
.sigundong .custom-select {
  width: 80%;
  height: 40px;
  text-align: center;
}
</style>
