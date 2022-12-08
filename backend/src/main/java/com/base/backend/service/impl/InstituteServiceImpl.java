package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.InstituteMapper;
import com.base.backend.pojo.Institute;
import com.base.backend.pojo.User;
import com.base.backend.pojo.vo.AddInstituteVo;
import com.base.backend.service.InstituteService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: select
 * @description: 与学院相关的服务
 * @author: zhw
 * @created: 2022/11/25 11:03
 */
@Service
public class InstituteServiceImpl implements InstituteService {

    @Autowired
    public InstituteMapper instituteMapper;
    
    @Override
    public R addInstitute(AddInstituteVo addInstituteVo) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        if(!Objects.equals(user.getUserType(), "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        String name = addInstituteVo.getName();
        String address = addInstituteVo.getAddress();

        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Institute oldInstitute = instituteMapper.selectOne(wrapper);
        
        if(oldInstitute != null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_IS_EXIST);
        }

        instituteMapper.insert(new Institute(null, name, address));
        return R.ok();
    }

    @Override
    public R modifyInstitute(Institute institute) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")) {
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        Integer id = institute.getId();
        String name = institute.getName();
        String address = institute.getAddress();

        Institute oldInstitute = instituteMapper.selectById(id);
        if(oldInstitute == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }
        
        instituteMapper.updateById(institute);
        
        return R.ok();
    }

    @Override
    public R getInstitute(AddInstituteVo addInstituteVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")) {
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        String name = addInstituteVo.getName();
        String address = addInstituteVo.getAddress();
        if(name != null && name.length() > 0) {
            wrapper.and(i -> i.like("name", name));
        }
        
        if(address != null && address.length() > 0) {
            wrapper.and(i -> i.like("address", address));
        }
        
        List<Institute> institutes = instituteMapper.selectList(wrapper);
        return R.ok().data("institutes", institutes);
    }

    @Override
    public R deleteInstitute(Institute institute) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")) {
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        Integer id = institute.getId();
        Institute oldInstitute = instituteMapper.selectById(id);
        if(oldInstitute == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }
        
        instituteMapper.deleteById(id);
        
        return R.ok();
    }

    public String getUserType(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        return user.getUserType();
    }
}

