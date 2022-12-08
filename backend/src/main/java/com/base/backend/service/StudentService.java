package com.base.backend.service;

import com.base.backend.common.R;
import com.base.backend.pojo.Student;
import com.base.backend.pojo.vo.AddStudentVo;
import com.base.backend.pojo.vo.GetStudentVo;
import com.base.backend.pojo.vo.ModifyStudentVo;

public interface StudentService {
    R addStudent(AddStudentVo addStudentVo);

    R getAllStudent(GetStudentVo getStudentVo);

    R modifyStudent(ModifyStudentVo modifyStudentVo);
}
