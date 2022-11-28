<template>
  <div class="content">
    <div class="rounded-3 bg-white mb-2 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col-sm-12 mt-4 mb-0 text-center">
          <label class="h2">전체 사용자 관리</label>
        </div>
      </div>
      <b-container class="bv-example-row">
        <user-list></user-list>
      </b-container>
    </div>
  </div>
</template>

<script>
import UserList from "@/components/user/UserList.vue";
export default {
  name: "ListUserView",
  components: {
    UserList,
  },
};
</script>

<style></style>
