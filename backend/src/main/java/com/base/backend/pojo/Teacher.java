package com.base.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: select
 * @description: 教师信息表
 * @author: zhw
 * @created: 2022/11/25 10:32
 */

@TableName("public.teacher")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teacher {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;             /* 主键 */
    private String name;            /* 教师姓名 */
    private String sex;             /* 性别 */
    private String degree;          /* 学位 */
    private String title;           /* 职称 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date hireDate;          /* 聘用日期 */
    private Integer instituteId;    /* 学院编号 */
    private Integer userId;         /* 对应的用户编号 */
}

