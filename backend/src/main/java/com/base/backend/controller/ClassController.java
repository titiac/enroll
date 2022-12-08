package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.Class;
import com.base.backend.pojo.vo.AddClassVo;
import com.base.backend.pojo.vo.SelectClassVo;
import com.base.backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 与班级相关接口，主要是管理员操作
 * @author: zhw
 * @created: 2022/11/26 20:43
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    public ClassService classService;
    
    /**
     * @Author: zhw
     * @Description: 添加班级
     * @DateTime: 2022/11/25 14:23
     */
    @PostMapping("/admin/add")
    public R addClass(@RequestBody AddClassVo addClassVo){
        return classService.addClass(addClassVo);
    }

    /**
     * @Author: zhw
     * @Description: 获取所有班级
     * @DateTime: 2022/11/26 14:50
     */
    @PostMapping("admin/getInfo")
    public R getAllClass(@RequestBody SelectClassVo selectClassVo){
        return classService.getAllClass(selectClassVo);
    }

    /**
     * @Author: zhw
     * @Description: 修改班级信息
     * @DateTime: 2022/11/26 15:34
     */
    @PostMapping("/admin/modifyInfo")
    public R modifyClassInfo(@RequestBody Class newClass){
        return classService.modifyClassInfo(newClass);
    }
}

