<template>
<el-container>
    <el-header>
      <nav-bar></nav-bar>
    </el-header>
    <el-container>
      <el-main>
        <user-teacher></user-teacher>
      </el-main>
    </el-container>
    <el-footer> </el-footer>
  </el-container>
</template>

<script>
import NavBar from '../components/navBar/NavBar.vue'
import UserTeacher from '../components/userTeacher/UserTeacher.vue'
export default {
  components: { NavBar,UserTeacher },
  data() {
    return {
      
    };
  },
  created() {},
  methods: {
    
    },
};
</script>

<style lang="less" scoped>
.el-container {
  min-height: calc(100vh);
}
.tabsContainer {
  margin: 0 60px;
}
</style>