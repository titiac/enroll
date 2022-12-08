package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.vo.AddTeacherVo;
import com.base.backend.pojo.vo.ModifyTeacherVo;
import com.base.backend.pojo.vo.SelectTeacherVo;
import com.base.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 与教师相关的接口
 * @author: zhw
 * @created: 2022/11/26 20:51
 */

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    public TeacherService teacherService;
    
    /**
     * @Author: zhw
     * @Description: 聘用老师，             
     * @DateTime: 2022/11/26 15:48
     */
    @PostMapping("/admin/add")
    public R addTeacher(@RequestBody  AddTeacherVo addTeacherVo){
        return teacherService.addTeacher(addTeacherVo);
    }

    /**
     * @Author: zhw
     * @Description: 查询老师          
     * @DateTime: 2022/11/26 15:50
     */
    @PostMapping("/admin/get")
    public R getTeachers(@RequestBody SelectTeacherVo selectTeacherVo){
        return teacherService.getTeachers(selectTeacherVo);
    }

    /**
     * @Author: zhw
     * @Description: 修改老师信息
     * @DateTime: 2022/11/26 15:55
     */
    @PostMapping("/admin/modify")
    public R modifyTeacher(@RequestBody ModifyTeacherVo modifyTeacherVo){
        System.out.println(modifyTeacherVo);
        return teacherService.modifyTeacher(modifyTeacherVo);
    }
}

