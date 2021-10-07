package com.engure.fblog.controller;

import com.engure.fblog.vo.RespVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping
    public RespVO test() {
        return RespVO.success("没得问题~");
    }

}
