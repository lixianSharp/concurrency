package com.mmall.concurrency;

import com.mmall.concurrency.example.count.CountExample1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class LoggerUtil {
    //日志记录器（这是SpringBoot自带的日志记录器）
    public static Logger logger = LoggerFactory.getLogger(Object.class);
}
