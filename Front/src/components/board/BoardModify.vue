<template>
  <b-container class="bv-example-row">
    <board-input-item type="modify" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/board/BoardInputItem.vue";

export default {
  name: "BoardModify",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
