package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:27 2019/5/10
 * @Description: Future表示一个任务的生命周期，并提供了响应的方法来判断是否已经完成或取消，以及获取
 *      任务的结果和取消任务等；在Future规范中包含的隐含意义是，任务的什么周期只能前进，不能后退，就
 *          像ExecutorService的生命周期一样，当某个任务完成后，它就永远停留在“完成”状态上。
 *       ExecutorService中的所有submit方法都将返回一个Future，从而将一个Runnable或Callable提交给
 *          Executor，并得到一个Future用来获得任务的执行结果或者取消任务。还可以显示地指定Runnable或
 *          Callable实例化一个FutureTask。(由于FutureTask实现了Run那边了，因此可以将它提交给Executor来
 *          执行或者直接调用他的run方法。)
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
