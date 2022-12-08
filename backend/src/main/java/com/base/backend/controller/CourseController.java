package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.Course;
import com.base.backend.pojo.vo.AddCourseRequestVo;
import com.base.backend.pojo.vo.AdminGetCourseRequestVo;
import com.base.backend.pojo.vo.GetAvailableClassroom;
import com.base.backend.pojo.vo.GetAvailableTeacher;
import com.base.backend.service.ClassroomService;
import com.base.backend.service.CourseService;
import com.base.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 课程相关的接口
 * @author: zhw
 * @created: 2022/11/28 08:45
 */
@RequestMapping("/course")
@RestController
public class CourseController {
    
    @Autowired
    public CourseService courseService;
    
    @Autowired
    public ClassroomService classroomService;
    
    @Autowired
    public TeacherService teacherService;
    
    /**
     * @Author: zhw
     * @Description: 添加课程
     * @DateTime: 2022/11/29 14:53
     */
    @PostMapping("/admin/add")
    public R addCourse(@RequestBody AddCourseRequestVo addCourseRequestVo){
        return courseService.addCourse(addCourseRequestVo);
    }
    
    /**
     * @Author: zhw
     * @Description: 查询该时间段有空的老师, 如果一个参数都不给的话, 会返回一节课都没有的老师
     * @DateTime: 2022/11/29 14:54
     */
    @PostMapping("/get/available/teachers")
    public R getAvailableTeachers(@RequestBody GetAvailableTeacher getAvailableTeacher){
        return teacherService.getAvailableTeacher(getAvailableTeacher);
    }
    
    @PostMapping("/get/available/classrooms")
    public R getAvailableClassrooms(@RequestBody GetAvailableClassroom getAvailableClassroom){
        return classroomService.getAvailableClassrooms(getAvailableClassroom);
    }
    
    @PostMapping("/admin/getAll")
    public R getCourse(@RequestBody AdminGetCourseRequestVo adminGetCourseRequestVo) {
        return courseService.getCourse(adminGetCourseRequestVo);
    }
    
    /**
     * @Author: zhw
     * @Description: 管理员编辑课程信息
     * @DateTime: 2022/11/29 16:02
     */
    @PostMapping("/admin/modify")
    public R modifyCourse(@RequestBody Course course) {
        return courseService.modifyCourse(course);
    }
    
    
    @GetMapping("/student/get")
    public R studentGetCourse(){
        return courseService.studentGetCourse();
    }
    
    @GetMapping("/teacher/get")
    public R teacherGetCourse(){
        return courseService.teacherGetCourse();
    }
}