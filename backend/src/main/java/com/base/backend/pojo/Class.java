package com.base.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 班级表
 * @author: zhw
 * @created: 2022/11/25 14:04
 */
@TableName("public.class")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Class {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;             /* 主键 */
    private String name;            /* 班级名称 */
    private Integer grade;          /* 年级 比如 2020级 就是 2020 */
    private Integer instituteId;    /* 学院编号 */
}

