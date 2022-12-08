package com.base.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 学生选课, 如果要添加录入成绩选项可以在此表扩充
 * @author: zhw
 * @created: 2022/11/29 15:48
 */

@TableName("public.enroll")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Enroll {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 /* 主键自增 */
    private Integer studentId;          /* 学生id */
    private Integer courseId;           /* 课程id */
}

