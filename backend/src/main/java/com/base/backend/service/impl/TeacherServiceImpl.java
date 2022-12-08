package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.CourseMapper;
import com.base.backend.mapper.InstituteMapper;
import com.base.backend.mapper.TeacherMapper;
import com.base.backend.mapper.UserMapper;
import com.base.backend.pojo.*;
import com.base.backend.pojo.vo.*;
import com.base.backend.service.TeacherService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: select
 * @description: 与教师相关的服务
 * @author: zhw
 * @created: 2022/11/27 12:24
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    
    @Autowired
    public TeacherMapper teacherMapper;

    @Autowired
    public InstituteMapper instituteMapper;
    
    @Autowired
    public UserMapper userMapper;
    
    @Autowired
    public CourseMapper courseMapper;

    @Autowired
    public PasswordEncoder passwordEncoder;
    
    @Override
    public R addTeacher(AddTeacherVo addTeacherVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        String name = addTeacherVo.getName();
        String degree = addTeacherVo.getDegree();
        String title = addTeacherVo.getTitle();
        Date birthday = addTeacherVo.getBirthday();
        Integer instituteId = addTeacherVo.getInstituteId();
        String sex = addTeacherVo.getSex();
        if(name == null || degree == null || title == null || birthday == null || instituteId == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        Institute institute = instituteMapper.selectById(instituteId);
        if(institute == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }

        /* 第一步, 先插入user表 */
        Date createTime = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
        Random r = new Random();
        String username = "T" + sdf.format(createTime) + r.nextInt(9);
        String password = passwordEncoder.encode("123456");
        String photo= "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png";
        User newUser = new User(null, username, password, photo, "teacher", 1);

        userMapper.insert(newUser);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);

        User insertUser = userMapper.selectOne(wrapper);
        if(insertUser == null) {
            return R.error();
        }
        Integer userId = insertUser.getId();

        Teacher newTeacher = new Teacher(null, name, sex, degree, title,birthday, new Date(), instituteId, userId);
        teacherMapper.insert(newTeacher);

        QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", userId);
        Teacher insertTeacher = teacherMapper.selectOne(wrapper1);
        if(insertTeacher == null) {
            return R.error();   
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("user", insertUser);
        returnMap.put("teacher", insertTeacher);
        return R.ok().data(returnMap);
    }

    @Override
    public R getTeachers(SelectTeacherVo selectTeacherVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        String name = selectTeacherVo.getName();            /* 教师姓名 */
        String sex = selectTeacherVo.getSex();             /* 性别 */
        String degree = selectTeacherVo.getDegree();          /* 学位 */
        String title = selectTeacherVo.getTitle();           /* 职称 */
        String instituteName = selectTeacherVo.getInstituteName();

        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        if(instituteName != null && instituteName.length() > 0) {
            wrapper.and(i -> i.like("name", instituteName));
        }
        
        List<Institute> institutes = instituteMapper.selectList(wrapper);

        List<GetTeacherReturnVo> getTeacherReturnVos = new ArrayList<>();
        for(Institute institute : institutes) {
            QueryWrapper<Teacher> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("institute_id", institute.getId())
                    .orderByAsc("id");
            
            if(name != null && name.length() > 0) {
                wrapper1.and(i -> i.like("name", name));
            }
            
            if(sex != null && sex.length() > 0) {
                wrapper1.and(i -> i.eq("sex", sex));
            } 
            
            if(degree != null && degree.length() > 0) {
                wrapper1.and(i -> i.eq("degree", degree));
            }
            
            if(title != null && title.length() > 0) {
                wrapper1.and(i -> i.eq("title", title));
            }
            
            List<Teacher> teachers = teacherMapper.selectList(wrapper1);
            
            for(Teacher teacher : teachers) {
                GetTeacherReturnVo getTeacherReturnVo = new GetTeacherReturnVo();
                getTeacherReturnVo.setInstitute(institute);
                getTeacherReturnVo.setTeacher(teacher);
                User te = userMapper.selectById(teacher.getUserId());
                te.setPassword("123456");
                getTeacherReturnVo.setUser(te);
                getTeacherReturnVos.add(getTeacherReturnVo);
            }
            
        }
        return R.ok().data("teachers", getTeacherReturnVos);
    }

    @Override
    public R modifyTeacher(ModifyTeacherVo modifyTeacherVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        Integer id = modifyTeacherVo.getId();
        String name = modifyTeacherVo.getName();
        String degree = modifyTeacherVo.getDegree();
        String title = modifyTeacherVo.getTitle();
        Date birthday = modifyTeacherVo.getBirthday();
        Date hireDay = modifyTeacherVo.getHireDate();
        Integer instituteId = modifyTeacherVo.getInstituteId();
        String sex = modifyTeacherVo.getSex();
        
        if(id == null || name == null || degree == null || title == null || birthday == null || instituteId == null || sex == null || hireDay == null){
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }
        
        Teacher oldTeacher = teacherMapper.selectById(id);
        if(oldTeacher == null){
            return R.error().resultEnum(ResultEnum.TEACHER_NOT_FOUND);
        }
        
        oldTeacher.setName(name);
        oldTeacher.setDegree(degree);
        oldTeacher.setTitle(title);
        oldTeacher.setInstituteId(instituteId);
        oldTeacher.setSex(sex);
        oldTeacher.setBirthday(birthday);
        oldTeacher.setHireDate(hireDay);
        
        teacherMapper.updateById(oldTeacher);
        
        return R.ok();
    }

    @Override
    public R getAvailableTeacher(GetAvailableTeacher getAvailableTeacher) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        // 获取学院的老师
        Integer instituteId = getAvailableTeacher.getInstituteId();
        if(instituteId != null) {
            wrapper.eq("institute_id", instituteId);
        }
        List<Teacher> teachers = teacherMapper.selectList(wrapper);
        System.out.println(teachers);
        
        QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
        Integer year = getAvailableTeacher.getYear();
        String semester = getAvailableTeacher.getSemester();
        String whatDay = getAvailableTeacher.getWhatDay();
        String dayTime = getAvailableTeacher.getDayTime();
        if(instituteId != null) {
            wrapper1.and(i -> i.eq("institute_id", instituteId));
        }
        
        if(year != null) {
            wrapper1.and(i -> i.eq("year", year));
        }
        
        if(semester != null && semester.length() > 0) {
            wrapper1.and(i -> i.eq("semester", semester));
        }
        
        if(whatDay != null && whatDay.length() > 0) {
            wrapper1.and(i -> i.eq("what_day", whatDay));
        }
        
        if(dayTime != null && dayTime.length() > 0) {
            wrapper1.and(i -> i.eq("day_time", dayTime));
        }
        
        List<Course> courses = courseMapper.selectList(wrapper1);
        
        Set<Integer> busyTeachersId = new HashSet<>();
        for(Course course : courses) {
            busyTeachersId.add(course.getTeacherId());
        }
        
        List<Teacher> availableTeachers = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if(!busyTeachersId.contains(teacher.getId())) {
                availableTeachers.add(teacher);   
            }
        }
        return R.ok().data("availableTeachers", availableTeachers);
    }


    public String getUserType(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        return user.getUserType();
    }
}

