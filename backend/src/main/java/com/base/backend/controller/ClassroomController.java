package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.Classroom;
import com.base.backend.pojo.vo.AddClassroomVo;
import com.base.backend.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 与教室有关的接口
 * @author: zhw
 * @created: 2022/11/27 19:35
 */
@RestController
@RequestMapping("/classroom")
public class ClassroomController {
    
    @Autowired
    public ClassroomService classroomService;

    /**
     * @Author: zhw
     * @Description: 管理员添加教室
     * @DateTime: 2022/11/27 22:24
     */
    @PostMapping("/admin/add")
    public R addClassroom(@RequestBody AddClassroomVo addClassroomVo){
        return classroomService.addClassroom(addClassroomVo);
    }
    
    /**
     * @Author: zhw
     * @Description: 管理员获取教室
     * @DateTime: 2022/11/27 22:25
     */
    @PostMapping("/admin/get")
    public R getClassrooms(@RequestBody Classroom classroom){
        return classroomService.getClassroom(classroom);
    }
    
    @PostMapping("/admin/modify")
    public R modifyClassroom(@RequestBody Classroom classroom){
        return classroomService.modifyClassroom(classroom);
    }
}

