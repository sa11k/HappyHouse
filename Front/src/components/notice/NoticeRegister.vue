<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col style="text-align: left; margin-left: 10%; margin-right: 10%">
        <h3>공지사항 작성</h3>
      </b-col>
    </b-row>
    <notice-input-item type="register" />
  </b-container>
</template>

<script>
import NoticeInputItem from "@/components/notice/NoticeInputItem.vue";

export default {
  name: "noticeRegister",
  components: {
    NoticeInputItem,
  },
};
</script>

<style></style>
