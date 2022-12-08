package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.ClassMapper;
import com.base.backend.mapper.EnrollMapper;
import com.base.backend.mapper.StudentMapper;
import com.base.backend.pojo.Class;
import com.base.backend.pojo.Course;
import com.base.backend.pojo.Enroll;
import com.base.backend.pojo.Student;
import com.base.backend.pojo.User;
import com.base.backend.service.EnrollService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: select
 * @description: 选课相关
 * @author: zhw
 * @created: 2022/11/29 20:03
 */
@Service
public class EnrollServiceImpl implements EnrollService {

    @Autowired
    public EnrollMapper enrollMapper;
    
    @Autowired
    public StudentMapper studentMapper;
    
    @Autowired
    public ClassMapper classMapper;
    
    @Override
    public R studentSelect(Integer courseId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        if(!Objects.equals(user.getUserType(), "student")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        Student student = studentMapper.selectOne(wrapper);
        if(student == null) {
            return R.error().resultEnum(ResultEnum.STUDENT_NOT_FOUND);
        }

        QueryWrapper<Enroll> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId)
                .and(i -> i.eq("student_id", student.getId()));
        Enroll enroll = enrollMapper.selectOne(wrapper1);
        if(enroll != null) {
            return R.error().resultEnum(ResultEnum.ENROLL_IS_EXIST);
        }
        
        enrollMapper.insert(new Enroll(null, student.getId(), courseId));
        
        return R.ok();
    }

    @Override
    public R studentCancel(Integer courseId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        if(!Objects.equals(user.getUserType(), "student")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        Student student = studentMapper.selectOne(wrapper);
        if(student == null) {
            return R.error().resultEnum(ResultEnum.STUDENT_NOT_FOUND);
        }

        QueryWrapper<Enroll> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId)
                .and(i -> i.eq("student_id", student.getId()));
        enrollMapper.delete(wrapper1);
        
        return R.ok();
    }

    @Override
    public R getEnrollStudents(Integer courseId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        if(!Objects.equals(user.getUserType(), "superAdmin") && !Objects.equals(user.getUserType(), "teacher")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        QueryWrapper<Enroll> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<Enroll> enrolls = enrollMapper.selectList(wrapper);
        
        List<Map<String, Object>> returnList = new ArrayList<>();
        System.out.println(enrolls);
        for(Enroll enroll : enrolls) {
            Student student = studentMapper.selectById(enroll.getStudentId());
            Map<String, Object> map = new HashMap<>();
            map.put("student", student);

            Class studentClass = classMapper.selectById(student.getClassId());
            map.put("studentClass", studentClass);
            returnList.add(map);
        }
        
        return R.ok().data("students", returnList);
    }
}

