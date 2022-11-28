<template>
  <div class="content">
    <div class="rounded-3 bg-white mb-2 pb-3 container">
      <div class="mb-3 mt-3 row" style="border-bottom: 1px grey solid">
        <div class="mt-4 mb-0 text-center">
          <label class="h2 head">찜한 매물</label>
        </div>
      </div>
      <div class="container">
        <interest-house></interest-house>
      </div>
    </div>
  </div>
</template>

<script>
import InterestHouse from "@/components/interesthouse/InteresthouseList.vue";
export default {
  name: "InterestView",
  components: {
    InterestHouse,
  },
};
</script>

<style>
.head {
  margin-bottom: 30px;
}
</style>
