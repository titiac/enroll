package com.base.backend.service;

import com.base.backend.common.R;

public interface EnrollService {
    R studentSelect(Integer courseId);

    R studentCancel(Integer courseId);

    R getEnrollStudents(Integer courseId);
}
