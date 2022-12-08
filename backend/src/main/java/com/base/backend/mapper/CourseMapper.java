package com.base.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.backend.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
