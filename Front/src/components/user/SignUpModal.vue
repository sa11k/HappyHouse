<template>
  <Modal v-if="showModalSignup" @close="showModalChange">
    <h3 slot="header" class="mb-0">회원가입</h3>
    <div slot="body">
      <div class="form-group container">
        <div class="row">
          <div class="col-3" style="padding: 0; text-align: left">
            <label class="label-id" for="input-live-id">아이디</label>
          </div>
          <div class="col" style="padding: 0">
            <div class="row">
              <div class="col-9" style="padding-right: 0; width: 200px">
                <b-form-input
                  id="input-liveid-id"
                  v-model="user.id"
                  :state="idState"
                  aria-describedby="input-live-help-id input-live-feedback-id"
                  placeholder="ID"
                  trim
                  @keyup.enter="confirm"
                ></b-form-input>
                <b-form-invalid-feedback id="input-live-feedback-id"> Enter at least 4 letters </b-form-invalid-feedback>
              </div>
              <div class="col" style="padding: 0">
                <img src="@/assets/btn/verified.png" @click="userIdCheck" style="width: 38px; height: 38px; margin-top: 2px; margin-left: 20px; opacity: 75%; cursor: pointer" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="form-group container-fluid">
        <div class="row">
          <div class="col-3" style="padding: 0; text-align: left">
            <label class="label-id" for="input-live-password">비밀번호</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-input
              id="input-liveid-password"
              type="password"
              v-model="user.pw"
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
          <div class="col-3" style="padding: 0; text-align: left">
            <label class="label-id" for="input-live-name">이름</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-input
              id="input-liveid-name"
              v-model="user.name"
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
          <div class="col-3" style="padding: 0; text-align: left">
            <label class="label-id" for="input-live-email">이메일</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-input id="input-liveid-email" type="email" v-model="user.email" placeholder="Email" trim @keyup.enter="confirm"></b-form-input>
          </div>
        </div>
      </div>
      <div class="form-group container-fluid">
        <div class="row">
          <div class="col-3" style="padding: 0; text-align: left; margin-bottom: 0px">
            <label class="label-id" for="input-live-address">주소</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-input id="input-liveid-address" v-model="user.address" placeholder="주소" trim @keyup.enter="confirm"></b-form-input>
          </div>
        </div>
      </div>
      <div class="form-group container-fluid">
        <div class="row">
          <div class="col-3" style="padding: 0; text-align: left; margin-bottom: 0px">
            <label class="label-id" for="input-live-phone">전화번호</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-input id="input-liveid-phone" v-model="user.phone" placeholder="전화번호(숫자만 입력)" trim @keyup.enter="confirm"></b-form-input>
          </div>
        </div>
      </div>
      <div class="form-group container-fluid">
        <div class="row">
          <div class="col-9" style="padding: 0; text-align: left; margin-bottom: 0px">
            <label class="label-id" for="input-live-address">sms 수신 동의</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-checkbox style="height: 38px; line-height: 38px" size="lg" v-model="smschecked" :indeterminate.sync="indeterminate"> </b-form-checkbox>
          </div>
        </div>
      </div>
      <div class="form-group container-fluid">
        <div class="row">
          <div class="col-9" style="padding: 0; text-align: left; margin-bottom: 0px">
            <label class="label-id" for="input-live-address">공인중개사 계정으로 등록</label>
          </div>
          <div class="col" style="padding: 0">
            <b-form-checkbox style="height: 38px; line-height: 38px" size="lg" v-model="checked" :indeterminate.sync="indeterminate"> </b-form-checkbox>
          </div>
        </div>
      </div>
      <div class="mt-2" style="text-align: center">
        <button type="button" class="btn btn-outline-success" @click="onSubmit">Submit</button>

        <!-- <img src="@/assets/btn/submit.jpg" @click="onSubmit" style="width: 110px; height: 50px; margin-top: 2px; margin-left: 20px; opacity: 100%; cursor: pointer" /> -->
      </div>
    </div>
  </Modal>
</template>

<script>
import http from "@/api/http";
import Modal from "@/components/user/MemberModal.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import swal from "sweetalert";
export default {
  name: "SignUpModal",
  components: {
    Modal,
  },
  data() {
    return {
      user: {
        id: "",
        pw: "",
        name: "",
        email: "",
        address: "",
        isAgent: "",
        sms: "",
        phone: "",
      },
      checked: false,
      smschecked: false,
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin", "isLoginError", "showModalSignup", "isCheckId"]),
    idState() {
      if (this.user.id.length == 0) return null;
      return this.user.id.length >= 4 ? true : false;
    },
    passwordState() {
      if (this.user.pw.length == 0) return null;
      return this.user.pw.length >= 4 ? true : false;
    },
    nameState() {
      if (this.user.name.length == 0) return null;
      return this.user.name.length >= 2 ? true : false;
    },
  },
  methods: {
    ...mapMutations("memberStore", ["SET_SHOW_MODAL_SIGNUP"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo", "idCheck"]),
    onSubmit(event) {
      event.preventDefault();
      this.registUser();
    },
    showModalChange() {
      this.user.id = "";
      this.user.pw = "";
      this.user.name = "";
      this.user.email = "";
      this.user.address = "";
      this.user.isAgent = "";
      this.user.sms = "";
      this.user.phone = "";
      this.SET_SHOW_MODAL_SIGNUP();
    },
    registUser() {
      if (!this.isCheckId) {
        swal("아이디 중복체크 해주세요");
        return;
      }
      if (this.user.id === "" || this.user.password === "" || this.user.name === "" || this.user.email === "") {
        swal("모든 내용을 입력해주세요");
        return;
      }
      if (this.checked) {
        this.user.isAgent = "-";
      } else {
        this.user.isAgent = "N";
      }
      if (this.smschecked) {
        this.user.sms = 1;
      } else {
        this.user.sms = 0;
      }
      const user = {
        id: this.user.id,
        pw: this.user.pw,
        name: this.user.name,
        email: this.user.email,
        address: this.user.address,
        isAgent: this.user.isAgent,
        sms: this.user.sms,
        phone: this.user.phone,
      };
      http.post(`/user/register`, null, { params: user }).then(({ data }) => {
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "등록이 완료되었습니다.";
        }
        swal(msg);
        this.checked = false;
        this.smschecked = false;
        this.SET_SHOW_MODAL_SIGNUP();
      });
    },
    async userIdCheck() {
      await this.idCheck(this.user.id);
      if (this.isCheckId) {
        swal("사용 가능한 아이디입니다.");
      } else {
        swal("이미 사용 중인 아이디입니다.");
      }
    },
  },
};
</script>

<style>
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
.custom-control-input {
  width: 20px;
  height: 20px;
  margin-top: 8px;
}
</style>
