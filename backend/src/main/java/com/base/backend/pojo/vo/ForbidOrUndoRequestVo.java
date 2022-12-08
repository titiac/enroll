package com.base.backend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: select
 * @description: 封禁用户或解禁用户
 * @author: zhw
 * @created: 2022/11/26 21:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForbidOrUndoRequestVo {
    private Integer userId;         // 用户id
    private Integer status;         // 状态 0 为封禁状态，  1 为解禁状态
}

