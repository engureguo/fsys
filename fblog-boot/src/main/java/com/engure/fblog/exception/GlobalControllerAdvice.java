package com.engure.fblog.exception;


import com.engure.fblog.vo.RespConstants;
import com.engure.fblog.vo.RespVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

//    @ExceptionHandler(value = Exception.class)
//    public RespVO handleServerException(Exception e) {
//        return RespVO.error(RespConstants.SERVER_ERROR, e.getMessage());
//    }

    @ExceptionHandler(value = GlobalException.class)
    public RespVO handleGlobalException(GlobalException exp) {
        return RespVO.error(exp.getConstants());
    }

}
