package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:34 2019/5/10
 * @Description: FutureTask: 可以返回执行结果
 */
public class FutureTaskExample {

    public static void main(String[] args) throws Exception{
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                LoggerUtil.logger.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(futureTask).start();
        LoggerUtil.logger.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        LoggerUtil.logger.info("result:{}",result);

    }




}