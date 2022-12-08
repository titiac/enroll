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
 * @description: 学生表
 * @author: zhw
 * @created: 2022/11/25 22:06
 */

@TableName("public.student")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 /* 主键自增 */
    private String name;                /* 姓名 */
    private String sex;                 /* 性别 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;              /* 出生日期 */
    private String province;            /* 省份 */
    private String city;                /* 城市 */
    private Integer classId;            /* 所属班级id */
    private Integer instituteId;        /* 所属学院id */
    private Integer userId;             /* 账号id */
}