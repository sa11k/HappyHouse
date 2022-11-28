<template>
  <Modal v-if="showModalUserInfo" @close="showModalChange">
    <h3 slot="header" class="mb-0">회원정보</h3>
    <div slot="body">
      <div v-if="userInfo">
        <div class="form-group container">
          <div class="row">
            <div class="col-4" style="padding: 0; text-align: left">
              <label class="label-id" for="input-live-id">아이디</label>
            </div>
            <div class="col" style="padding: 0">
              <div class="row">
                <div class="col-9" style="padding-right: 0; width: 233px">
                  <b-form-input id="input-liveid-id" v-model="userInfo.id" placeholder="ID" trim @keyup.enter="confirm" readonly></b-form-input>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="!pwYes" style="margin-top: -20px">
          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left">
                <label class="label-id" for="input-live-passwordconfirm">현재 비밀번호</label>
              </div>
              <div class="col" style="padding: 0; text-align: right">
                <b-form-input
                  id="input-liveid-passwordconfirm"
                  type="password"
                  v-model="pw"
                  :state="nowpassState"
                  aria-describedby="input-live-help-passwordconfirm input-live-feedback-passwordconfirm"
                  placeholder="Password"
                  trim
                  @keyup.enter="confirm"
                ></b-form-input>
                <b-form-invalid-feedback id="input-live-feedback-passwordconfirm"> 현재 비밀번호를 다시 확인해주세요. </b-form-invalid-feedback>
              </div>
            </div>
          </div>
        </div>
        <div v-if="pwYes" style="margin-top: -20px">
          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left">
                <label class="label-id" for="input-live-passwordconfirm">현재 비밀번호</label>
              </div>
              <div class="col" style="padding: 0; text-align: right">
                <b-form-input id="input-liveid-passwordconfirm" type="password" v-model="pw" placeholder="Password" trim readonly @keyup.enter="confirm"></b-form-input>
              </div>
            </div>
          </div>
          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left">
                <label class="label-id" for="input-live-password">비밀번호</label>
              </div>
              <div class="col" style="padding: 0">
                <b-form-input
                  id="input-liveid-password"
                  type="password"
                  v-model="newpw"
                  :state="passwordState"
                  aria-describedby="input-live-help-password input-live-feedback-password"
                  placeholder="Password"
                  trim
                  @keyup.enter="confirm"
                ></b-form-input>
                <b-form-invalid-feedback id="input-live-feedback-password"> Enter at least 4 letters </b-form-invalid-feedback>
              </div>
            </div>
          </div>

          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left">
                <label class="label-id" for="input-live-name">이름</label>
              </div>
              <div class="col" style="padding: 0">
                <b-form-input
                  id="input-liveid-name"
                  v-model="userInfo.name"
                  :state="nameState"
                  aria-describedby="input-live-help-id input-live-feedback-name"
                  placeholder="이름"
                  trim
                  @keyup.enter="confirm"
                ></b-form-input>
                <b-form-invalid-feedback id="input-live-feedback-name"> Enter at least 2 letters </b-form-invalid-feedback>
              </div>
            </div>
          </div>

          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left">
                <label class="label-id" for="input-live-email">이메일</label>
              </div>
              <div class="col" style="padding: 0">
                <b-form-input id="input-liveid-email" type="email" v-model="userInfo.email" placeholder="Email" trim @keyup.enter="confirm"></b-form-input>
              </div>
            </div>
          </div>
          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left; margin-bottom: 0px">
                <label class="label-id" for="input-live-address">주소</label>
              </div>
              <div class="col" style="padding: 0">
                <b-form-input id="input-liveid-address" v-model="userInfo.address" placeholder="주소" trim @keyup.enter="confirm"></b-form-input>
              </div>
            </div>
          </div>
          <div class="form-group container-fluid">
            <div class="row">
              <div class="col-4" style="padding: 0; text-align: left; margin-bottom: 0px">
                <label class="label-id" for="input-live-phone">전화번호</label>
              </div>
              <div class="col" style="padding: 0">
                <b-form-input id="input-liveid-phone" v-model="userInfo.phone" placeholder="전화번호(숫자만 입력" trim @keyup.enter="confirm"></b-form-input>
              </div>
            </div>
          </div>

          <div class="mt-2" style="text-align: right">
            <img src="@/assets/btn/refresh.png" @click="onSubmit" style="width: 35px; height: 35px; margin-right: 230px; cursor: pointer; opacity: 70%" />
            <img src="@/assets/btn/trash-bin.png" @click="onDelete" style="width: 40px; height: 40px; cursor: pointer; opacity: 100%" />
          </div>
        </div>
      </div>
    </div>
  </Modal>
</template>

<script>
import http from "@/api/http";
import Modal from "@/components/user/MemberModal.vue";
import swal from "sweetalert";
import { mapState, mapActions, mapMutations } from "vuex";
export default {
  name: "UserInfoModal",
  components: {
    Modal,
  },
  data() {
    return {
      pw: "",
      newpw: "",
      pwYes: false,
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin", "isLoginError", "showModalUserInfo", "userInfo", "isCheckId"]),
    nowpassState() {
      if (this.pw.length == 0) return null;
      if (this.userInfo.pw == this.pw) {
        this.updateState();
        return true;
      } else return false;
    },
    passwordState() {
      if (this.newpw.length == 0) return null;
      return this.newpw.length >= 4 ? true : false;
    },
    nameState() {
      if (this.userInfo.name.length == 0) return null;
      return this.userInfo.name.length >= 2 ? true : false;
    },
  },
  methods: {
    ...mapMutations("memberStore", ["SET_SHOW_MODAL_USERINFO", "SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo", "idCheck"]),
    updateState() {
      this.pwYes = !this.pwYes;
    },
    onDelete() {
      swal({
        title: "정말로 삭제할까요?",
        text: "",
        icon: "info",
        buttons: ["NO", "YES"],
      }).then((YES) => {
        if (YES) {
          http.delete(`/user/${this.userInfo.id}`).then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "삭제가 완료되었습니다.";
            }
            swal(msg);
            this.SET_SHOW_MODAL_USERINFO();
            this.SET_IS_LOGIN(false);
            this.SET_USER_INFO(null);
            sessionStorage.removeItem("access-token");
            if (this.$route.path != "/") this.$router.push({ name: "home" });
          });
        }
      });
    },
    onSubmit(event) {
      event.preventDefault();
      this.pwYes = false;
      this.updateUser();
    },
    showModalChange() {
      this.pwYes = false;
      this.pw = "";
      this.SET_SHOW_MODAL_USERINFO();
    },
    updateUser() {
      if (this.userInfo.id === "" || this.newpw === "" || this.userInfo.name === "" || this.userInfo.email === "") {
        swal("모든 내용을 입력해주세요");
        return;
      }
      this.userInfo.pw = this.newpw;
      const user = { id: this.userInfo.id, pw: this.newpw, name: this.userInfo.name, email: this.userInfo.email, address: this.userInfo.address };
      http.put(`/user/update`, null, { params: user }).then(({ data }) => {
        let msg = "수정 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "수정이 완료되었습니다.";
        }
        swal(msg);
        this.SET_SHOW_MODAL_USERINFO();
      });
    },
  },
};
</script>

<style>
.registbtn {
  margin-left: 10px;
}
.regist-form {
  /* border: 1px grey solid; */
  border-radius: 10px;
  border-style: outset;
  padding-top: 50px;
  padding-right: 70px;
  padding-left: 140px;
  margin-bottom: 100px;
  margin-top: 70px;
}
.form-group {
  margin-top: 50px;
  margin-bottom: 50px;
  display: inline;
  width: 100%;
}
.label-id {
  display: inline;
  height: 38px;
  line-height: 38px;
}
</style>
