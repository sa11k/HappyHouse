<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-table
          :striped="striped"
          :bordered="bordered"
          :borderless="borderless"
          :outlined="outlined"
          :small="small"
          hover
          :dark="dark"
          :fixed="fixed"
          :foot-clone="footClone"
          :no-border-collapse="noCollapse"
          :head-variant="headVariant"
          :table-variant="tableVariant"
          :items="interests"
          :fields="fields"
        >
          <template #cell(delete)="data">
            <img src="@/assets/btn/heart.png" @click="deleteInterest(data.item.no)" style="width: 25px; height: 25px; cursor: pointer" />
          </template>
          <template #cell(go)="data"> <img src="@/assets/btn/house.png" class="deletebtn" @click="goList(data)" style="width: 25px; height: 25px; cursor: pointer" /> </template>
        </b-table>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState, mapActions, mapMutations } from "vuex";
import swal from "sweetalert";

export default {
  name: "InterestList",
  data() {
    return {
      interests: [],
      fields: [
        { key: "sidoName", label: "시/도", tdClass: "tdClass" },
        { key: "gugunName", label: "구/군", tdClass: "tdClass1" },
        { key: "dongName", label: "동", tdClass: "tdClass1" },
        { key: "delete", label: "삭제", tdClass: "tdbtn" },
        { key: "go", label: "매물", tdClass: "tdbtn" },
      ],
    };
  },
  created() {
    http.get(`/interest/interestform/${this.userInfo.id}`).then(({ data }) => {
      this.interests = data.interestlist;
    });
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    ...mapMutations("memberStore", ["SET_SHOW_MODAL_USERINFO", "SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapMutations("houseStore", ["SET_SIDO_CODE", "SET_GUGUN_CODE", "SET_DONG_CODE", "CLEAR_SI_GUN_DONG"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo"]),
    ...mapActions("houseStore", ["getHouseList"]),
    deleteInterest(no) {
      swal({
        title: "정말로 삭제할까요?",
        text: "",
        icon: "info",
        buttons: ["NO", "YES"],
      }).then((YES) => {
        if (YES) {
          http.delete(`/interest/${no}`).then(({ data }) => {
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
    goList(data) {
      console.log(data);
      this.SET_SIDO_CODE(data.item.dongCode.substr(0, 2));
      this.SET_GUGUN_CODE(data.item.dongCode.substr(0, 5));
      this.SET_DONG_CODE(data.item.dongCode);
      this.CLEAR_SI_GUN_DONG();
      this.getHouseList(data.item.dongCode);
      this.$router.push({ name: "houseList" });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 15%;
  line-height: 35px;
  text-align: center;
}
.tdClass1 {
  width: 15%;
  line-height: 35px;
  text-align: center;
  padding: 0;
}
.tdbtn {
  width: 5%;
  line-height: 35px;
  text-align: center;
  padding: 0;
}
.deletebtn {
  width: 50px;
  height: 35px;
  font-size: 13px;
  font-weight: bold;
}
</style>
