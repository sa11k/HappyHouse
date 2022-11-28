<template>
  <div>
    <b-container class="bv-example-row mt-2 containnotice" style="border-top: 1px grey solid">
      <b-row style="margin-bottom: 5px; margin-top: 15px">
        <b-col style="text-align: center">
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
            :items="boards"
            :fields="fields"
            :head-variant="headVariant"
            :table-variant="tableVariant"
            @row-clicked="viewBoard"
            style="cursor: pointer"
          >
            <template #cell(subject)="data">
              <router-link
                :to="{
                  name: 'boardDetail',
                  params: { no: data.item.no },
                }"
                >{{ data.item.title }}</router-link
              >
            </template>
          </b-table>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-right mb-3" style="text-align: right">
          <img src="@/assets/btn/edit2.png" @click="moveWrite()" style="cursor: pointer; width: 30px; height: 32px" />
        </b-col>
      </b-row>
      <b-pagination v-model="currentPage" pills :total-rows="boardCnt" :per-page="10" align="center" @page-click="pageBoard"></b-pagination>
    </b-container>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "BoardList",
  data() {
    return {
      boards: [],
      boardCnt: 0,
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
    http.get(`/board/index`).then(({ data }) => {
      this.boards = data.boards;
      this.boardCnt = data.cnt;
      this.currentPage = data.nowpage;
    });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "boardRegister" });
    },
    viewBoard(board) {
      this.$router.push({
        name: "boardDetail",
        params: { no: board.no },
      });
    },
    pageBoard(button, page) {
      this.currentPage = page;
      http.get(`/board/movepage/${this.currentPage}`).then(({ data }) => {
        this.boards = data.boards;
        this.boardCnt = data.cnt;
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
