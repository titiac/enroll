package com.base.backend.service;

import com.base.backend.common.R;
import com.base.backend.pojo.vo.AddTeacherVo;
import com.base.backend.pojo.vo.GetAvailableTeacher;
import com.base.backend.pojo.vo.ModifyTeacherVo;
import com.base.backend.pojo.vo.SelectTeacherVo;

public interface TeacherService {
    R addTeacher(AddTeacherVo addTeacherVo);

    R getTeachers(SelectTeacherVo selectTeacherVo);

    R modifyTeacher(ModifyTeacherVo modifyTeacherVo);

    R getAvailableTeacher(GetAvailableTeacher getAvailableTeacher);
}
