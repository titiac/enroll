package com.base.backend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: select
 * @description: 添加学生所需参数
 * @author: zhw
 * @created: 2022/11/25 22:44
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddStudentVo {
    private String name;
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;
    private String province;
    private String city;
    private Integer classId;
    private Integer instituteId;
}