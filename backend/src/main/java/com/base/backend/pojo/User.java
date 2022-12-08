package com.base.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("public.user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;             // 自增
    private String username;        // 用户名
    private String password;        // 密码
    private String photo;           // 头像
    private String userType;        // 用户类型
    private Integer status;         // 状态 1正常, 0冻结   
}
