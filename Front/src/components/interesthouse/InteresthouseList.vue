<template>
  <b-container class="bv-example-row mt-3">
    <div>
      <div v-if="houseCnt">
        <ul style="text-align: center; padding: 0">
          <li class="houses" v-for="(item, index) in pageHouseList()" :key="index">
            <b-card :title="item.apartmentName" tag="article" style="max-width: 90%; width: 85%; height: 330px; border-radius: 15px" class="mb-2 house" align="center">
              <img src="@/assets/btn/heart.png" alt="" style="width: 20px; height: 20px; cursor: pointer" @click="deletehouse(item)" />
              <a style="text-decoration: none; color: black" @click="goDetail">
                <img :src="require(`../../assets/img/house/${(index % 10) + 1}.jpg`)" alt="" class="card-img" style="height: 130px; width: 100%; margin-top: 15px; margin-bottom: 25px" />
                <p style="height: 20px; font-weight: bold">{{ item.sidoName }} {{ item.gugunName }} {{ item.dongName }} <br /><br /></p>
                <p style="height: 20px">{{ item.dealAmount }} (단위 : 만원)<br /><br /></p>
              </a>
            </b-card>
          </li>
        </ul>
      </div>
      <div v-else style="text-align: center; margin-bottom: 200px; margin-top: 200px">등록된 관심 매물이 없습니다.</div>
      <b-pagination v-model="currentPage" pills :total-rows="houseCnt" :per-page="perPage" align="center"></b-pagination>
    </div>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
import swal from "sweetalert";
export default {
  name: "InterestList",
  data() {
    return {
      perPage: 8,
      currentPage: 1,
    };
  },

  computed: {
    ...mapState("memberStore", ["userInfo"]),
    ...mapState("houseInterestStore", ["houses"]),
    ...mapGetters("houseInterestStore", ["houseCnt"]),
  },
  created() {
    this.sethouselist(this.userInfo.id);
  },
  methods: {
    ...mapMutations("memberStore", ["SET_SHOW_MODAL_USERINFO", "SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions("houseInterestStore", ["sethouselist"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo"]),
    deletehouse(item) {
      console.log(item.no);
      swal({
        title: "정말로 삭제할까요?",
        text: "",
        icon: "info",
        buttons: ["NO", "YES"],
      }).then((YES) => {
        if (YES) {
          http.delete(`/interesthouse/${item.no}`).then(({ data }) => {
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
    goDetail() {},
    pageHouseList() {
      return this.houses.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
    },
  },
};
</script>

<style scope>
.houses {
  width: 23%;
  margin: 0;
  display: inline-block;
}
.house {
  margin-bottom: 30px !important;
}
.house:hover {
  z-index: 999;
  transform: scale(1.2);
}
.page-item.active .page-link {
  /* background-color: rgb(214, 214, 214) !important; */
  background-color: rgb(172, 172, 172) !important;
  border-color: rgb(151, 151, 151) !important;
  color: white;
}

.page-item .page-link {
  color: rgb(70, 69, 69);
}
</style>
