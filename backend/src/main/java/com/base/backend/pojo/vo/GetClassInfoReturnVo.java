package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 获取班级返回信息
 * @author: zhw
 * @created: 2022/11/26 15:07
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetClassInfoReturnVo {
    private Integer id;                 /* 班级id */
    private String name;                /* 班级名称 */
    private Integer grade;              /* 年级 */
    private Integer studentCount;       /* 班级学生数量 */
    private Integer instituteId;        /* 学院id */
    private String instituteName;       /* 学院名称 */
}

