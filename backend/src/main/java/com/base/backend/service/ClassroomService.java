package com.base.backend.service;

import com.base.backend.common.R;
import com.base.backend.pojo.Classroom;
import com.base.backend.pojo.vo.AddClassroomVo;
import com.base.backend.pojo.vo.GetAvailableClassroom;
import com.base.backend.pojo.vo.GetAvailableTeacher;

public interface ClassroomService {
    R addClassroom(AddClassroomVo addClassroomVo);

    R getClassroom(Classroom classroom);

    R modifyClassroom(Classroom classroom);

    R getAvailableClassrooms(GetAvailableClassroom getAvailableClassroom);
}
