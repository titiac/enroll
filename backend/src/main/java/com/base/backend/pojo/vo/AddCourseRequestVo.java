package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 添加课程所需的参数
 * @author: zhw
 * @created: 2022/11/28 08:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCourseRequestVo {
    private String courseName;              /* 课程名 */          
    private Integer credit;                 /* 学分 */
    private Integer courseHour;             /* 课时 */
    private Integer year;                   /* 学年 */
    private String semester;                /* 学期 */
    private String whatDay;                 /* 星期 */
    private String dayTime;                 /* 当天时间段 */
    private Integer instituteId;            /* 所属学院id */
    private Integer teacherId;              /* 任课教师id */
    private Integer classroomId;            /* 教室id */
    private Integer limitNumber;            /* 限选人数 */
}