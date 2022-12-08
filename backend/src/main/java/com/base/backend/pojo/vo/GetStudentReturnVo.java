package com.base.backend.pojo.vo;

import com.base.backend.pojo.Class;
import com.base.backend.pojo.Institute;
import com.base.backend.pojo.Student;
import com.base.backend.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 获取学生类返回类
 * @author: zhw
 * @created: 2022/11/26 22:32
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetStudentReturnVo {
    private Student student;
    private User user;
    private Institute institute;
    private Class studentClass;
}

