<template>
  <div class="navbarContainer">
    <div class="userInfo">
      <div class="avatar"><img :src="userInfo.photo" alt="" /></div>
      <div class="username">{{ userInfo.username }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: this.$store.state.userInfo,
    };
  },
  created() {
    this.userInfo.username = window.localStorage.getItem("username");
    this.userInfo.photo = window.localStorage.getItem("avatar");
    console.log(this.userInfo);
  },
  methods: {
    async 方法名() {
      let result = await this.$axios.get("/url");
      console.log(result);
      this.d = result.data;
    },
  },
};
</script>

<style lang="less" scoped>
.navbarContainer {
  background-color: rgb(67, 185, 249);
  height: 100%;
  display: flex;
  .userInfo {
    display: flex;
    padding: 0 30px;
  }
  .username {
    color: #fff;
    font-size: 20px;
    margin-left: 15px;
    padding-top: 15px;
  }
  .avatar {
    margin-top: 5px;
    height: 50px;
    width: 50px;
    img {
      border-radius: 50%;
      height: 100%;
      width: 100%;
    }
  }
}
</style>