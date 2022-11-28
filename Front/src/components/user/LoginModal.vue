<template>
  <Modal v-if="showModal" @close="showModalChange">
    <h3 slot="header" class="mb-0">로그인</h3>
    <div slot="body">
      <div class="form-group container">
        <div class="row">
          <div class="col-3" style="padding: 0; text-align: left">
            <label class="label-id" for="input-live-id">아이디</label>
          </div>
          <div class="col" style="padding-right: 10px">
            <b-form-input id="input-liveid-id" v-model="user.id" placeholder="ID" trim @keyup.enter="confirm"></b-form-input>
          </div>
        </div>
      </div>

      <div class="form-group container-fluid">
        <div class="row">
          <div class="col-3" style="padding: 0; text-align: left">
            <label class="label-id" for="input-live-password">비밀번호</label>
          </div>
          <div class="col" style="padding-right: 10px">
            <b-form-input id="input-liveid-password" type="password" v-model="user.pw" placeholder="Password" trim @keyup.enter="confirm"></b-form-input>
          </div>
        </div>
      </div>

      <div align="right">
        <button type="button" class="btn btn-outline-success" @click="confirm">Login</button>
        <!-- <img src="@/assets/btn/loginbtn.jpg" @click="confirm" style="width: 80px; height: 50px; opacity: 100%; cursor: pointer; margin-right: 10px" /> -->
      </div>
      <!-- <b-form>
        
        <b-form-group class="mb-3 text-left" label="아이디" label-for="userid">
          <b-form-input id="userid" v-model="user.id" required placeholder="아이디 입력...." @keyup.enter="confirm"></b-form-input>
        </b-form-group>
        <b-form-group class="mb-3 text-left" label="비밀번호" label-for="userpwd">
          <b-form-input type="password" id="userpwd" v-model="user.pw" required placeholder="비밀번호 입력...." @keyup.enter="confirm"></b-form-input>
        </b-form-group>
        
      </b-form> -->
    </div>
  </Modal>
</template>

<script>
import Modal from "@/components/user/MemberModal.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import swal from "sweetalert";
export default {
  components: {
    Modal,
  },
  data() {
    return {
      user: {
        id: null,
        pw: null,
      },
    };
  },
  computed: {
    ...mapState("memberStore", ["isLogin", "isLoginError", "showModal"]),
  },
  methods: {
    ...mapMutations("memberStore", ["SET_SHOW_MODAL_LOGIN"]),
    ...mapActions("memberStore", ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.SET_SHOW_MODAL_LOGIN();
        swal("로그인 성공!", this.user.id + "님 로그인되었습니다.", "success");
      } else {
        swal("로그인 실패!", "아이디 또는 비밀번호를 확인하세요.", "error");
      }
    },
    showModalChange() {
      this.user.id = "";
      this.user.pw = "";
      this.SET_SHOW_MODAL_LOGIN();
    },
  },
};
</script>

<style>
.d-block {
  margin-bottom: 5px;
}
</style>
