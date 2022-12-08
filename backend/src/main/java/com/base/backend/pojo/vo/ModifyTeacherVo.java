package com.base.backend.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: select
 * @description: 修改教师所需要的参数
 * @author: zhw
 * @created: 2022/11/27 14:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyTeacherVo {
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
}

