<template>
  <div class="container">
    <div class="loginArea">
      <h2 class="title">登录学生管理平台</h2>
      <el-form
        :model="loginForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm loginForm"
      >
        <el-form-item label="用户名" prop="account">
          <el-input v-model.number="loginForm.account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >登录</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        password: "",
        account: "",
        isLogin: false,
      },
      rules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 1, max: 15, message: "长度在 1 到 15 位", trigger: "blur" },
        ],
        account: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 2,
            max: 25,
            message: "长度在 2 到 25 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    async login() {
      let result = await this.$axios({
        method: "post",
        url: "/user/login",
        data: {
          password: this.loginForm.password,
          username: this.loginForm.account,
        },
      });
      console.log(result.data);
      if (result.data.code == 200) {
        // 登录成功 将token和用户信息存入本地
        var token = "Bearer " + result.data.data.token;
        window.localStorage.setItem("token",token);
        window.localStorage.setItem("username", result.data.data.user.username);
        window.localStorage.setItem("avatar", result.data.data.user.photo);
        // 存入用户信息
        this.$store.commit("updataUserInfo", result.data.data.user);
        // 修改vuex中的登录状态
        this.$store.commit("updataIsLogin", true);
        //同步isLogin值
        this.isLogin = this.$store.state.isLogin;
        // vuex存入token
        this.$store.commit("updataToken", token);
        // 鉴别身份 跳转页面
        console.log(result.data.data.user.userType)
        if(result.data.data.user.userType=="superAdmin")
        this.$router.push('/admin')
        else if(result.data.data.user.userType=="student")
        this.$router.push('/student')
        else if(result.data.data.user.userType=="teacher")
        this.$router.push('/teacher')
        this.$message.success('登录成功');
      }
      else if(!result){
        this.$message.error('登录失败，请检查账号密码或网络！');
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.login();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style lang="less" scoped>
@import "./style.css";

.container {
  padding-top: 150px;
  background-image: linear-gradient(to right bottom, #00a2ff, #4592df, #95bdeb);
  height: calc(100vh);
  .loginArea {
    background-color: #fff;
    width: 30%;
    margin: 0 auto;
    padding: 30px 80px 20px 0;
    border-radius: 6px;
    .loginFrom {
      padding: 30px !important;
      border-radius: 20px;
    }
  }
  .title {
    padding-bottom: 25px;
    margin-left: 45px;
  }
}
</style>