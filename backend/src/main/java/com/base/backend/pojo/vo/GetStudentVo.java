package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: select
 * @description: 获取学生所需参数
 * @author: zhw
 * @created: 2022/12/02 13:58
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetStudentVo {
    private String name;
    private String sex;
    private String province;
    private String city;
    private String className;
    private String instituteName;
}