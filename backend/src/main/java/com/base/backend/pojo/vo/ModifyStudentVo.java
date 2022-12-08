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
 * @description: 修改学生所需参数
 * @author: zhw
 * @created: 2022/11/27 13:20
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyStudentVo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;                 /* 主键自增 */
    private String name;                /* 新姓名 */
    private String sex;                 /* 新性别 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;              /* 新出生日期 */
    private String province;            /* 新省份 */
    private String city;                /* 新城市 */
    private Integer classId;            /* 所属新班级id */
    private Integer instituteId;        /* 所属新学院id */
}

