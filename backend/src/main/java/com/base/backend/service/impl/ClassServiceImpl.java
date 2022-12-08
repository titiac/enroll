package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.ClassMapper;
import com.base.backend.mapper.InstituteMapper;
import com.base.backend.mapper.StudentMapper;
import com.base.backend.pojo.*;
import com.base.backend.pojo.Class;
import com.base.backend.pojo.vo.AddClassVo;
import com.base.backend.pojo.vo.GetClassInfoReturnVo;
import com.base.backend.pojo.vo.SelectClassVo;
import com.base.backend.service.ClassService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: select
 * @description: 班级相关服务
 * @author: zhw
 * @created: 2022/11/25 14:17
 */
@Service
public class ClassServiceImpl implements ClassService {
    
    @Autowired
    public ClassMapper classMapper;

    @Autowired
    public InstituteMapper instituteMapper;

    @Autowired
    public StudentMapper studentMapper;
    
    @Override
    public R addClass(AddClassVo addClassVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        String name = addClassVo.getName();
        Integer grade = addClassVo.getGrade();
        Integer instituteId = addClassVo.getInstituteId();
        if(name == null || grade == null || instituteId == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }
        
        if(name.length() == 0) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        Institute institute = instituteMapper.selectById(instituteId);
        if(institute == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }

        QueryWrapper<Class> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name)
                .and(i -> i.eq("grade", grade))
                .and(i -> i.eq("institute_id", instituteId));
        Class oldClass = classMapper.selectOne(wrapper);
        if(oldClass != null) {
            return R.ok().resultEnum(ResultEnum.CLASS_IS_EXIST);
        }
        
        classMapper.insert(new Class(null, name, grade, instituteId));
        return R.ok();
    }

    @Override
    public R getAllClass(SelectClassVo selectClassVo) {
        String name = selectClassVo.getName();            /* 班级名称 */
        Integer grade = selectClassVo.getGrade();          /* 年级 */
        String instituteName = selectClassVo.getInstituteName();   /* 学院名称 */

        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        if(instituteName != null && instituteName.length() > 0) {
            wrapper.and(i -> i.like("name", instituteName));
        }
        List<Institute> institutes = instituteMapper.selectList(wrapper);
        
        List<GetClassInfoReturnVo> classInfos = new ArrayList<>();
        
        for(Institute institute : institutes) {
            Integer instituteId = institute.getId();
            QueryWrapper<Class> wrapper1 = new QueryWrapper<>();
            if(name != null && name.length() > 0) {
                wrapper1.and(i -> i.like("name", name));
            }

            if(grade != null) {
                wrapper1.and(i -> i.eq("grade", grade));
            }
            
            wrapper1.and(i -> i.eq("institute_id", instituteId));
            
            List<Class> classes = classMapper.selectList(wrapper1);
            
            for(Class one : classes) {
                GetClassInfoReturnVo classInfo = new GetClassInfoReturnVo();
                classInfo.setId(one.getId());
                classInfo.setName(one.getName());
                classInfo.setGrade(one.getGrade());
                classInfo.setInstituteId(one.getInstituteId());
                classInfo.setInstituteName(institute.getName());

                QueryWrapper<Student> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("class_id", one.getId());
                List<Student> students = studentMapper.selectList(wrapper2);
                Integer studentCount = students.size();
                classInfo.setStudentCount(studentCount);
                classInfos.add(classInfo);
            }
        }
        
        return R.ok().data("classes", classInfos);
    }

    @Override
    public R modifyClassInfo(Class newClass) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        Integer classId = newClass.getId();
        Class oldClass = classMapper.selectById(classId);
        if(oldClass == null) {
            return R.error().resultEnum(ResultEnum.CLASS_NOT_FOUND);
        }
        
        String name = newClass.getName();
        Integer grade = newClass.getGrade();
        Integer instituteId = newClass.getInstituteId();
        if(name == null || grade == null || instituteId == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        if(name.length() == 0) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        Institute institute = instituteMapper.selectById(instituteId);
        if(institute == null) {
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }
        
        classMapper.updateById(newClass);
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