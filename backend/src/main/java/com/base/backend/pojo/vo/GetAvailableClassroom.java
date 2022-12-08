package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 获取空闲的教室所需参数
 * @author: zhw
 * @created: 2022/11/28 15:52
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetAvailableClassroom {
    private Integer year;
    private String semester;
    private String whatDay;
    private String dayTime;
    private Integer limitNumber;
}