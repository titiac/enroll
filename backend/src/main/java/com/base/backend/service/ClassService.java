package com.base.backend.service;

import com.base.backend.common.R;
import com.base.backend.pojo.Class;
import com.base.backend.pojo.vo.AddClassVo;
import com.base.backend.pojo.vo.SelectClassVo;

public interface ClassService {
    R addClass(AddClassVo addClassVo);

    R getAllClass(SelectClassVo selectClassVo);

    R modifyClassInfo(Class newClass);
}
