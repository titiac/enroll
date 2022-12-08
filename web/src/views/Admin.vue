<template>
  <el-container>
    <el-header>
      <nav-bar></nav-bar>
    </el-header>
    <el-container>
      <el-main>
        <div class="tabsContainer">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="学院" name="institutes">
              <institutes></institutes>
            </el-tab-pane>
            <el-tab-pane label="班级" name="class">
              <class></class>
            </el-tab-pane>
            <el-tab-pane label="教师" name="teacher">
              <teacher></teacher>
            </el-tab-pane>
            <el-tab-pane label="学生" name="student">
              <student></student>
            </el-tab-pane>
            <el-tab-pane label="教室" name="classroom">
              <classroom></classroom>
            </el-tab-pane>
            <el-tab-pane label="课程" name="course">
              <course></course>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-main>
    </el-container>
    <el-footer> </el-footer>
  </el-container>
</template>

<script>
import NavBar from "../components/navBar/NavBar";
import Institutes from "../components/institutes/Institutes.vue";
import Class from "../components/class/Class.vue";
import Teacher from "../components/teacher/Teacher.vue";
import Student from "../components/student/Student.vue";
import Course from '../components/course/Course.vue';
import Classroom from '../components/classroom/Classroom.vue';
export default {
  components: { NavBar, Institutes, Class, Teacher, Student, Course, Classroom },
  data() {
    return {
      activeName: "institutes",
    };
  },
  created() {},
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    async 方法名() {
      let result = await this.$axios.get("/url");
      console.log(result);
      this.d = result.data;
    },
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