<template>
  <div class="container" style="text-align: left">
    <a :href="news.link" style="width: 477.53px; text-overflow: ellipsis; text-decoration: none">{{ news.title }}</a>
    <div style="text-align: right">
      <span>{{ news.time }}</span>
      &nbsp;
      <span>{{ news.origin }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainNewsItem",
  props: {
    news: Object,
  },
};
</script>

<style scoped>
a {
  /* text-decoration: none; */
  color: black;
}
span {
  font-size: 12px;
  color: grey;
}
.container {
  border-bottom: 1px lightgrey solid;
  width: 477.53px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
