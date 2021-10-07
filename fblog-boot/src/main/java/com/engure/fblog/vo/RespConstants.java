package com.engure.fblog.vo;

/*

返回类型

 */

public enum RespConstants {

    //通用
    SUCCESS(200, "OK"),
    SERVER_ERROR(50000, "服务端出错"),

    //user
    FAIL_TO_ADD_USER(500101, "添加用户失败!"),
    FAIL_TO_UPDATE_USER(500102, "修改用户失败!"),
    FAIL_TO_DELETE_USER(500103, "删除用户失败!"),

    //login，register
    LOGIN_ERROR(500200, "登陆失败，账号或密码错误"),
    REGISTER_USERNAME_EXISTS(500201, "注册失败，账号已存在"),
    REGISTER_ERROR(500202, "注册失败"),

    //word
    FAIL_TO_ADD_WORD(500300, "添加词条失败！"),
    FAIL_TO_UPDATE_WORD(500301, "更新词条失败"),
    FAIL_TO_DELETE_WORD(500302, "删除词条失败"),

    ;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    RespConstants(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;
}
