<template>
  <div class="content">
    <div class="rounded-3 mb-2 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col mt-4 mb-0 text-center">
          <label class="h2">자유게시판</label>
        </div>
      </div>
      <div class="mb-3 mt-3 row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8 text-center">
          <router-view></router-view>
        </div>
        <div class="col-sm-2"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BoardView",
};
</script>

<style></style>
