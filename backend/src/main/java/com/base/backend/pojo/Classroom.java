package com.base.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 教室表
 * @author: zhw
 * @created: 2022/11/27 19:08
 */

@TableName("public.classroom")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Classroom {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;         /* 教室编号 */
    private String building;    /* 教学楼 */
    private String name;        /* 教室名称 例如101 */
    private String type;        /* 教室类型     普通教室, 梯形教室, 多媒体教室 */
    private Integer capacity;   /* 教室容量 */
}