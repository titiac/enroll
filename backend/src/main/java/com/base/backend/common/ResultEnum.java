package com.base.backend.common;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(200, "成功"),
    ERROR(400, "失败"),
    ILLEGAL_OPERATE(400, "非法操作"),
    USERNAME_NULL_ERROR(400, "用户名不能为空"),
    PASSWORD_NULL_ERROR(400, "密码或确认密码不能为空"),
    USERNAME_TOO_LONG(400, "用户名不能超过100"),
    PASSWORD_TOO_LONG(400, "密码或确认密码不能超过100"),
    PASSWORD_DIFFERENT(400, "两次密码不一致"),
    USERNAME_IS_EXIST(400, "用户名已存在"),
    USERNAME_IS_FROZEN(400, "您的账号已被冻结"),
    INSTITUTE_IS_EXIST(400, "学院名称已存在"),
    INSTITUTE_NOT_FIND(400, "未找到对应的学院"),
    PARAMETER_LACK(400, "有参数为空或缺少"),
    CLASS_IS_EXIST(400, "班级已存在"),
    CLASS_NOT_FOUND(400, "班级未找到"),
    STUDENT_NOT_FOUND(400, "对应学生未找到"),
    TEACHER_NOT_FOUND(400, "对应教师未找到"),
    CLASSROOM_NOT_FOUND(400, "对应教室未找到"),
    CLASSROOM_IS_EXIST(400, "教室已存在"),
    CLASSROOM_IS_USED(400, "该教室在该时间段已被占用"),
    TEACHER_IS_BUSY(400, "该教师该时间段忙"),
    ENROLL_IS_EXIST(400, "选课结果已存在"),
    CLASSROOM_IS_REPEAT(400, "教室已重复")
    ;


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}