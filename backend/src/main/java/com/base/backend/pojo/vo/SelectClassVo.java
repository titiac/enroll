package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 查询班级所需参数
 * @author: zhw
 * @created: 2022/12/02 11:38
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectClassVo {
    private String name;            /* 班级名称 */
    private Integer grade;          /* 年级 */
    private String instituteName;   /* 学院名称 */
}

