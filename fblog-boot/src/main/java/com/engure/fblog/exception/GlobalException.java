package com.engure.fblog.exception;

import com.engure.fblog.vo.RespConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局异常。
 * 使用：throw new GlobalException(RespConstants.XYZ)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private RespConstants constants;
}
