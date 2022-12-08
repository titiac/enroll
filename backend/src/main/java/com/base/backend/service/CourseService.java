package com.base.backend.service;

import com.base.backend.common.R;
import com.base.backend.pojo.Course;
import com.base.backend.pojo.vo.AddCourseRequestVo;
import com.base.backend.pojo.vo.AdminGetCourseRequestVo;
import com.base.backend.pojo.vo.GetAvailableTeacher;

public interface CourseService {
    R addCourse(AddCourseRequestVo addCourseRequestVo);

    R getCourse(AdminGetCourseRequestVo adminGetCourseRequestVo);

    R modifyCourse(Course course);

    R studentGetCourse();

    R teacherGetCourse();
}
