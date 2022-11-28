<template>
  <b-container class="bv-example-row mt-1">
    <b-row>
      <b-col align="center">
        <div class="noticecontent">
          <div class="title">
            <hr />
            <div class="headerTitle" style="text-weight: bold; height: 50px">
              <h4 style="height: 50px; line-height: 50px; font-size: 30px">{{ notice.title }}</h4>
            </div>
            <hr />
            <div style="text-align: right">
              <p style="font-size: 15px; margin-right: 10px">조회수 : {{ notice.hit }} | 작성자 : {{ notice.user_id }}</p>
            </div>
          </div>
          <hr />
          <div class="message" style="font-size: 20px">{{ message }}</div>
          <hr style="height: 0.5px" />
        </div>
      </b-col>
    </b-row>

    <b-container style="width: 83%">
      <b-row align="center">
        <b-col class="text-right" style="text-align: left; margin-right: 10px" v-if="userInfo.id == 'admin'">
          <img src="@/assets/btn/tools.png" style="cursor: pointer; width: 30px; height: 30px" @click="moveModifyNotice" />
          <img src="@/assets/btn/bin.png" style="margin-left: 5px; cursor: pointer; width: 30px; height: 30px" @click="deleteNotice" />
        </b-col>
        <b-col class="text-right" style="text-align: right">
          <img src="@/assets/btn/menu.png" @click="listNotice" style="cursor: pointer; width: 30px; height: 30px" />
        </b-col>
      </b-row>
    </b-container>

    <div></div>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/api/http";
import swal from "sweetalert";
import { mapState } from "vuex";

export default {
  name: "NoticeDetail",
  data() {
    return {
      notice: {},
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
    message() {
      if (this.notice.content) return this.notice.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/notice/detail/${this.$route.params.no}`).then(({ data }) => {
      this.notice = data;
    });
  },
  methods: {
    listNotice() {
      this.$router.push({ name: "noticeList" });
    },
    moveModifyNotice() {
      this.$router.replace({
        name: "noticeModify",
        params: { no: this.notice.no },
      });
    },
    deleteNotice() {
      swal({
        title: "정말로 삭제할까요?",
        text: "",
        icon: "info",
        buttons: ["NO", "YES"],
      }).then((YES) => {
        if (YES) {
          http.delete(`/notice/${this.$route.params.no}`).then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "삭제가 완료되었습니다.";
            }
            swal(msg);
            // 현재 route를 /list로 변경.
            this.$router.push({ name: "noticeList" });
          });
        }
      });
    },
  },
};
</script>

<style>
.noticecontent {
  width: 80%;
  height: 450px;
}
.message {
  height: 250px;
  line-height: 250px;
}
</style>
