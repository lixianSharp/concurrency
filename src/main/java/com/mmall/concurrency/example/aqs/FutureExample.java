package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:27 2019/5/10
 * @Description:
 */
public class FutureExample {

    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            LoggerUtil.logger.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws Exception{
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        LoggerUtil.logger.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        LoggerUtil.logger.info("result:{}",result);
    }

}
