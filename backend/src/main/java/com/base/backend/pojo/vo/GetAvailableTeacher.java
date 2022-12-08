package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 获取当前时间段有空的老师
 * @author: zhw
 * @created: 2022/11/28 14:22
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetAvailableTeacher {
    private Integer year;
    private String semester;
    private String whatDay;
    private String dayTime;
    private Integer instituteId;
}

