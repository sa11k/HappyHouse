<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col style="text-align: left; margin-left: 10%; margin-right: 10%">
        <h3>글 작성</h3>
      </b-col>
    </b-row>
    <board-input-item type="register" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/board/BoardInputItem.vue";

export default {
  name: "boardRegister",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
