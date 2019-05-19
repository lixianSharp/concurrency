package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.*;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:28 2019/5/8
 * @Description:
 */
public class CyclicBarrierExample3 {

    private static CyclicBarrier barrier = new CyclicBarrier(5,() -> {
        //在线程到达这个屏障的时候，优先执行这里面的runnable，也就是这里的代码
        LoggerUtil.logger.info("callback is running");
    });//5表示屏障的线程数目是5个

    public static void main(String[] args) throws Exception{
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(()->{
                try {
                    race(threadNum);
                } catch (Exception e) {
                    LoggerUtil.logger.error("exception",e);

                }
            });
        }
        executorService.shutdown();//关闭线程池
    }

    private static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        LoggerUtil.logger.info("{} is ready",threadNum);
        barrier.await();
        LoggerUtil.logger.info("{} continue",threadNum);
    }
}
