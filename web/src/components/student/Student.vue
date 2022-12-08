<template>
  <div class="studentContainer">
    <div class="StuSearch">
      <el-input placeholder="请输入学生所在省份（选填）" v-model="getStuData.province" clearable class="stuinput"
        style="margin: 0 5px"></el-input>
      <el-input placeholder="请输入学生所在城市（选填）" v-model="getStuData.city" clearable style="margin: 0 5px"
        class="stuinput"></el-input>
      <el-select v-model="getStuData.instituteName" placeholder="请选择所属学院（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.name">
        </el-option>
      </el-select>
      <el-input placeholder="请输入学生年段（选填）" v-model="getStuData.grade" clearable style="margin: 0 5px"
        class="stuinput"></el-input>
      <el-input placeholder="请输入学生班级（选填）" v-model="getStuData.className" clearable style="margin: 0 5px"
        class="stuinput"></el-input>
      <el-input placeholder="请输入学生姓名（选填）" v-model="getStuData.name" clearable style="margin: 0 5px"
        class="stuinput"></el-input>
      <el-select v-model="getStuData.sex" placeholder="请选择学生性别（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-button slot="append" icon="el-icon-search" @click="getStudentsList(getStuData)"
        class="StuSearchBtn"></el-button>
    </div>
    <div class="addStu">
      <el-button type="primary" @click="addDialogVisible = true">添加学生</el-button>
    </div>
    <div class="studentsForm">
      <el-table :data="studentsList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="student.province" label="省份">
        </el-table-column>
        <el-table-column prop="student.city" label="城市"> </el-table-column>
        <el-table-column prop="studentClass.grade" label="年段"> </el-table-column>
        <el-table-column label="班级" width="250">
          <template slot-scope="scope">
            <el-select disabled v-model="scope.row.student.classId" placeholder="请选择所属班级（选填）" style="margin: 0 5px">
              <el-option v-for="item in classesList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="student.name" label="姓名"> </el-table-column>
        <el-table-column prop="student.birthday" label="出生日期">
        </el-table-column>
        <el-table-column prop="student.sex" label="性别"> </el-table-column>
        <el-table-column label="所属学院" width="250">
          <template slot-scope="scope">
            <el-select disabled v-model="scope.row.student.instituteId" placeholder="请选择所属学院（选填）" style="margin: 0 5px">
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
    <el-dialog title="添加学生" :visible.sync="addDialogVisible" width="40%" :before-close="handleClose">
      <div class="block">
        <span class="demonstration">出生日期 </span>
        <el-date-picker v-model="addStuData.birthday" type="date" placeholder="选择学生出生日期" style="margin: 5px 0"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </div>

      <el-input placeholder="请输入学生名称" v-model="addStuData.name" clearable style="margin: 5px 0">
      </el-input>
      <el-select v-model="addStuData.sex" placeholder="请选择学生性别" style="margin: 5px 0" clearable>
        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-input placeholder="请输入学生省份" v-model="addStuData.province" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入学生城市" v-model="addStuData.city" clearable style="margin: 5px 0"></el-input>
      <el-select v-model="addStuData.instituteId" placeholder="请选择学院" style="margin: 5px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="addStuData.classId" placeholder="请选择班级" style="margin: 5px">
        <el-option v-for="item in classesList" :key="item.id" :label="item.grade + ' ' + item.name" :value="item.id">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addstudents(addStuData)">添加学生</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog title="修改学生信息" :visible.sync="alterDialogVisible" width="40%" :before-close="handleClose">
      <div class="block">
        <span class="demonstration">出生日期 </span>
        <el-date-picker v-model="alterStuData.student.birthday" type="date" placeholder="选择学生出生日期"
          value-format="yyyy-MM-dd" style="margin: 5px 0">
        </el-date-picker>
      </div>

      <el-input placeholder="请输入学生名称" v-model="alterStuData.student.name" clearable style="margin: 5px 0">
      </el-input>
      <el-select v-model="alterStuData.student.sex" placeholder="请选择学生性别" style="margin: 5px 0" clearable>
        <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-input placeholder="请输入学生省份" v-model="alterStuData.student.province" clearable
        style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入学生城市" v-model="alterStuData.student.city" clearable style="margin: 5px 0"></el-input>
      <el-select v-model="alterStuData.student.instituteId" placeholder="请选择学院" style="margin: 5px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="alterStuData.student.classId" placeholder="请选择班级" style="margin: 5px">
        <el-option v-for="item in classesList" :key="item.id" :label="item.grade + ' ' + item.name" :value="item.id">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="alterstudents(alterStuData)">确认修改学生信息</el-button>
      </span>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="学生详情" :visible.sync="detailDialogVisible" width="40%">
      <div class="detail">
        <img :src="detailStuData.user.photo" alt="" />
        <div>用户名： {{ detailStuData.user.username }}</div>
        <div>用户密码：{{ detailStuData.user.password }}</div>
        <div>
          用户状态：
          <el-switch v-model="detailStuData.user.status" :active-value="1" :inactive-value="0" active-text="可用"
            inactive-text="禁用" @change="switchChange($event, detailStuData.user.id)">
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
      // 添加学生对话框
      addDialogVisible: false,
      // 修改学生信息对话框
      alterDialogVisible: false,
      detailDialogVisible: false,
      getStuData: {
        provience: "",
        name: "",
        sex: null,
        instituteName: "",
        grade: null,
        city: "",
      },
      addStuData: {
        birthday: "",
        name: "",
        classId: "",
        institutesId: "",
        name: "",
        province: "",
        sex: "",
      },
      alterStuData: {
        student: {
          birthday: "",
          name: "",
          classId: "",
          institutesId: "",
          name: "",
          province: "",
          sex: "",
        },
      },
      studentsList: [],
      institutesList: [],
      classesList: [],
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
      detailStuData: {
        user: {
          password: "",
          photo: "",
          username: "",
        },
      },
    };
  },
  created() {
    this.getStudentsList({});
    this.getInstitutesList();
    this.getClassesList();
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
    // 获取班级信息
    async getClassesList() {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/class/admin/getInfo",
        data: {},
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result.data.data);
      this.classesList = result.data.data.classes;
    },
    // 获取学生信息
    async getStudentsList(getStudata) {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/student/admin/getAll",
        data: getStudata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result.data.data.students);
      this.studentsList = result.data.data.students;
    },
    // 添加学生
    async addstudents(addStudata) {
      console.log(addStudata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (addStudata.name !== "" && addStudata.address !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/student/admin/add",
          data: addStudata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.addDialogVisible = false;
        this.addStuData = {
          birthday: "",
          name: "",
          classeId: "",
          institutesId: "",
          name: "",
          province: "",
          sex: "",
        };
        if (result.data.code == 200) {
          this.$message.success('添加成功');
          this.getStudentsList({})
        }
      } else {
        this.$message.error("名称和地址不可以为空！");
      }
    },
    // 修改学生信息
    async alterstudents(alterStudata) {
      var token = window.localStorage.getItem("token"); //要取的名称
      if (alterStudata.name !== "" && alterStudata.address !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/student/admin/modify",
          data: alterStudata.student,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.alterDialogVisible = false;
        if (result.data.code == 200) {
          this.$message.success('修改成功');
          this.getStudentsList({})
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
    alterDialogOpen(Studata) {
      this.alterDialogVisible = true;
      this.alterStuData = Studata;
    },
    // 打开详情对话框
    detailDialogOpen(Studata) {
      console.log(Studata);
      this.detailDialogVisible = true;
      this.detailStuData = Studata;
      console.log(this.detailStuData);
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
@import "./student.css";

.studentContainer {}

.StuSearch {
  display: flex;

  .StuSearchBtn {
    background-color: rgb(245, 245, 240);
    width: 15%;
  }
}

.addStu {
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