<template>
  <div class="classroomContainer">
    <div class="CrmSearch">
      <el-input
        placeholder="请输入教学楼（选填）"
        v-model="getCrmData.building"
        clearable
        class="crminput"
        style="margin: 0 5px"
      ></el-input>
      <el-input
        placeholder="请输入教室容量（选填）"
        v-model="getCrmData.capacity"
        clearable
        class="crminput"
        style="margin: 0 5px"
      ></el-input>
      <el-input
        placeholder="请输入教室号（选填）"
        v-model="getCrmData.name"
        clearable
        class="crminput"
        style="margin: 0 5px"
      ></el-input>
      <el-select
        v-model="getCrmData.type"
        placeholder="请选择教室类型（选填）"
        style="margin: 0 5px"
      >
        <el-option
          v-for="item in crmTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        slot="append"
        icon="el-icon-search"
        @click="getClassroomsList(getCrmData)"
        class="CrmSearchBtn"
      ></el-button>
    </div>
    <div class="addCrm">
      <el-button type="primary" @click="addDialogVisible = true"
        >添加教室</el-button
      >
    </div>
    <div class="classroomsForm">
      <el-table :data="classroomsList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="building" label="教学楼" style="width: 33%">
        </el-table-column>
        <el-table-column prop="name" label="教室" style="width: 33%">
        </el-table-column>
        <el-table-column prop="capacity" label="教室容量" style="width: 33%">
        </el-table-column>
        <el-table-column prop="type" label="教室类型" style="width: 33%">
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
      title="添加教室"
      :visible.sync="addDialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-input
        placeholder="请输入教室房间号"
        v-model="addCrmData.name"
        clearable
        style="margin: 5px 0"
      >
      </el-input>
      <el-input
        placeholder="请输入教室所在楼"
        v-model="addCrmData.building"
        clearable
        style="margin: 5px 0"
      ></el-input>
      <el-input
        placeholder="请输入教室容量"
        v-model="addCrmData.capacity"
        clearable
        style="margin: 5px 0"
      ></el-input>
      <el-select
        v-model="addCrmData.type"
        placeholder="请选择教室类型（选填）"
        style="margin: 0 5px"
      >
        <el-option
          v-for="item in crmTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addclassrooms(addCrmData)"
          >添加教室</el-button
        >
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog
      title="修改教室信息"
      :visible.sync="alterDialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-input
        placeholder="请输入教室房间号"
        v-model="alterCrmData.name"
        clearable
        style="margin: 5px 0"
      >
      </el-input>
      <el-input
        placeholder="请输入教室所在楼"
        v-model="alterCrmData.building"
        clearable
        style="margin: 5px 0"
      ></el-input>
      <el-input
        placeholder="请输入教室容量"
        v-model="alterCrmData.capacity"
        clearable
        style="margin: 5px 0"
      ></el-input>
      <el-select
        v-model="alterCrmData.type"
        placeholder="请选择教室类型（选填）"
        style="margin: 0 5px"
      >
        <el-option
          v-for="item in crmTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="alterclassrooms(alterCrmData)"
          >确认修改教室信息</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 添加教室对话框
      addDialogVisible: false,
      // 修改教室信息对话框
      alterDialogVisible: false,
      getCrmData: {
        building: "",
        capacity: "",
        name: "",
        type: "",
      },
      addCrmData: {
        building: "",
        capacity: "",
        name: "",
        type: "",
      },
      alterCrmData: {},
      classroomsList: [],
      crmTypeOptions: [
        {
          value: "普通教室",
          lable: "普通教室",
        },
        {
          value: "梯形教室",
          lable: "梯形教室",
        },
        {
          value: "多媒体教室",
          lable: "多媒体教室",
        },
      ],
    };
  },
  created() {
    this.getClassroomsList({});
  },
  methods: {
    async 方法名() {
      let result = await this.$axios.get("/url");
      console.log(result);
      this.d = result.data;
    },
    // 获取教室信息
    async getClassroomsList(getCrmdata) {
      console.log(getCrmdata);
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/classroom/admin/get",
        data: getCrmdata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.classroomsList = result.data.data.classrooms;
    },
    // 添加教室
    async addclassrooms(addCrmdata) {
      console.log(addCrmdata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (addCrmdata.name !== "" && addCrmdata.building !== "" && addCrmdata.capacity!==''&&addCrmdata.type!=='') {
        let result = await this.$axios({
          method: "post",
          url: "/classroom/admin/add",
          data: addCrmdata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.addDialogVisible = false;
        this.addCrmData = {
          building: "",
          capacity: "",
          name: "",
          type: "",
        };
        if(result.data.code==200){
            this.$message.success('教室添加成功');
            this.getClassroomsList({})
        }
        else
        this.$message.error("教室已存在！");
      } else{
        this.$message.error("参数不可以为空！");
      }
    },
    // 修改教室信息
    async alterclassrooms(alterCrmdata) {
      var token = window.localStorage.getItem("token"); //要取的名称
      if (alterCrmdata.name !== "" && alterCrmdata.building !== "" && alterCrmdata.capacity!==''&&alterCrmdata.type!=='') {
        let result = await this.$axios({
          method: "post",
          url: "/classroom/admin/modify",
          data: alterCrmdata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.alterDialogVisible = false;
        if(result.data.code==200){
            this.$message.success('教室添加成功');
            this.getClassroomsList({})
        }
        else
        this.$message.error("教室已存在！");
      } else {
        this.$message.error("参数不可以为空！");
      }
    },
    // 打开修改对话框
    alterDialogOpen(Crmdata) {
      this.alterDialogVisible = true;
      this.alterCrmData = Crmdata;
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
@import "./classroom.css";
.classroomContainer {
}
.CrmSearch {
  display: flex;
  .CrmSearchBtn {
    background-color: rgb(245, 245, 240);
    width: 15%;
  }
}
.addCrm {
  padding: 15px;
  display: flex;
}
</style>