package com.base.backend.pojo.vo;

import com.base.backend.pojo.Institute;
import com.base.backend.pojo.Teacher;
import com.base.backend.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 获取教师信息的返回类
 * @author: zhw
 * @created: 2022/11/27 13:55
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTeacherReturnVo {
    private Teacher teacher;
    private User user;
    private Institute institute;
}

