package com.base.backend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: select
 * @description: 查询老师的一些参数
 * @author: zhw
 * @created: 2022/12/02 13:34
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectTeacherVo {
    private String name;            /* 教师姓名 */
    private String sex;             /* 性别 */
    private String degree;          /* 学位 */
    private String title;           /* 职称 */
    private String instituteName;    /* 学院编号 */
}