<template>
  <div class="container" style="text-align: left">
    <a @click="moveDetail" style="cursor: pointer">{{ notice.title }}</a>
    <div style="text-align: right">
      <span>{{ notice.date }}</span>
    </div>
  </div>
</template>

<script>
import swal from "sweetalert";
import { mapState } from "vuex";

export default {
  name: "MainNoticeItem",
  props: {
    notice: Object,
  },
  computed: {
    ...mapState("memberStore", ["isLogin"]),
  },
  methods: {
    moveDetail() {
      if (!this.isLogin) {
        swal("로그인 후 이용 가능합니다.");
      }
      this.$router.push({ name: "noticeDetail", params: { no: this.notice.no } });
    },
  },
};
</script>

<style scoped>
a {
  /* text-decoration: none; */
  color: black;
}
span {
  font-size: 12px;
  color: grey;
}
.container {
  border-bottom: 1px lightgrey solid;
  width: 477.53px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
