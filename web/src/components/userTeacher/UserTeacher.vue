<template>
  <div class="courseContainer">
    <div class="coursesForm">
      <el-table :data="coursesList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="course.name" label="课程" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.credit" label="学分" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.dayTime" label="上课时间" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.whatDay" label="星期" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.year" label="学年" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.semester" label="学期" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.limitNumber" label="限选人数" style="width: 33%">
        </el-table-column>
        <el-table-column prop="course.courseHour" label="课时" style="width: 33%">
        </el-table-column>
        <el-table-column  label="上课地点" style="width: 33%">
          <template slot-scope="scope">
            <div>{{scope.row.classroom.building + ' ' +  scope.row.classroom.name}}</div>
          </template>
        </el-table-column>
        <el-table-column prop="course.status" label="选课状态">
          <template slot-scope="scope">
            <div v-if="scope.row.course.status">开启</div>
            <div v-else>结束</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      coursesList: [],
    };
  },
  created() {
    this.getCoursesList();
  },
  methods: {
    async 方法名() {
      let result = await this.$axios.get("/url");
      console.log(result);
      this.d = result.data;
    },
    // 获取课程信息
    async getCoursesList() {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "get",
        url: "/course/teacher/get",
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.coursesList = result.data.data.courses;
    },
    // 关闭对话框
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
};
</script>

<style lang="less" scoped>
.courseContainer {
  padding: 35px;
}
.CouSearch {
  display: flex;
  .CouSearchBtn {
    background-color: rgb(245, 245, 240);
    width: 15%;
  }
}
.addCou {
  padding: 15px;
  display: flex;
}

</style>