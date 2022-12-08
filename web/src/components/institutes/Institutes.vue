<template>
  <div class="instituteContainer">
    <div class="insSearch">
      <el-input
        placeholder="请输入学院地址（选填）"
        v-model="getInsData.address"
        clearable
        style="margin: 0 5px"
      ></el-input>
      <el-input
        placeholder="请输入学院名称（选填）"
        v-model="getInsData.name"
        clearable
        style="margin: 0 5px"
      ></el-input>
      <el-button
        slot="append"
        icon="el-icon-search"
        @click="getInstitutesList(getInsData)"
        class="insSearchBtn"
      ></el-button>
    </div>
    <div class="addIns">
      <el-button type="primary" @click="addDialogVisible = true"
        >添加学院</el-button
      >
    </div>
    <div class="institutesForm">
      <el-table :data="institutesList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="name" label="学院" style="width: 33%">
        </el-table-column>
        <el-table-column prop="address" label="地址" style="width: 33%">
        </el-table-column>
        <el-table-column label="操作" style="width: 33%">
          <template slot-scope="scope">
            <el-button type="primary" @click="alterDialogOpen(scope.row)"
              >修改</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 添加对话框 -->
    <el-dialog
      title="添加学院"
      :visible.sync="addDialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-input
        placeholder="请输入学院名称"
        v-model="addInsData.name"
        clearable
        style="margin: 5px 0"
      >
      </el-input>
      <el-input
        placeholder="请输入学院地址"
        v-model="addInsData.address"
        clearable
        style="margin: 5px 0"
      ></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addInstitutes(addInsData)"
          >添加学院</el-button
        >
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog
      title="修改学院信息"
      :visible.sync="alterDialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-input
        placeholder="请输入学院名称"
        v-model="alterInsData.name"
        clearable
        style="margin: 5px 0"
      >
      </el-input>
      <el-input
        placeholder="请输入学院地址"
        v-model="alterInsData.address"
        clearable
        style="margin: 5px 0"
      ></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="alterInstitutes(alterInsData)"
          >确认修改学院信息</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 添加学院对话框
      addDialogVisible: false,
      // 修改学院信息对话框
      alterDialogVisible: false,
      getInsData: {
        address: "",
        name: "",
      },
      addInsData: {
        address: "",
        name: "",
      },
      alterInsData: {},
      institutesList: [],
    };
  },
  created() {
    this.getInstitutesList({});
  },
  methods: {
    async 方法名() {
      let result = await this.$axios.get("/url");
      console.log(result);
      this.d = result.data;
    },
    // 获取学院信息
    async getInstitutesList(getInsdata) {
      console.log(getInsdata);
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/institute/admin/get",
        data: getInsdata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.institutesList = result.data.data.institutes;
    },
    // 添加学院
    async addInstitutes(addInsdata) {
      console.log(addInsdata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (addInsdata.name !== "" && addInsdata.address !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/institute/admin/add",
          data: addInsdata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.addDialogVisible = false;
        this.addInsData = {
          address: "",
          name: "",
        };
        if(result.data.code==200){
            this.$message.success('添加成功');
            this.getInstitutesList({})
        }
      } else {
        this.$message.error("名称和地址不可以为空！");
      }
    },
    // 修改学院信息
    async alterInstitutes(alterInsdata) {
      var token = window.localStorage.getItem("token"); //要取的名称
      if (alterInsdata.name !== "" && alterInsdata.address !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/institute/admin/modify",
          data: alterInsdata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.alterDialogVisible = false;
        if(result.data.code==200){
            this.$message.success('修改成功');
            this.getInstitutesList({})
        }
      } else {
        this.$message.error("名称和地址不可以为空！");
      }
    },
    // 打开修改对话框
    alterDialogOpen(insdata) {
      this.alterDialogVisible = true;
      this.alterInsData = insdata;
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
.instituteContainer {
}
.insSearch {
  display: flex;
  .insSearchBtn {
    background-color: rgb(245, 245, 240);
    width: 15%;
  }
}
.addIns {
  padding: 15px;
  display: flex;
}

</style>