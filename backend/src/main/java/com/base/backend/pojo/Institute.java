package com.base.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 学院实体
 * @author: zhw
 * @created: 2022/11/25 10:51
 */
@TableName("public.institute")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Institute {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;         /* 编号 */
    private String name;        /* 学院名称 */
    private String address;     /* 学院地址 */
}

