package com.base.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.backend.pojo.Student;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
