<template>
  <div class="content">
    <div class="rounded-3 mb-2 pb-3">
      <div class="mb-3 mt-3 row">
        <div class="col mt-4 mb-0 text-center">
          <label class="h2">중개사 매물 등록</label>
        </div>
      </div>
      <div class="mb-3 mt-3 row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6 text-center">
          <house-regist></house-regist>
        </div>
        <div class="col-sm-3"></div>
      </div>
    </div>
  </div>
</template>

<script>
import HouseRegist from "@/components/house/HouseRegist.vue";
export default {
  name: "HouseRegistView",
  components: {
    HouseRegist,
  },
};
</script>

<style></style>
