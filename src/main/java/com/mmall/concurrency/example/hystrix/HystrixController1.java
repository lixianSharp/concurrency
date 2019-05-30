package com.mmall.concurrency.example.hystrix;

import com.mmall.concurrency.LoggerUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/hystrix1")
@DefaultProperties(defaultFallback = "defaultFail")
public class HystrixController1 {

    @HystrixCommand(fallbackMethod = "fail1")
    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
        throw new RuntimeException();
    }

    private String fail1() {
        LoggerUtil.logger.warn("fail1");
        return "fail1";
    }

    @HystrixCommand(fallbackMethod = "fail2")
    @RequestMapping("/test2")
    @ResponseBody
    public String test2() {
        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fail3")
    private String fail2() {
        LoggerUtil.logger.warn("fail2");
        throw new RuntimeException();
    }

    @HystrixCommand
    private String fail3() {
        LoggerUtil.logger.warn("fail3");
        throw new RuntimeException();
    }

    private String defaultFail() {
        LoggerUtil.logger.warn("default fail");
        return "default fail";
    }
}
