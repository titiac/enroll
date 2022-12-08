<template></template>

<script>
export default {
  data() {
    return {};
  },
  created() {},
  methods: {
    async 方法名() {
      var token=window.localStorage.getItem('token');//要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/institute/admin/get",
        data: getInsdata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
    },
  },
};
</script>

<style lang="less" scoped>
</style>