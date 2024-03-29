package com.base.backend.controller;

import com.base.backend.common.R;
import com.base.backend.pojo.vo.LoginVo;
import com.base.backend.pojo.vo.RegisterVo;
import com.base.backend.service.UserService;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: base
 * @description: 用户相关接口
 * @author: zhw
 * @created: 2022/11/16 23:44
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
//    @PostMapping("/register")
//    public R register(@RequestBody RegisterVo registerVo) {
//        return userService.register(registerVo);
//    }
    
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }
    
//    @GetMapping("/getInfo")
//    public R getInfo() {
//        return userService.getInfo();
//    }
}

