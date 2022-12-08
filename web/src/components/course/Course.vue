<template>
  <div class="courseContainer">
    <div class="CouSearch">
      <el-input placeholder="请输入课程名称（选填）" v-model="getCouData.name" clearable class="couinput"
        style="margin: 0 5px"></el-input>
      <el-input placeholder="请输入课程学分（选填）" v-model="getCouData.credit" clearable class="couinput"
        style="margin: 0 5px"></el-input>
      <el-select v-model="getCouData.year" placeholder="请选择课程学年（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in yearOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="getCouData.whatDay" placeholder="请选择星期（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in dayOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="getCouData.status" placeholder="请选择选课状态（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in staOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="getCouData.teacherId" placeholder="请选择课任老师（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in teachersList" :key="item.teacher.id" :label="item.teacher.name"
          :value="item.teacher.id">
        </el-option>
      </el-select>
      <el-select v-model="getCouData.instituteId" placeholder="请选择所属学院（选填）" style="margin: 0 5px" clearable>
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button slot="append" icon="el-icon-search" @click="getCoursesList(getCouData)"
        class="CouSearchBtn"></el-button>
    </div>
    <div class="addCou">
      <el-button type="primary" @click="addDialogVisible = true">添加课程</el-button>
      <el-button type="primary" @click="avaCrmDialogVisible = true">查看可用教室</el-button>
      <el-button type="primary" @click="avaTeaDialogVisible = true">查看空闲老师</el-button>
    </div>
    <div class="coursesForm">
      <el-table :data="coursesList" border style="width: 100%">
        <el-table-column type="index" width="50"> </el-table-column>
        <el-table-column prop="course.name" label="课程"> </el-table-column>
        <el-table-column prop="course.credit" label="学分"> </el-table-column>
        <el-table-column prop="course.courseHour" label="学时">
        </el-table-column>
        <el-table-column prop="course.whatDay" label="星期"> </el-table-column>
        <el-table-column prop="course.semester" label="学期"> </el-table-column>
        <el-table-column prop="course.year" label="学年"> </el-table-column>
        <el-table-column label="课任老师" width="120">
          <template slot-scope="scope">
            <el-select disabled v-model="scope.row.course.teacherId" placeholder="请选择所属学院（选填）" style="margin: 0 5px">
              <el-option v-for="item in teachersList" :key="item.teacher.id" :label="item.teacher.name"
                :value="item.teacher.id">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="上课教室" width="160">
          <template slot-scope="scope">
            <el-select disabled v-model="scope.row.course.classroomId" placeholder="请选择所属学院（选填）" style="margin: 0 5px">
              <el-option v-for="item in classroomsList" :key="item.id" :label="item.building + ' ' + item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="所属学院" width="220">
          <template slot-scope="scope">
            <el-select disabled v-model="scope.row.course.instituteId" placeholder="请选择所属学院（选填）" style="margin: 0 5px">
              <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="course.limitNumber" label="限选人数">
        </el-table-column>
        <el-table-column prop="enrollNum" label="已选人数">
        </el-table-column>
        <el-table-column prop="course.status" label="选课状态">
          <template slot-scope="scope">
            <div v-if="scope.row.course.status">开启</div>
            <div v-else>结束</div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="230" fixed="right">
          <template slot-scope="scope">
            <el-button type="primary" @click="alterDialogOpen(scope.row)">修改</el-button>
            <el-button type="primary" @click="detailDialogOpen(scope.row.course.id)">选课详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 添加对话框 -->
    <el-dialog title="添加课程" :visible.sync="addDialogVisible" width="45%" :before-close="handleClose">
      <el-input placeholder="请输入学时" v-model="addCouData.courseHour" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入课程名称" v-model="addCouData.courseName" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入课程学分" v-model="addCouData.credit" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入上课时间（例如3~4节）" v-model="addCouData.dayTime" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入限选人数" v-model="addCouData.limitNumber" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入学期（例如上/下学期）" v-model="addCouData.semester" clearable style="margin: 5px 0"></el-input>
      <el-select v-model="addCouData.teacherId" placeholder="请选择授课老师" style="margin: 5px 3px">
        <el-option v-for="item in teachersList" :key="item.teacher.id" :label="item.teacher.name"
          :value="item.teacher.id">
        </el-option>
      </el-select>
      <el-select v-model="addCouData.instituteId" placeholder="请选择所属学院" style="margin: 5px 3px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="addCouData.classroomId" placeholder="请选择教室" style="margin: 5px 3px">
        <el-option v-for="item in classroomsList" :key="item.id" :label="item.building + ' ' + item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="addCouData.whatDay" placeholder="请选择星期（选填）" style="margin: 5px 3px" clearable>
        <el-option v-for="item in dayOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="addCouData.year" placeholder="请选择课程学年（选填）" style="margin: 5px 3px" clearable>
        <el-option v-for="item in yearOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <!-- 星期和学年 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCourses(addCouData)">添加课程</el-button>
      </span>
    </el-dialog>

    <!-- 查看可用教室对话框 -->
    <el-dialog title="可用教室详情" :visible.sync="avaCrmDialogVisible" width="80%">
      <div class="avaCrmSearch">
        <el-input placeholder="请输入上课时间(例如3~4节)（选填）" v-model="getAvaCrmData.dayTime" clearable style="margin: 5px"
          class="avainput"></el-input>
        <el-input placeholder="请输入教室容量（选填）" v-model="getAvaCrmData.limitNumber" clearable style="margin: 5px"
          class="avainput"></el-input>
        <el-input placeholder="请输入学期(例如上/下学期)（选填）" v-model="getAvaCrmData.semester" clearable style="margin: 5px"
          class="avainput"></el-input>
        <el-select v-model="getAvaCrmData.year" placeholder="请选择课程学年（选填）" style="margin: 5px" clearable>
          <el-option v-for="item in yearOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="getAvaCrmData.whatDay" placeholder="请选择星期（选填）" style="margin: 5px" clearable>
          <el-option v-for="item in dayOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="getAvailableClassrooms(getAvaCrmData)"
          class="insSearchBtn" style="margin: 5px"></el-button>
      </div>
      <div class="classroomsForm">
        <el-table :data="avaClassroomsList" border style="width: 100%">
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column prop="building" label="教学楼" style="width: 33%">
          </el-table-column>
          <el-table-column prop="name" label="教室" style="width: 33%">
          </el-table-column>
          <el-table-column prop="capacity" label="教室容量" style="width: 33%">
          </el-table-column>
          <el-table-column prop="type" label="教室类型" style="width: 33%">
          </el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer"> </span>
    </el-dialog>

    <!-- 查看空闲老师对话框 -->
    <el-dialog title="空闲老师详情" :visible.sync="avaTeaDialogVisible" width="80%">
      <div class="avaCrmSearch">
        <el-input placeholder="请输入上课时间(例如3~4节)（选填）" v-model="getAvaTeaData.dayTime" clearable style="margin: 5px"
          class="avainput"></el-input>
        <el-select v-model="getAvaTeaData.instituteId" placeholder="请选择所属学院（选填）" style="margin: 5px" clearable>
          <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <el-input placeholder="请输入学期(例如上/下学期)（选填）" v-model="getAvaTeaData.semester" clearable style="margin: 5px"
          class="avainput"></el-input>
        <el-select v-model="getAvaTeaData.year" placeholder="请选择课程学年（选填）" style="margin: 5px" clearable>
          <el-option v-for="item in yearOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="getAvaTeaData.whatDay" placeholder="请选择星期（选填）" style="margin: 5px" clearable>
          <el-option v-for="item in dayOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="getAvailableTeachers(getAvaTeaData)" class="insSearchBtn"
          style="margin: 5px"></el-button>
      </div>
      <div class="classroomsForm">
        <el-table :data="avaTeachersList" border style="width: 100%">
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column prop="name" label="教师姓名"> </el-table-column>
          <el-table-column prop="sex" label="教师性别" width="100">
          </el-table-column>
          <el-table-column prop="degree" label="教师学位"> </el-table-column>
          <el-table-column prop="title" label="教师职称"> </el-table-column>
          <el-table-column prop="birthday" label="出生年月"> </el-table-column>
          <el-table-column prop="hireDate" label="聘用日期"> </el-table-column>
          <el-table-column label="所属学院" width="250">
            <template slot-scope="scope">
              <el-select disabled v-model="scope.row.instituteId" placeholder="请选择所属学院（选填）" style="margin: 0 5px">
                <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer"> </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog title="修改课程信息" :visible.sync="alterDialogVisible" width="40%" :before-close="handleClose">
      <el-input placeholder="请输入学时" v-model="alterCouData.course.courseHour" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入课程名称" v-model="alterCouData.course.name" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入课程学分" v-model="alterCouData.course.credit" clearable style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入上课时间（例如3~4节）" v-model="alterCouData.course.dayTime" clearable
        style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入限选人数" v-model="alterCouData.course.limitNumber" clearable
        style="margin: 5px 0"></el-input>
      <el-input placeholder="请输入学期（例如上/下学期）" v-model="alterCouData.course.semester" clearable
        style="margin: 5px 0"></el-input>
      <el-select v-model="alterCouData.course.teacherId" placeholder="请选择授课老师" style="margin: 5px 3px">
        <el-option v-for="item in teachersList" :key="item.teacher.id" :label="item.teacher.name"
          :value="item.teacher.id">
        </el-option>
      </el-select>
      <el-select v-model="alterCouData.course.instituteId" placeholder="请选择所属学院" style="margin: 5px 3px">
        <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="alterCouData.course.classroomId" placeholder="请选择教室" style="margin: 5px 3px">
        <el-option v-for="item in classroomsList" :key="item.id" :label="item.building + ' ' + item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="alterCouData.course.whatDay" placeholder="请选择星期（选填）" style="margin: 5px 3px">
        <el-option v-for="item in dayOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="alterCouData.course.year" placeholder="请选择课程学年" style="margin: 5px 3px">
        <el-option v-for="item in yearOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="alterCouData.course.status" placeholder="请选择选课状态" style="margin: 0 5px">
        <el-option v-for="item in staOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="altercourses(alterCouData)">确认修改课程信息</el-button>
      </span>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="学生选课详情" :visible.sync="detailDialogVisible" width="80%">
      <div class="institutesForm">
        <el-table :data="stuCouseList" border style="width: 100%">
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column prop="student.name" label="姓名" style="width: 33%">
          </el-table-column>
          <el-table-column prop="student.sex" label="性别" style="width: 33%">
          </el-table-column>
          <el-table-column label="所在班级" style="width: 33%">
            <template slot-scope="scope">
              <div>
                {{
                    scope.row.studentClass.grade +
                    " " +
                    scope.row.studentClass.name
                }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="所属学院" style="width: 33%">
            <template slot-scope="scope">
              <el-select disabled v-model="scope.row.student.instituteId" placeholder="请选择所属学院（选填）"
                style="margin: 0 5px">
                <el-option v-for="item in institutesList" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer"> </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 添加课程对话框
      addDialogVisible: false,
      // 修改课程信息对话框
      alterDialogVisible: false,
      // 选课详情对话框
      detailDialogVisible: false,
      // 查看可用教室
      avaCrmDialogVisible: false,
      // 查看空闲老师
      avaTeaDialogVisible: false,
      getCouData: {
        classroomId: "",
        courseHour: "",
        credit: "",
        dayTime: "",
        instituteId: "",
        limitNumber: "",
        semester: "",
        teacherId: "",
        whatDay: "",
        year: "",
      },
      addCouData: {
        classroomId: "",
        courseHour: "",
        credit: "",
        dayTime: "",
        instituteId: "",
        limitNumber: "",
        semester: "",
        teacherId: "",
        whatDay: "",
        year: "",
      },
      alterCouData: {
        course: {},
      },
      getAvaCrmData: {
        dayTime: "",
        limitNumber: "",
        semester: "",
        whatDay: "",
        year: "",
      },
      getAvaTeaData: {
        dayTime: "",
        instituteId: "",
        semester: "",
        whatDay: "",
        year: "",
      },
      coursesList: [],
      teachersList: [],
      institutesList: [],
      classroomsList: [],
      stuCouseList: [],
      avaClassroomsList: [],
      avaTeachersList: [],
      // 添加下拉框
      yearOptions: [
        {
          value: 2021,
          label: "2021",
        },
        {
          value: 2022,
          label: "2022",
        },
        {
          value: 2023,
          label: "2023",
        },
        {
          value: 2024,
          label: "2024",
        },
        {
          value: 2025,
          label: "2025",
        },
      ],
      dayOptions: [
        {
          value: "星期一",
          label: "星期一",
        },
        {
          value: "星期二",
          label: "星期二",
        },
        {
          value: "星期三",
          label: "星期三",
        },
        {
          value: "星期四",
          label: "星期四",
        },
        {
          value: "星期五",
          label: "星期五",
        },
        {
          value: "星期六",
          label: "星期六",
        },
        {
          value: "星期天",
          label: "星期天",
        },
      ],
      staOptions: [
        {
          value: 0,
          label: "结束",
        },
        {
          value: 1,
          label: "开启",
        },
      ],
    };
  },
  created() {
    //localhost:3000/course/get/available/teachers
    this.getCoursesList({});
    this.getteachersList();
    this.getInstitutesList();
    this.getClassroomsList();
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
    // 获取教室信息
    async getClassroomsList() {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/classroom/admin/get",
        data: {},
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.classroomsList = result.data.data.classrooms;
    },
    // 获取教师信息
    async getteachersList() {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/teacher/admin/get",
        data: {},
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.teachersList = result.data.data.teachers;
    },
    // 查看空闲教室
    async getAvailableClassrooms(getAvaCrmData) {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/course/get/available/classrooms",
        data: getAvaCrmData,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.avaClassroomsList = result.data.data.availableClassrooms;
    },
    // 查看空闲教师
    async getAvailableTeachers(getAvaTeaData) {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/course/get/available/teachers",
        data: getAvaTeaData,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.avaTeachersList = result.data.data.availableTeachers;
    },
    // 获取课程信息
    async getCoursesList(getCoudata) {
      console.log(getCoudata);
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "post",
        url: "/course/admin/getAll",
        data: getCoudata,
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result);
      this.coursesList = result.data.data.Courses;
    },
    // 添加课程
    async addCourses(addCoudata) {
      console.log(addCoudata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (addCoudata.courseHour !== "" && addCoudata.credit !== "" && addCoudata.dayTime !== "" && addCoudata.instituteId !== "" && addCoudata.limitNumber !== "" && addCoudata.semester !== "" && addCoudata.teacherId !== "" && addCoudata.whatDay !== "" && addCoudata.year !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/course/admin/add",
          data: addCoudata,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.addDialogVisible = false;
        this.addCouData = {
          classroomId: "",
          courseHour: "",
          credit: "",
          dayTime: "",
          instituteId: "",
          limitNumber: "",
          semester: "",
          teacherId: "",
          whatDay: "",
          year: "",
        };
        if (result.data.code == 200) {
          this.$message.success('添加成功');
          this.getCoursesList({})
        }
        else
          this.$message.error(result.data.message);
      } else {
        this.$message.error("参数不可以为空！");
      }
    },
    // 修改课程信息
    async altercourses(alterCoudata) {
      console.log(alterCoudata);
      var token = window.localStorage.getItem("token"); //要取的名称
      if (alterCoudata.course.courseHour !== "" && alterCoudata.course.credit !== "" && alterCoudata.course.dayTime !== "" && alterCoudata.course.instituteId !== "" && alterCoudata.course.limitNumber !== "" && alterCoudata.course.semester !== "" && alterCoudata.course.teacherId !== "" && alterCoudata.course.whatDay !== "" && alterCoudata.course.year !== "") {
        let result = await this.$axios({
          method: "post",
          url: "/course/admin/modify",
          data: alterCoudata.course,
          // 自定义请求头,
          headers: { Authorization: token },
        });
        console.log(result.data);
        this.alterDialogVisible = false;
        if (result.data.code == 200) {
          this.$message.success('修改成功');
          this.getCoursesList({})
        }
      } else {
        this.$message.error("参数不可以为空！");
      }
    },
    // 查看学生选课信息
    async getStuCourseList(courseId) {
      var token = window.localStorage.getItem("token"); //要取的名称
      let result = await this.$axios({
        method: "get",
        url: "/enroll/adminOrTeacher/getEnrollStudents",
        params: {
          courseId: courseId,
        },
        // 自定义请求头,
        headers: { Authorization: token },
      });
      console.log(result.data.data);
      this.stuCouseList = result.data.data.students;
    },
    // 打开修改对话框
    alterDialogOpen(Coudata) {
      this.alterDialogVisible = true;
      this.alterCouData = Coudata;
    },
    // 打开详情对话框
    detailDialogOpen(courseId) {
      console.log(courseId);
      this.detailDialogVisible = true;
      this.getStuCourseList(courseId);
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
@import "./course.css";

.courseContainer {}

.avaCrmSearch {
  display: flex;
}

.avaTeaSearch {
  display: flex;
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