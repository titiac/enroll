package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.UserMapper;
import com.base.backend.pojo.User;
import com.base.backend.pojo.vo.ForbidOrUndoRequestVo;
import com.base.backend.pojo.vo.LoginVo;
import com.base.backend.pojo.vo.RegisterVo;
import com.base.backend.service.UserService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import com.base.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: base
 * @description: 用户相关服务
 * @author: zhw
 * @created: 2022/11/16 23:46
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public R register(RegisterVo registerVo) {
        String username = registerVo.getUsername();
        String password = registerVo.getPassword();
        String confirmedPassword = registerVo.getConfirmedPassword();
        
        if (username == null) {
            return R.error().resultEnum(ResultEnum.USERNAME_NULL_ERROR);
        }
        
        if (password == null || confirmedPassword == null) {
            return R.error().resultEnum(ResultEnum.PASSWORD_NULL_ERROR);
        }

        username = username.trim();
        if (username.length() == 0) {
            return R.error().resultEnum(ResultEnum.USERNAME_NULL_ERROR);
        }

        if (password.length() == 0 || confirmedPassword.length() == 0) {
            return R.error().resultEnum(ResultEnum.PASSWORD_NULL_ERROR);
        }

        if (username.length() > 100) {
            R.error().resultEnum(ResultEnum.USERNAME_TOO_LONG);
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            return R.error().resultEnum(ResultEnum.PASSWORD_TOO_LONG);
        }

        if (!password.equals(confirmedPassword)) {
            return R.error().resultEnum(ResultEnum.PASSWORD_DIFFERENT);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            return R.error().resultEnum(ResultEnum.USERNAME_IS_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/13557_lg_eb1ced6a92.jpg";
        User user = new User(null, username, encodedPassword, photo, "admin", 1);
        userMapper.insert(user);
        return R.ok();
    }

    @Override
    public R login(LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);  // 登录失败，会自动处理
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());
        if(user.getStatus() == 0) {
            return R.error().resultEnum(ResultEnum.USERNAME_IS_FROZEN);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("token", jwt);
        returnMap.put("user", user);
        
        return R.ok().data(returnMap);
    }

    @Override
    public R getInfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        return R.ok().data("myselfInfo", user);
    }

    @Override
    public R forbidOrUndo(ForbidOrUndoRequestVo forbidOrUndoRequestVo) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        
        if(!Objects.equals(user.getUserType(), "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        Integer userId = forbidOrUndoRequestVo.getUserId();
        Integer status = forbidOrUndoRequestVo.getStatus();
        if(userId == null || status == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        } 
        
        User oldUser = userMapper.selectById(userId);
        oldUser.setStatus(status);
        
        userMapper.updateById(oldUser);
        return R.ok();
    }
}

