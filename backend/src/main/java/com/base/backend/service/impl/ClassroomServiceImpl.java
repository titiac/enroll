package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.ClassroomMapper;
import com.base.backend.mapper.CourseMapper;
import com.base.backend.pojo.Classroom;
import com.base.backend.pojo.Course;
import com.base.backend.pojo.User;
import com.base.backend.pojo.vo.AddClassroomVo;
import com.base.backend.pojo.vo.GetAvailableClassroom;
import com.base.backend.service.ClassroomService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: select
 * @description: 与教室有关的服务
 * @author: zhw
 * @created: 2022/11/27 19:34
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {
    
    @Autowired
    public ClassroomMapper classroomMapper;
    
    @Autowired
    public CourseMapper courseMapper;
    
    @Override
    public R addClassroom(AddClassroomVo addClassroomVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        String building = addClassroomVo.getBuilding();    /* 教学楼 */
        String name = addClassroomVo.getName();        /* 教室名称 例如101 */
        String type = addClassroomVo.getType();        /* 教室类型     普通教室, 梯形教室, 多媒体教室 */
        Integer capacity = addClassroomVo.getCapacity();   /* 教室容量 */

        if(building == null || name == null || type == null || capacity == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        wrapper.eq("building", building).and(i -> i.eq("name", name));
        Classroom oldClassroom = classroomMapper.selectOne(wrapper);
        if(oldClassroom != null){
            return R.error().resultEnum(ResultEnum.CLASSROOM_IS_EXIST);
        }

        Classroom newClassroom = new Classroom(null, building, name, type, capacity);
        
        classroomMapper.insert(newClassroom);
        
        return R.ok();
    }

    @Override
    public R getClassroom(Classroom classroom) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        Integer id = classroom.getId();                 /* 教室编号 */
        String building = classroom.getBuilding();      /* 教学楼 */
        String name = classroom.getName();              /* 教室名称 例如101 */
        String type = classroom.getType();              /* 教室类型     普通教室, 梯形教室, 多媒体教室 */
        Integer capacity = classroom.getCapacity();     /* 教室容量 */
        
        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        if(id != null) {
            wrapper.eq("id", id);
        }
        
        if(building != null && building.length() > 0) {
            wrapper.and(i -> i.like("building", building));
        }
        
        if(name != null && name.length() > 0) {
            wrapper.and(i -> i.eq("name", name));
        }
        
        if(type != null && type.length() > 0) {
            wrapper.and(i -> i.like("type", type));
        }
        
        if(capacity != null) {
            wrapper.and(i -> i.ge("capacity", capacity));
        }
        
        List<Classroom> classrooms = classroomMapper.selectList(wrapper);

        return R.ok().data("classrooms", classrooms);
    }

    @Override 
    public R modifyClassroom(Classroom classroom) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")){
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        Integer id = classroom.getId();
        String building = classroom.getBuilding();      /* 教学楼 */
        String name = classroom.getName();              /* 教室名称 例如101 */
        String type = classroom.getType();              /* 教室类型     普通教室, 梯形教室, 多媒体教室 */
        Integer capacity = classroom.getCapacity();     /* 教室容量 */

        if(id == null || building == null || name == null || type == null || capacity == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }
        
        Classroom oldClassroom = classroomMapper.selectById(id);
        if(oldClassroom == null) {
            return R.error().resultEnum(ResultEnum.CLASSROOM_NOT_FOUND);
        }

        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        wrapper.eq("building", building)
                .and(i -> i.eq("name", name))
                .and(i -> i.ne("id", id));
        Classroom exitRoom = classroomMapper.selectOne(wrapper);
        if(exitRoom != null) {
            return R.error().resultEnum(ResultEnum.CLASSROOM_IS_REPEAT);
        }
        
        classroomMapper.updateById(classroom);
        return R.ok();
    }

    @Override
    public R getAvailableClassrooms(GetAvailableClassroom getAvailableClassroom) {
        Integer year = getAvailableClassroom.getYear();
        String semester = getAvailableClassroom.getSemester();
        String whatDay = getAvailableClassroom.getWhatDay();
        String dayTime = getAvailableClassroom.getDayTime();
        Integer limitNumber = getAvailableClassroom.getLimitNumber();

        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        if(limitNumber != null) {
            wrapper.ge("capacity", limitNumber);
        }
        List<Classroom> allClassrooms = classroomMapper.selectList(wrapper);
        System.out.println(allClassrooms);
        QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
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
        
        List<Course> oldCourses = courseMapper.selectList(wrapper1);
        Set<Integer> usedClassroomId = new HashSet<>();
        for(Course course : oldCourses) {
            usedClassroomId.add(course.getClassroomId());
        }
        System.out.println(usedClassroomId);
        List<Classroom> availableClassrooms = new ArrayList<>();
        for(Classroom classroom : allClassrooms) {
            if(!usedClassroomId.contains(classroom.getId())){
                availableClassrooms.add(classroom);
            }
        }
        return R.ok().data("availableClassrooms", availableClassrooms);
    }

    public String getUserType(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        return user.getUserType();
    }
}