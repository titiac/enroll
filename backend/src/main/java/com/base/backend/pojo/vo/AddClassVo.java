package com.base.backend.pojo.vo;

import com.base.backend.pojo.Institute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 添加班级所需参数
 * @author: zhw
 * @created: 2022/11/25 14:26
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddClassVo {
    private String name;            /* 班级名称 */
    private Integer grade;          /* 年级 */
    private Integer instituteId;    /* 学院编号 */
}

