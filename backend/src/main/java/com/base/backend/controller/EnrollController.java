package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.service.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 选课相关接口
 * @author: zhw
 * @created: 2022/11/29 19:32
 */
@RequestMapping("/enroll")
@RestController
public class EnrollController {
    
    @Autowired
    public EnrollService enrollService;
    
    /**
     * @Author: zhw
     * @Description: 学生选课
     * @DateTime: 2022/11/29 19:33
     */
    @GetMapping ("/student/select")
    public R studentSelect(@RequestParam Integer courseId){
        return enrollService.studentSelect(courseId);
    }
    
    /**
     * @Author: zhw
     * @Description: 学生退选
     * @DateTime: 2022/11/29 19:33
     */
    @GetMapping("/student/cancel")
    public R studentCancel(@RequestParam Integer courseId){
        return enrollService.studentCancel(courseId);
    }
    
    /**
     * @Author: zhw
     * @Description: 管理员或老师通过课程id查找到选课的学生
     * @DateTime: 2022/11/29 21:17
     */
    @GetMapping("/adminOrTeacher/getEnrollStudents")
    public R getEnrollStudents(@RequestParam Integer courseId){
        return enrollService.getEnrollStudents(courseId);
    }
    
}

