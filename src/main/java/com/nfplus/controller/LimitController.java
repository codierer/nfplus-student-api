package com.nfplus.controller;

import com.nfplus.annotation.RequestLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zac
 * @description: 测试springboot 环境搭建
 * @data 2020 2020/3/10 17:40
 */

@RestController
public class LimitController {

    @RequestMapping("/test")
    @RequestLimit(perSecond = 1.0, timeOut = 1, timeOutUnit = TimeUnit.SECONDS)
    public String hello(){
        return "Hello springboot";
    }

}
