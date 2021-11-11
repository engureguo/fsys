package com.engure.fsys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*

统一返回

 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespVO {
    private Integer code;
    private String msg;
    private Object data;

    public static RespVO success() {
        return success(RespConstants.SUCCESS.getMsg());
    }

    public static RespVO success(String msg) {
        return success(msg, null);
    }

    public static RespVO success(Object data) {
        return new RespVO(RespConstants.SUCCESS.getCode(), RespConstants.SUCCESS.getMsg(), data);
    }

    public static RespVO success(String msg, Object data) {
        return new RespVO(RespConstants.SUCCESS.getCode(), msg, data);
    }

    public static RespVO error(RespConstants constant, String msg) {
        return new RespVO(constant.getCode(), msg, null);
    }

    public static RespVO error(RespConstants constant) {
        return new RespVO(constant.getCode(), constant.getMsg(), null);
    }

    public static RespVO error(RespConstants constant, Object data) {
        return new RespVO(constant.getCode(), constant.getMsg(), data);
    }


}
