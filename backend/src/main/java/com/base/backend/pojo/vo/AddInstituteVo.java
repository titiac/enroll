package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 添加学院所需的参数
 * @author: zhw
 * @created: 2022/11/25 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInstituteVo {
    private String name;        /* 学院名称 */
    private String address;     /* 学院地址 */
}

