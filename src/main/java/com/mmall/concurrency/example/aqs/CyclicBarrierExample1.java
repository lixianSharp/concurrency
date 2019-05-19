package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:28 2019/5/8
 * @Description:
 */
public class CyclicBarrierExample1 {

    private static CyclicBarrier barrier = new CyclicBarrier(5);//5表示屏障的线程数目是5个

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
