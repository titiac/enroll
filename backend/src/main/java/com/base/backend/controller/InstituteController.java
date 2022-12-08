package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.Institute;
import com.base.backend.pojo.vo.AddInstituteVo;
import com.base.backend.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 学院相关接口
 * @author: zhw
 * @created: 2022/11/26 20:32
 */

@RequestMapping("/institute")
@RestController
public class InstituteController {
    
    @Autowired
    public InstituteService instituteService;
    
    
    /**
     * @Author: zhw
     * @Description: 添加学院
     * @DateTime: 2022/11/25 11:10
     */
    @PostMapping("/admin/add")
    public R addInstitute(@RequestBody AddInstituteVo addInstituteVo){
        return instituteService.addInstitute(addInstituteVo);
    }

    /**
     * @Author: zhw
     * @Description: 修改学院信息
     * @DateTime: 2022/11/25 12:57
     */
    @PostMapping("/admin/modify")
    public R modifyInstitute(@RequestBody Institute institute) {
        return instituteService.modifyInstitute(institute);
    }

    /**
     * @Author: zhw
     * @Description: 查看学院 / 获取学院信息
     * @DateTime: 2022/11/25 12:58
     */
    @PostMapping("/admin/get")
    public R getInstitute(@RequestBody AddInstituteVo addInstituteVo){
        return instituteService.getInstitute(addInstituteVo);
    }

    /**
     * @Author: zhw
     * @Description: 删除学院信息
     * @DateTime: 2022/11/25 13:30
     */
    // TODO: 2022/11/26 这边有bug，就是删除学院对应的班级和学生还在 
//    @PostMapping("/delete/institute")
//    public R deleteInstitute(@RequestBody Institute institute){
//        return instituteService.deleteInstitute(institute);
//    }
    
}

