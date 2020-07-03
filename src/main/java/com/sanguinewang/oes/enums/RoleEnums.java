package com.sanguinewang.oes.enums;

/**
 * 对于用户权限类型的枚举
 */
public enum RoleEnums {
    STUDENT(1, "学生"),
    TEACHER(2, "教师"),
    ADMINISTRATOR(0, "管理员");

    private Integer code;

    private String message;

    RoleEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
