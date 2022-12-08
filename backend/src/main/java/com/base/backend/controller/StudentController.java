package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.Student;
import com.base.backend.pojo.vo.AddStudentVo;
import com.base.backend.pojo.vo.GetStudentVo;
import com.base.backend.pojo.vo.ModifyStudentVo;
import com.base.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 与学生相关的接口
 * @author: zhw
 * @created: 2022/11/26 20:36
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    public StudentService studentService;

    /**
     * @Author: zhw
     * @Description: 添加学生
     * @DateTime: 2022/11/25 22:43
     */
    @PostMapping("/admin/add")
    public R addStudent(@RequestBody AddStudentVo addStudentVo) {
        return studentService.addStudent(addStudentVo);
    }

    /**
     * @Author: zhw
     * @Description: 查询所有学生 
     * @DateTime: 2022/11/26 15:31
     */
    @PostMapping("/admin/getAll")
    public R getAllStudent(@RequestBody GetStudentVo getStudentVo){
        return studentService.getAllStudent(getStudentVo);
    }

    /**
     * @Author: zhw
     * @Description: 修改学生信息， 错误添加、转专业 
     * @DateTime: 2022/11/26 15:42
     */
    @PostMapping("/admin/modify")
    public R modifyStudent(@RequestBody ModifyStudentVo modifyStudentVo){
        return studentService.modifyStudent(modifyStudentVo);
    }
}

