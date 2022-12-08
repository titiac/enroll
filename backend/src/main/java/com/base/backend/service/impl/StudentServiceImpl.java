package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.ClassMapper;
import com.base.backend.mapper.InstituteMapper;
import com.base.backend.mapper.StudentMapper;
import com.base.backend.mapper.UserMapper;
import com.base.backend.pojo.*;
import com.base.backend.pojo.Class;
import com.base.backend.pojo.vo.AddStudentVo;
import com.base.backend.pojo.vo.GetStudentReturnVo;
import com.base.backend.pojo.vo.GetStudentVo;
import com.base.backend.pojo.vo.ModifyStudentVo;
import com.base.backend.service.StudentService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: select
 * @description: 学生的相关服务
 * @author: zhw
 * @created: 2022/11/25 22:36
 */
@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    public ClassMapper classMapper;
    
    @Autowired
    public StudentMapper studentMapper;
    
    @Autowired
    public InstituteMapper instituteMapper;
    
    @Autowired
    public UserMapper userMapper;
    
    @Autowired
    public PasswordEncoder passwordEncoder;
    
    @Override
    public R addStudent(AddStudentVo addStudentVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        String name = addStudentVo.getName();
        String sex = addStudentVo.getSex();
        Date birthday = addStudentVo.getBirthday();
        String province = addStudentVo.getProvince();
        String city = addStudentVo.getCity();
        Integer classId = addStudentVo.getClassId();
        Integer instituteId = addStudentVo.getInstituteId();
        
        if(name == null || sex == null || birthday == null || province == null 
                || city == null || classId == null || instituteId == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        Institute getInstitute = instituteMapper.selectById(instituteId);
        if(getInstitute == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }
        
        Class getClass = classMapper.selectById(classId);
        if(getClass == null) {
            return R.error().resultEnum(ResultEnum.CLASS_NOT_FOUND);
        }
        
        /* 第一步, 先插入user表 */
        Date createTime = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
        Random r = new Random();
        String username = "S" + sdf.format(createTime) + r.nextInt(9);
        String password = passwordEncoder.encode("123456");
        String photo= "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png";
        User newUser = new User(null, username, password, photo, "student", 1);

        userMapper.insert(newUser);
        
        /* 第二步, 从user表取出 */
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);

        User insertUser = userMapper.selectOne(wrapper);
        if(insertUser == null) {
            return R.error();
        }
        
        /* 第三步, 插入student表  */
        Integer userId = insertUser.getId();
        Student newStudent = new Student(null, name, sex, birthday, province, city, classId, instituteId,userId);
        studentMapper.insert(newStudent);

        QueryWrapper<Student> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", userId);
        Student insertStudent = studentMapper.selectOne(wrapper1);
        if(insertStudent == null) {
            return R.error();
        }
        
        
        /* 封装返回 */
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("user", insertUser);
        returnMap.put("student", insertStudent);
        
        return R.ok().data(returnMap);
    }

    @Override
    public R getAllStudent(GetStudentVo getStudentVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        String name = getStudentVo.getName();
        String sex = getStudentVo.getSex();
        String province = getStudentVo.getProvince();
        String city = getStudentVo.getCity();
        String className = getStudentVo.getClassName();
        String instituteName = getStudentVo.getInstituteName();

        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        if(instituteName != null && instituteName.length() > 0) {
            wrapper.and(i -> i.like("name", instituteName));
        }
        List<Institute> institutes = instituteMapper.selectList(wrapper);

        List<GetStudentReturnVo> getStudentReturnVos = new ArrayList<>();
        for(Institute institute : institutes) {
            QueryWrapper<Class> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("institute_id", institute.getId())
                    .orderByAsc("id");
            
            if(className != null && className.length() > 0) {
                wrapper1.and(i -> i.like("name", className));
            }
            List<Class> classes = classMapper.selectList(wrapper1);
            
            for(Class studentClass : classes) {
                QueryWrapper<Student> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("class_id", studentClass.getId());
                if(name != null && name.length() > 0) {
                    wrapper2.and(i -> i.like("name", name));
                }
                
                if(sex != null && sex.length() > 0) {
                    wrapper2.and(i -> i.eq("sex", sex));
                }
                
                if(province != null && province.length() > 0) {
                    wrapper2.and(i -> i.like("province", province));
                }
                
                if(city != null && city.length() > 0) {
                    wrapper2.and(i -> i.eq("city", city));
                }
                List<Student> students = studentMapper.selectList(wrapper2);
                
                for(Student student : students) {
                    GetStudentReturnVo getStudentReturnVo = new GetStudentReturnVo();
                    getStudentReturnVo.setStudentClass(studentClass);
                    getStudentReturnVo.setInstitute(institute);
                    getStudentReturnVo.setStudent(student);
                    User studentUser = userMapper.selectById(student.getUserId());
                    studentUser.setPassword("123456");
                    getStudentReturnVo.setUser(studentUser);
                    getStudentReturnVos.add(getStudentReturnVo);
                }
            }
        }
        
        return R.ok().data("students", getStudentReturnVos);
    }

    @Override
    public R modifyStudent(ModifyStudentVo modifyStudentVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        Integer studentId = modifyStudentVo.getId();
        String name = modifyStudentVo.getName();                /* 姓名 */
        String sex = modifyStudentVo.getSex();                 /* 性别 */
        Date birthday = modifyStudentVo.getBirthday();              /* 出生日期 */
        String province = modifyStudentVo.getProvince();            /* 省份 */
        String city = modifyStudentVo.getCity();                /* 城市 */
        Integer classId = modifyStudentVo.getClassId(); /* 所属班级id */
        Integer instituteId = modifyStudentVo.getInstituteId();
        
        if(studentId == null || name == null || sex == null || birthday == null || 
                province == null || city == null || classId == null || instituteId == null){
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }
        
        Student oldStudent = studentMapper.selectById(studentId);
        Class newClass = classMapper.selectById(classId);
        Institute newInstituteId = instituteMapper.selectById(instituteId);
        
        if(oldStudent == null) {
            return R.error().resultEnum(ResultEnum.STUDENT_NOT_FOUND);
        }
        
        if(newInstituteId == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }
        
        if(newClass == null) {
            return R.error().resultEnum(ResultEnum.CLASS_NOT_FOUND);
        }
        
        oldStudent.setName(name);
        oldStudent.setSex(sex);
        oldStudent.setBirthday(birthday);
        oldStudent.setProvince(province);
        oldStudent.setCity(city);
        oldStudent.setClassId(classId);
        oldStudent.setInstituteId(instituteId);
        
        studentMapper.updateById(oldStudent);
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