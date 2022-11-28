<template>
  <b-container class="bv-example-row mt-2 containnotice" style="border-top: 1px grey solid; margin-top: 25px">
    <b-row style="margin-bottom: 20px; margin-top: 15px">
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
          :items="notices"
          :fields="fields"
          :head-variant="headVariant"
          :table-variant="tableVariant"
          @row-clicked="viewNotice"
          style="cursor: pointer"
        >
          <template #cell(subject)="data">
            <router-link
              :to="{
                name: 'noticeDetail',
                params: { no: data.item.no },
              }"
              >{{ data.item.title }}</router-link
            >
          </template>
        </b-table>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right mb-3" style="text-align: right" v-if="userInfo.id == 'admin'">
        <img src="@/assets/btn/edit2.png" @click="moveWrite()" style="cursor: pointer; width: 30px; height: 32px" />
      </b-col>
    </b-row>
    <b-pagination v-model="currentPage" pills :total-rows="noticeCnt" :per-page="10" align="center" @page-click="pageNotice"></b-pagination>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "NoticeList",
  data() {
    return {
      notices: [],
      noticeCnt: 0,
      fields: [
        { key: "no", label: "글번호", tdClass: "tdClass" },
        { key: "title", label: "제목", tdClass: "tdTitle" },
        { key: "user_name", label: "작성자", tdClass: "tdClass" },
        { key: "date", label: "작성일", tdClass: "tdDate" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
      currentPage: 1,
    };
  },
  created() {
    http.get(`/notice/index`).then(({ data }) => {
      this.notices = data.notices;
      this.noticeCnt = data.cnt;
      this.currentPage = data.nowpage;
    });
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    ...mapMutations("memberStore", ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo"]),
    moveWrite() {
      if (this.userInfo.id == "admin") this.$router.push({ name: "noticeRegister" });
    },
    viewNotice(notice) {
      this.$router.push({
        name: "noticeDetail",
        params: { no: notice.no },
      });
    },
    pageNotice(button, page) {
      this.currentPage = page;
      http.get(`/notice/movepage/${this.currentPage}`).then(({ data }) => {
        this.notices = data.notices;
        this.noticeCnt = data.cnt;
        this.currentPage = data.nowpage;
      });
    },
  },
};
</script>

<style scope>
.containnotice {
  width: 75%;
}
.tdClass {
  width: 100px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
.tdDate {
  width: 200px;
  text-align: center;
}
.page-item.active .page-link {
  background-color: rgb(172, 172, 172) !important;
  border-color: rgb(151, 151, 151) !important;
  color: white;
}

.page-item .page-link {
  color: rgb(58, 58, 58);
}
th > div {
  text-align: center;
}

tr > td {
  text-align: center;
}
</style>
