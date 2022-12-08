<template>
  <div class="teacherContainer">
    <div class="TeaSearch">
      <!-- 学院下拉框 -->
      <el-select v-model="getTeaData.instituteName" placeholder="请选择所属学院（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.name">
        </el-option>
      </el-select>
      <el-select v-model="getTeaData.degree" placeholder="请选择教师学位（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in degreeOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-input placeholder="请输入教师姓名（选填）" v-model="getTeaData.name" class="teainput" clearable
        style="margin: 0 5px"></el-input>
      <el-select v-model="getTeaData.sex" placeholder="请选择教师性别（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="getTeaData.title" placeholder="请选择教师职称（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in titleOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>

      <el-button slot="append" icon="el-icon-search" @click="getteachersList(getTeaData)"
        class="TeaSearchBtn"></el-button>
    </div>
    <div class="addTea">
      <el-button type="primary" @click="addDialogVisible = true">添加教师</el-button>
    </div>
    <div class="teachersForm">
      <el-table :data="teachersList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="teacher.name" label="教师姓名">
        </el-table-column>
        <el-table-column prop="teacher.sex" label="教师性别" width="100">
        </el-table-column>
        <el-table-column prop="teacher.degree" label="教师学位">
        </el-table-column>
        <el-table-column prop="teacher.title" label="教师职称">
        </el-table-column>
        <el-table-column prop="teacher.birthday" label="出生年月">
        </el-table-column>
        <el-table-column prop="teacher.hireDate" label="聘用日期">
        </el-table-column>
        <el-table-column label="所属学院" width="250">
          <template slot-scope="scope">
            <el-select disabled v-model="scope.row.teacher.instituteId" placeholder="请选择所属学院（选填）" style="margin: 0 5px">
              <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template slot-scope="scope">
            <el-button type="primary" @click="alterDialogOpen(scope.row)">修改</el-button>
            <el-button type="primary" @click="detailDialogOpen(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 添加对话框 -->
    <el-dialog title="添加教师" :visible.sync="addDialogVisible" width="40%" :before-close="handleClose">
      <el-select v-model="addTeaData.teacher.instituteId" placeholder="请选择所属学院（选填）" style="margin: 5px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="addTeaData.teacher.degree" placeholder="请选择教师学位（选填）" style="margin: 5px">
        <el-option v-for="item in degreeOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-input placeholder="请输入教师姓名（选填）" v-model="addTeaData.teacher.name" clearable style="margin: 5px"></el-input>
      <el-select v-model="addTeaData.teacher.sex" placeholder="请选择教师性别（选填）" style="margin: 5px">
        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="addTeaData.teacher.title" placeholder="请选择教师职称（选填）" style="margin: 5px">
        <el-option v-for="item in titleOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <div class="block">
        <span class="demonstration">出生日期 </span>
        <el-date-picker v-model="addTeaData.teacher.birthday" type="date" placeholder="请输入教师出生日期" style="margin: 5px"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addteachers(addTeaData)">添加教师</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog title="修改教师信息" :visible.sync="alterDialogVisible" width="40%" :before-close="handleClose">
      <el-select v-model="alterTeaData.teacher.instituteId" placeholder="请选择所属学院（选填）" style="margin: 5px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="alterTeaData.teacher.degree" placeholder="请选择教师学位（选填）" style="margin: 5px">
        <el-option v-for="item in degreeOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-input placeholder="请输入教师姓名（选填）" v-model="alterTeaData.teacher.name" class="teainput" clearable
        style="margin: 5px"></el-input>
      <el-select v-model="alterTeaData.teacher.sex" placeholder="请选择教师性别（选填）" style="margin: 5px">
        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="alterTeaData.teacher.title" placeholder="请选择教师职称（选填）" style="margin: 5px">
        <el-option v-for="item in titleOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <div class="block">
        <span class="demonstration">聘用日期 </span>
        <el-date-picker v-model="alterTeaData.teacher.hireDate" type="date" placeholder="请输入教师应聘日期"
          value-format="yyyy-MM-dd" style="margin: 5px 0">
        </el-date-picker>
      </div>

      <div class="block">
        <span class="demonstration">出生日期 </span>
        <el-date-picker v-model="alterTeaData.teacher.birthday" type="date" placeholder="请输入教师出生日期"
          value-format="yyyy-MM-dd" style="margin: 5px 0">
        </el-date-picker>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="alterteachers(alterTeaData)">确认修改教师信息</el-button>
      </span>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="老师详情" :visible.sync="detailDialogVisible" width="40%">
      <div class="detail">
        <img :src="detailTeaData.user.photo" alt="" />
        <div>用户名：{{ detailTeaData.user.username }}</div>
        <div>用户密码：{{ detailTeaData.user.password }}</div>

        <div>
          用户状态：
          <el-switch v-model="detailTeaData.user.status" :active-value="1" :inactive-value="0" active-text="可用"
            inactive-text="禁用" @change="switchChange($event, detailTeaData.user.id)">
          </el-switch>
        </div>
      </div>
      <span slot="footer" class="dialog-footer"> </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 添加教师对话框
      addDialogVisible: false,
      // 修改教师信息对话框
      alterDialogVisible: false,
      // 教师详情
      detailDialogVisible: false,
      detailTeaData: {
        user: {
          password: "",
          photo: "",
          username: "",
          status: 1,
        },
      },
      getTeaData: {},
      addTeaData: {
        teacher: {
          name: "",
          sex: "",
          degree: "",
          title: "",
          birthday: "",
          instituteId: "",
        },
      },
      alterTeaData: {
        teacher: {
          name: "",
          sex: "",
          degree: "",
          title: "",
          birthday: "",
          hireDate: "",
          instituteId: "",
          userId: "",
        },
      },
      teachersList: [],
      institutesList: [],
      // 性别下拉框
      sexOptions: [
        {
          value: "男",
          label: "男",
        },
        {
          value: "女",
          label: "女",
        },
      ],
      // 职称下拉框
      titleOptions: [
        {
          value: "教授",
          label: "教授",
        },
        {
          value: "副教授",
          label: "副教授",
        },
        {
          value: "讲师",
          label: "讲师",
        },
        {
          value: "助教",
          label: "助教",
        },
      ],
      // 学位下拉框
      degreeOptions: [
        {
          value: "学士",
          label: "学士",
        },
        {
          value: "硕士",
          label: "硕士",
        },
        {
          value: "博士",
          label: "博士",
        },
        {
          value: "博士后",
          label: "博士后",
        },
      ],
    };
  },
  created() {
    this.getteachersList({ address: "", name: "" });
    this.getInstitutesList();
  },
  methods: {
    async 方法名() {
      let result = await this.$axios.get("/url");
      console.log(result);
      this.d = result.data;
    },
    // 获取全部学院信息
    async getInstitutesList() {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/institute/admin/get",
        data: {},
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.institutesList = result.data.data.institutes;
    },
    // 获取教师信息
    async getteachersList(getTeadata) {
      console.log(getTeadata);
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/teacher/admin/get",
        data: getTeadata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.teachersList = result.data.data.teachers;
    },
    // 添加教师
    async addteachers(addTeadata) {
      console.log(addTeadata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (addTeadata.name !== "" && addTeadata.address !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/teacher/admin/add",
          data: addTeadata.teacher,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.addDialogVisible = false;
        if (result.data.code == 200) {
          this.$message.success('添加成功');
          this.getteachersList({})
        }
        this.addTeaData = {
          teacher: {
            name: "",
            sex: "",
            degree: "",
            title: "",
            birthday: "",
            instituteId: "",
          },
        };
      } else {
        this.$message.error("名称和地址不可以为空！");
      }
    },
    // 修改教师信息
    async alterteachers(alterTeadata) {
      var token = window.localStorage.getItem("token"); //要取的名称
      if (alterTeadata.name !== "" && alterTeadata.address !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/teacher/admin/modify",
          data: alterTeadata.teacher,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.alterDialogVisible = false;
        if (result.data.code == 200) {
          this.$message.success('修改成功');
          this.getteachersList({})
        }
      } else {
        this.$message.error("名称和地址不可以为空！");
      }
    },
    // 修改用户状态按钮
    switchChange: function ($event, id) {
      var data = {
        status: $event,
        userId: id
      }
      this.updateUserStatus(data)
    },
    // 修改用户状态
    async updateUserStatus(userData) {
      var token = window.localStorage.getItem('token');//要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/admin/forbidOrUndo",
        data: userData,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      if (result.data.code == 200)
        this.$message.success("操作成功");
    },
    // 打开修改对话框
    alterDialogOpen(Teadata) {
      this.alterDialogVisible = true;
      this.alterTeaData = Teadata;
    },
    // 打开详情对话框
    detailDialogOpen(Teadata) {
      console.log(Teadata);
      this.detailDialogVisible = true;
      this.detailTeaData = Teadata;
      console.log(this.detailTeaData);
    },
    // 关闭对话框
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
  },
};
</script>

<style lang="less" scoped>
@import "./teacher.css";

.teacherContainer {}

.TeaSearch {
  display: flex;

  .TeaSearchBtn {
    background-color: rgb(245, 245, 240);
    width: 15%;
  }
}

.addTea {
  padding: 15px;
  display: flex;
}

.detail {
  div {
    font-size: 18px;
    text-align: left;
    margin: 15px !important;
  }

  img {
    height: 100px;
    width: 100px;
  }
}
</style>