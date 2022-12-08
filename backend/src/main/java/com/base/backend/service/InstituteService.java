package com.base.backend.service;

import com.base.backend.common.R;
import com.base.backend.pojo.Institute;
import com.base.backend.pojo.vo.AddInstituteVo;

public interface InstituteService {
    R addInstitute(AddInstituteVo addInstituteVo);

    R modifyInstitute(Institute institute);

    R getInstitute(AddInstituteVo addInstituteVo);

    R deleteInstitute(Institute institute);
}
