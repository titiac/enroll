package com.base.backend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: select
 * @description: 添加教师所需的参数
 * @author: zhw
 * @created: 2022/11/27 12:28
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddTeacherVo {
    private String name;            /* 教师姓名 */
    private String degree;          /* 学位 */
    private String title;           /* 职称 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;
    private String sex;
    private Integer instituteId;    /* 学院编号 */
}

