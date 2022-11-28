<template>
  <div align="center">
    <div clas="mt-3 addInterest">
      <house-search-bar class="bar"></house-search-bar>
      <img src="@/assets/btn/like.png" @click="addInterest" class="addbtn" style="width: 30px; height: 30px; cursor: pointer" />
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import { mapState } from "vuex";
import swal from "sweetalert";
export default {
  components: {
    HouseSearchBar,
  },
  computed: {
    ...mapState("houseStore", ["dong", "houses"]),
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    addInterest() {
      console.log(this.userInfo.id + this.dong);
      http.post(`/interest/regist/${this.userInfo.id}/${this.dong}`).then(({ data }) => {
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "등록이 완료되었습니다.";
        }
        swal(msg);
        this.$router.go();
      });
    },
  },
};
</script>

<style>
.bar {
  display: inline-block;
  width: 400px;
}
.addbtn {
  display: inline-block;
}
</style>
