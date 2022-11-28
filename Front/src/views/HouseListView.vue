<template>
  <div>
    <!-- <house-search-bar></house-search-bar> -->
    <!-- Main Content -->
    <section class="main-container" style="height: 850px">
      <house-list-map></house-list-map>
    </section>
  </div>
</template>

<script>
// import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
// import HouseList from "@/components/house/HouseList.vue";
// import HouseKakaoMap from "@/components/house/HouseKakaoMap.vue";
import HouseListMap from "@/components/house/HouseListMap.vue";

export default {
  name: "HouseListView",
  components: {
    // HouseSearchBar,
    // HouseList,
    // HouseKakaoMap,
    HouseListMap,
  },
};
</script>

<style></style>
