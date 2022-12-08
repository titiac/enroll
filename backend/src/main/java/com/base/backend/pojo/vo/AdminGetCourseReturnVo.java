package com.base.backend.pojo.vo;

import com.base.backend.pojo.Classroom;
import com.base.backend.pojo.Course;
import com.base.backend.pojo.Institute;
import com.base.backend.pojo.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 管理员获取课程信息返回
 * @author: zhw
 * @created: 2022/11/29 10:48
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminGetCourseReturnVo {
    private Course course;
    private Institute institute;
    private Teacher teacher;
    private Classroom classroom;
    private Integer enrollNum;
}

