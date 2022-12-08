package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 添加教室所需参数
 * @author: zhw
 * @created: 2022/11/27 19:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddClassroomVo {
    private String building;    /* 教学楼 */
    private String name;        /* 教室名称 例如101 */
    private String type;        /* 教室类型     普通教室, 梯形教室, 多媒体教室 */
    private Integer capacity;   /* 教室容量 */
}

