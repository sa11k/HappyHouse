<template>
  <b-row class="mb-1">
    <b-col style="text-align: left; margin-left: 10%; margin-right: 10%">
      <b-form>
        <hr />
        <b-row style="margin-bottom: 10px">
          <b-col cols="8" style="padding 0">
            <b-row>
              <b-col style="text-align: center; padding: 0">
                <label class="label-title" for="title">제목</label>
              </b-col>
              <b-col cols="11" style="padding: 0">
                <b-form-input id="title" v-model="notice.title" type="text" required placeholder="제목 입력..."> </b-form-input>
              </b-col>
            </b-row>
          </b-col>

          <b-col>
            <b-row>
              <b-col style="text-align: center; padding: 0; margin-left: 10px">
                <label class="label-user_id" for="user_id">작성자</label>
              </b-col>
              <b-col cols="9" style="padding: 0">
                <b-form-input id="user_id" :disabled="isUserid" v-model="notice.user_id" type="text" readonly placeholder="admin" style="width: 95%"></b-form-input>
              </b-col>
            </b-row>
          </b-col>
        </b-row>

        <hr />

        <b-form-group id="content-group" label="내용" label-for="content">
          <b-form-textarea id="content" v-model="notice.content" placeholder="내용 입력..." rows="10" max-rows="15" style="width: 98%; margin-left: 10px; margin-bottom: 20px"></b-form-textarea>
        </b-form-group>
        <hr />
        <b-row>
          <b-col class="text-right" style="text-align: left">
            <img src="@/assets/btn/menu.png" @click="listNotice" style="cursor: pointer; width: 30px; height: 30px" />
          </b-col>
          <b-col align="right">
            <img v-if="this.type === 'register'" @click="onSubmit" src="@/assets/btn/upload.png" style="cursor: pointer; width: 38px; height: 38px" />
            <img v-else @click="onSubmit" src="@/assets/btn/upload.png" style="cursor: pointer; width: 38px; height: 38px" />
            <img @click="onReset" src="@/assets/btn/revision.png" style="margin-left: 5px; cursor: pointer; width: 35px; height: 35px" />
          </b-col>
        </b-row>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
import swal from "sweetalert";

export default {
  name: "NoticeInputItem",
  data() {
    return {
      notice: {
        no: 0,
        user_id: "",
        title: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      http.get(`/notice/updateform/${this.$route.params.no}`).then(({ data }) => {
        this.notice = data;
      });
      this.isUserid = true;
    }
  },
  methods: {
    listNotice() {
      this.$router.push({ name: "noticeList" });
    },
    onSubmit() {
      let err = true;
      let msg = "";
      !this.notice.title && ((msg = "제목 입력해주세요"), (err = false));
      err && !this.notice.content && ((msg = "내용 입력해주세요"), (err = false));

      if (!err) swal(msg);
      else this.type === "register" ? this.registNotice() : this.modifyNotice();
    },
    onReset() {
      this.notice.title = "";
      this.notice.content = "";
    },
    registNotice() {
      const notice = { title: this.notice.title, content: this.notice.content, user_id: "admin" };
      http.post(`/notice/register`, null, { params: notice }).then(({ data }) => {
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "등록이 완료되었습니다.";
        }
        swal(msg);
        this.moveList();
      });
    },
    modifyNotice() {
      http
        .put(`/notice/update`, null, {
          params: {
            no: this.notice.no,
            title: this.notice.title,
            content: this.notice.content,
          },
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          swal(msg);
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({ name: "noticeList" });
    },
  },
};
</script>

<style>
.label-title,
.label-user_id {
  height: 38px;
  line-height: 38px;
}
#content-group label {
  margin-left: 10px;
}
</style>
