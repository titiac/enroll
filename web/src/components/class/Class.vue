<template>
  <div class="classContainer">
    <div class="ClaSearch">
      <el-input placeholder="请输入年级（选填）" v-model="getClaData.grade" clearable style="margin: 0 5px"></el-input>
      <el-input placeholder="请输入班级名称（选填）" v-model="getClaData.name" clearable style="margin: 0 5px"></el-input>
      <el-input placeholder="请输入班级所属学院名称（选填）" v-model="getClaData.instituteName" clearable
        style="margin: 0 5px"></el-input>
      <el-button slot="append" icon="el-icon-search" @click="getClassesList(getClaData)"
        class="ClaSearchBtn"></el-button>
    </div>
    <div class="addCla">
      <el-button type="primary" @click="addDialogVisible = true">添加班级</el-button>
    </div>
    <div class="classsForm">
      <el-table :data="classesList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="grade" label="年段" style="width: 25%">
        </el-table-column>
        <el-table-column prop="instituteName" label="学院" style="width: 25%">
        </el-table-column>
        <el-table-column prop="name" label="班级" style="width: 25%">
        </el-table-column>
        <el-table-column prop="studentCount" label="班级人数" style="width: 25%">
        </el-table-column>
        <el-table-column label="操作" style="width: 25%">
          <template slot-scope="scope">
            <el-button type="primary" @click="alterDialogOpen(scope.row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 添加对话框 -->
    <el-dialog title="添加班级" :visible.sync="addDialogVisible" width="30%" :before-close="handleClose">
      <el-input placeholder="请输入班级名称" v-model="addClaData.name" clearable style="margin: 5px 0">
      </el-input>
      <!-- 年段下拉框 -->
      <el-select v-model="addClaData.grade" placeholder="请选择年段" style="margin: 5px 3px">
        <el-option v-for="item in addGraOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <!-- 学院下拉框 -->
      <el-select v-model="addClaData.instituteId" placeholder="请选择所属学院" style="margin: 5px 3px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addClass(addClaData)">添加班级</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog title="修改班级信息" :visible.sync="alterDialogVisible" width="30%" :before-close="handleClose">
      <el-input placeholder="请输入班级名称" v-model="alterClaData.name" clearable style="margin: 5px">
      </el-input>
      <!-- 年段下拉框 -->
      <el-select v-model="alterClaData.grade" placeholder="请选择年段" style="margin: 5px">
        <el-option v-for="item in addGraOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <!-- 学院下拉框 -->
      <el-select v-model="alterClaData.instituteId" placeholder="请选择所属学院" style="margin: 5px 0">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="alterClasses(alterClaData)">确认修改班级信息</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 添加班级对话框
      addDialogVisible: false,
      // 修改班级信息对话框
      alterDialogVisible: false,
      getClaData: {
        grade: "",
        instituteName: "",
        name: "",
      },
      addClaData: {
        grade: null,
        instituteId: null,
        name: "",
      },
      // 获取所有学院数据
      institutesList: [],
      alterClaData: {},
      classesList: [],
      // 添加下拉框
      addGraOptions: [
        {
          value: 2019,
          label: 2019,
        },
        {
          value: 2020,
          label: 2020,
        },
        {
          value: 2021,
          label: 2021,
        },
        {
          value: 2022,
          label: 2022,
        },
      ],
    };
  },
  created() {
    this.getClassesList({});
    this.getInstitutesList();
  },
  mounted() {
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
    async getClassesList(getCladata) {
      console.log(getCladata);
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/class/admin/getInfo",
        data: getCladata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result.data.data);
      this.classesList = result.data.data.classes;
    },
    // 添加班级
    async addClass(addCladata) {
      console.log(addCladata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (addCladata.name !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/class/admin/add",
          data: addCladata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.addDialogVisible = false;
        if (result.data.code == 200) {
          this.$message.success('添加成功');
          this.getClassesList({})
        }
        this.addClaData = {
          grade: null,
          instituteId: null,
          name: "",
        };
      } else {
        this.$message.error("班级名称不可以为空！");
      }
    },
    // 修改班级信息
    async alterClasses(alterCladata) {
      var token = window.localStorage.getItem("token"); //要取的名称
      if (alterCladata.name !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/class/admin/modifyInfo",
          data: alterCladata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.alterDialogVisible = false;
        if (result.data.code == 200) {
          this.$message.success('修改成功');
          this.getClassesList({})
        }
      } else {
        this.$message.error("班级名称不可以为空！");
      }
    },
    // 打开修改对话框
    alterDialogOpen(Cladata) {
      this.alterDialogVisible = true;
      this.alterClaData = Cladata;
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
.classContainer {}

.ClaSearch {
  display: flex;

  .ClaSearchBtn {
    background-color: rgb(245, 245, 240);
    width: 15%;
  }
}

.addCla {
  padding: 15px;
  display: flex;
}
</style>