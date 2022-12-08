package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 管理员获取课程请求
 * @author: zhw
 * @created: 2022/11/28 16:50
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminGetCourseRequestVo {
    private String name;                /* 课程名称 */
    private Integer credit;             /* 学分 */
    private Integer courseHour;         /* 学时 */
    private Integer year;               /* 学年 */
    private String semester;            /* 学期 */
    private String whatDay;             /* 星期几 */
    private String dayTime;             /* 每天的时间段 */
    private Integer instituteId;        /* 所属学院id */
    private Integer teacherId;          /* 任课教师id */
    private Integer classroomId;        /* 教室id */
    private Integer limitNumber;        /* 限选人数 */
    private Integer status;             /* 是否结束选课 0为 结束 1为开放 */
}