<template>
  <b-container class="bv-example-row mt-4 containnotice">
    <b-row style="margin-bottom: 20px">
      <b-col>
        <b-table
          :striped="striped"
          :bordered="bordered"
          :borderless="borderless"
          :outlined="outlined"
          :api-url="small"
          hover
          :dark="dark"
          :fixed="fixed"
          :foot-clone="footClone"
          :no-border-collapse="noCollapse"
          :items="users"
          :fields="fields"
          :head-variant="headVariant"
          :table-variant="tableVariant"
          class="usertable"
        >
          <template #cell(isAgent)="data">
            <div v-if="data.item.isAgent == '-'">
              <img src="@/assets/btn/refresh.png" @click="acceptAgent(data.item)" style="width: 25px; height: 25px; cursor: pointer" />
            </div>
            <div v-else>
              <p style="margin: 0">{{ data.item.isAgent }}</p>
            </div>
          </template>
          <template #cell(delete)="data">
            <img src="@/assets/btn/bin.png" @click="deleteuser(data.item)" style="width: 25px; height: 25px; cursor: pointer" />
          </template>
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
  name: "UserList",
  data() {
    return {
      users: [],
      fields: [
        { key: "id", label: "아이디", tdClass: "tdClass" },
        { key: "name", label: "이름", tdClass: "tdClass" },
        { key: "email", label: "이메일", tdClass: "tdSubject" },
        { key: "address", label: "주소", tdClass: "tdSubject" },
        { key: "joinDate", label: "가입일", tdClass: "tdDate" },
        { key: "isAgent", label: "공인중개사", tdClass: "tdDate" },
        { key: "delete", label: "회원 삭제", tdClass: "tdDate" },
      ],
    };
  },
  created() {
    http.get(`/user/list`).then(({ data }) => {
      this.users = data.users;
    });
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  methods: {
    ...mapMutations("memberStore", ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo"]),
    acceptAgent(item) {
      item.isAgent = "Y";
      const user = { id: item.id, pw: item.pw, name: item.name, email: item.email, address: item.address, isAgent: item.isAgent };
      http.put(`/user/update`, null, { params: user }).then(({ data }) => {
        swal({
          title: "공인중개사로 등록하시겠습니까?",
          text: "",
          icon: "info",
          buttons: ["NO", "YES"],
        }).then((YES) => {
          if (YES) {
            let msg = "공인중개사 계정 전환 중 문제가 발생했습니다.";
            if (data === "success") {
              msg = "공인중개사 계정 전환이 완료되었습니다.";
            }
            swal(msg);
          }
          this.$router.go();
        });
      });
    },
    deleteuser(item) {
      swal({
        title: "정말로 삭제할까요?",
        text: "",
        icon: "info",
        buttons: ["NO", "YES"],
      }).then((YES) => {
        if (YES) {
          http.delete(`/user/${item.id}`).then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "삭제가 완료되었습니다.";
            }
            swal(msg);
            sessionStorage.removeItem("access-token");
          });
        }
        this.$router.go();
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
  width: 230px;
  text-align: left;
}
.tdDate {
  width: 80px;
  text-align: center;
}
.usertable thead tr th {
  text-align: center;
}
.usertable tbody tr td {
  text-align: center;
}
</style>
