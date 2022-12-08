package com.base.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.backend.common.R;
import com.base.backend.common.ResultEnum;
import com.base.backend.mapper.*;
import com.base.backend.pojo.*;
import com.base.backend.pojo.Class;
import com.base.backend.pojo.vo.AddCourseRequestVo;
import com.base.backend.pojo.vo.AdminGetCourseRequestVo;
import com.base.backend.pojo.vo.AdminGetCourseReturnVo;
import com.base.backend.service.CourseService;
import com.base.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: select
 * @description: 课程相关的服务
 * @author: zhw
 * @created: 2022/11/28 08:41
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    public CourseMapper courseMapper;

    @Autowired
    public InstituteMapper instituteMapper;
    
    @Autowired
    public TeacherMapper teacherMapper;
    
    @Autowired
    public ClassroomMapper classroomMapper;
    
    @Autowired
    public StudentMapper studentMapper;
    
    @Autowired
    public ClassMapper classMapper;
    
    @Autowired
    public EnrollMapper enrollMapper;
    
    @Override
    public R addCourse(AddCourseRequestVo addCourseRequestVo) {
        String userType = getUserType();
        if(!Objects.equals(userType, "superAdmin")) {
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        
        String courseName = addCourseRequestVo.getCourseName();              /* 课程名 */
        Integer credit = addCourseRequestVo.getCredit();                 /* 学分 */
        Integer courseHour = addCourseRequestVo.getCourseHour();             /* 课时 */
        Integer year = addCourseRequestVo.getYear();                   /* 学年 */
        String semester = addCourseRequestVo.getSemester();                /* 学期 */
        String whatDay = addCourseRequestVo.getWhatDay();                 /* 星期 */
        String dayTime = addCourseRequestVo.getDayTime();                 /* 当天时间段 */
        Integer instituteId = addCourseRequestVo.getInstituteId();            /* 所属学院id */
        Integer teacherId = addCourseRequestVo.getTeacherId();              /* 任课教师id */
        Integer classroomId = addCourseRequestVo.getClassroomId();            /* 教室id */
        Integer limitNumber = addCourseRequestVo.getLimitNumber();            /* 限选人数 */    
        if(courseName == null || courseHour == null || credit == null || year == null || semester == null
            || whatDay == null || dayTime == null || instituteId == null || teacherId == null || classroomId == null || limitNumber == null) {
            return R.error().resultEnum(ResultEnum.PARAMETER_LACK);
        }

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("day_time", dayTime)
                .and(i -> i.eq("year", year))
                .and(i -> i.eq("semester", semester))
                .and(i -> i.eq("what_day", whatDay))
                .and(i -> i.eq("classroom_id", classroomId));

        List<Course> courseClassroom = courseMapper.selectList(wrapper);
        if(!courseClassroom.isEmpty()) {
            return R.error().resultEnum(ResultEnum.CLASSROOM_IS_USED);
        }

        QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("day_time", dayTime)
                .and(i -> i.eq("year", year))
                .and(i -> i.eq("semester", semester))
                .and(i -> i.eq("what_day", whatDay))
                .and(i -> i.eq("teacher_id", teacherId));
        List<Course> courseTeacher = courseMapper.selectList(wrapper1);
        if(!courseTeacher.isEmpty()) {
            return R.error().resultEnum(ResultEnum.TEACHER_IS_BUSY);
        }
        
        Course newCourse = new Course(
                null, 
                courseName,
                credit,
                courseHour,
                year,
                semester,
                whatDay,
                dayTime,
                instituteId,    
                teacherId,
                classroomId,
                limitNumber,
                1
        );
        
        courseMapper.insert(newCourse);
        return R.ok();
    }

    @Override
    public R getCourse(AdminGetCourseRequestVo adminGetCourseRequestVo) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        String name = adminGetCourseRequestVo.getName();                /* 课程名称 */
        if(name != null) {
            wrapper.and(i -> i.like("name", name));
        }
        
        Integer credit = adminGetCourseRequestVo.getCredit();             /* 学分 */
        if(credit != null) {
            wrapper.and(i -> i.eq("credit", credit));
        }
        
        Integer courseHour = adminGetCourseRequestVo.getCourseHour();         /* 学时 */
        if(courseHour != null) {
            wrapper.and(i -> i.eq("course_hour", courseHour));
        }
        
        Integer year = adminGetCourseRequestVo.getYear();               /* 学年 */
        if(year != null) {
            wrapper.and(i -> i.eq("year", year));
        }
        
        String semester = adminGetCourseRequestVo.getSemester();            /* 学期 */
        if(semester != null && semester.length() != 0) {
            wrapper.and(i -> i.eq("semester", semester));
        }
        
        String whatDay = adminGetCourseRequestVo.getWhatDay();             /* 星期几 */
        if(whatDay != null && whatDay.length() != 0) {
            wrapper.and(i -> i.eq("what_day", whatDay));
        }
        
        String dayTime = adminGetCourseRequestVo.getDayTime();             /* 每天的时间段 */
        if(dayTime != null && dayTime.length() != 0) {
            wrapper.and(i -> i.eq("day_time", dayTime));
        }
        
        Integer instituteId = adminGetCourseRequestVo.getInstituteId();        /* 所属学院id */
        if(instituteId != null) {
            wrapper.and(i -> i.eq("institute_id", instituteId));
        }
        
        Integer teacherId = adminGetCourseRequestVo.getTeacherId();          /* 任课教师id */
        if(teacherId != null) {
            wrapper.and(i -> i.eq("teacher_id", teacherId));
        }
        
        Integer classroomId = adminGetCourseRequestVo.getClassroomId();        /* 教室id */
        if(classroomId != null) {
            wrapper.and(i -> i.eq("classroom_id", classroomId));
        }
        
        Integer limitNumber = adminGetCourseRequestVo.getLimitNumber();        /* 限选人数 */
        if(limitNumber != null) {
            wrapper.and(i -> i.eq("limit_number", limitNumber));
        }
        
        Integer status = adminGetCourseRequestVo.getStatus();
        if(status != null) {
            wrapper.and(i -> i.eq("status", status));
        }
        
        List<Course> courses = courseMapper.selectList(wrapper);
        List<AdminGetCourseReturnVo> adminGetCourseReturnVos = new ArrayList<>();
        
        for(Course course : courses){
            AdminGetCourseReturnVo adminGetCourseReturnVo = new AdminGetCourseReturnVo();
            adminGetCourseReturnVo.setCourse(course);
            
            Integer cInstituteId = course.getInstituteId();
            Institute institute = instituteMapper.selectById(cInstituteId);
            adminGetCourseReturnVo.setInstitute(institute);
            
            Integer cTeacherId = course.getTeacherId();
            Teacher teacher = teacherMapper.selectById(cTeacherId);
            adminGetCourseReturnVo.setTeacher(teacher);
            
            Integer cClassroomId = course.getClassroomId();
            Classroom classroom = classroomMapper.selectById(cClassroomId);
            adminGetCourseReturnVo.setClassroom(classroom);

            QueryWrapper<Enroll> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("course_id", course.getId());
            Integer count = Math.toIntExact(enrollMapper.selectCount(wrapper1));
            adminGetCourseReturnVo.setEnrollNum(count);
            
            adminGetCourseReturnVos.add(adminGetCourseReturnVo);
        }
        
        return R.ok().data("Courses", adminGetCourseReturnVos);
    }

    // TODO: 有空再加约束吧
    @Override
    public R modifyCourse(Course course) {
        System.out.println(course);
        courseMapper.updateById(course);
        return R.ok();
    }
    
    @Override
    public R studentGetCourse() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        
        if(!Objects.equals(user.getUserType(), "student")) {
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }
        Integer userId = user.getId();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Student student = studentMapper.selectOne(wrapper);
        if(student == null) {
            return R.error().resultEnum(ResultEnum.STUDENT_NOT_FOUND);
        }
        
        Integer classId = student.getClassId();
        Class studentClass = classMapper.selectById(classId);
        if(studentClass == null) {
            return R.error().resultEnum(ResultEnum.CLASS_NOT_FOUND);
        }

        Integer instituteId = studentClass.getInstituteId();
        Institute institute = instituteMapper.selectById(instituteId);
        if(institute == null){
            return R.error().resultEnum(ResultEnum.INSTITUTE_NOT_FIND);
        }

        QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("institute_id", instituteId)
                .and(i -> i.eq("status", 1));
        List<Course> courses = courseMapper.selectList(wrapper1);
        
        List<Map<String, Object>> returnList = new ArrayList<>();
        for(Course course : courses) {
            Integer courseId = course.getId();
            QueryWrapper<Enroll> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("course_id", courseId);
            Long count = enrollMapper.selectCount(wrapper2);
            Map<String, Object> map = new HashMap<>();
            map.put("course", course);
            map.put("enrollNum", count);
            map.put("institute", institute);
            
            Classroom classroom = classroomMapper.selectById(course.getClassroomId());
            map.put("classroom", classroom);
            
            Teacher teacher = teacherMapper.selectById(course.getTeacherId());
            map.put("teacher", teacher);
            
            wrapper2.eq("student_id", student.getId());
            Enroll enroll = enrollMapper.selectOne(wrapper2);
            if(enroll != null) {
                map.put("isSelect", 1);
            } else {
                map.put("isSelect", 0);
            }
            
            returnList.add(map);
        }
        return R.ok().data("courses", returnList);
    }

    @Override
    public R teacherGetCourse() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if(!Objects.equals(user.getUserType(), "teacher")) {
            return R.error().resultEnum(ResultEnum.ILLEGAL_OPERATE);
        }

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        Teacher teacher = teacherMapper.selectOne(wrapper);
        
        Institute institute = instituteMapper.selectById(teacher.getInstituteId());
        
        QueryWrapper<Course> wrapper1 = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacher.getId());
        List<Course> courses = courseMapper.selectList(wrapper1);
        
        List<Map<String, Object>> returnList = new ArrayList<>();
        for(Course course : courses) {
            Map<String, Object> map = new HashMap<>();
            map.put("course", course);
            
            Classroom classroom = classroomMapper.selectById(course.getClassroomId());
            map.put("classroom", classroom);
            map.put("institute", institute);
            
            returnList.add(map);
        }
        
        return R.ok().data("courses", returnList);
    }

    public String getUserType(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        return user.getUserType();
    }
}