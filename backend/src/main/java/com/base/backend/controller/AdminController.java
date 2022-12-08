package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.vo.ForbidOrUndoRequestVo;
import com.base.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: select
 * @description: 管理员相关接口
 * @author: zhw
 * @created: 2022/11/24 21:47
 */
@RequestMapping("/admin")
@RestController
public class AdminController {
    
    @Autowired
    public UserService userService;
    
    /**
     * @Author: zhw
     * @Description: 封禁用户  
     * @DateTime: 2022/11/26 20:54
     */
    @PostMapping("/forbidOrUndo")
    public R forbidOrUndo(@RequestBody ForbidOrUndoRequestVo forbidOrUndoRequestVo){
        return userService.forbidOrUndo(forbidOrUndoRequestVo);
    }
    
}

