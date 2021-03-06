package com.engure.fsys.vo;

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

    //文件上传
    UPLOAD_FAIL(500400, "文件上传失败~"),


    //avatar
    FAIL_TO_ADD_AVATAR(500500, "头像上传失败！"),
    FAIL_TO_UPDATE_AVATAR(500501, "头像更新失败！"),
    FAIL_TO_DELETE_AVATAR(500502, "头像删除失败！"),

    //new
    FAIL_TO_ADD_NEW(500500, "新闻上传失败！"),
    FAIL_TO_UPDATE_NEW(500501, "新闻更新失败！"),
    FAIL_TO_DELETE_NEW(500502, "新闻删除失败！"),

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
