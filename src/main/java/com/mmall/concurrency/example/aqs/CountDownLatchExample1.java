package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:36 2019/5/7
 * @Description: CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。
 *                        每当一个线程完成了自己的任务后，计数器的值就会减1。
 *                        当计数器值到达0时，它表示所有的线程已经完成了任务，
 *                        然后在闭锁上等待的线程就可以恢复执行任务。
 */
public class CountDownLatchExample1 {
    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception{

        //创建一个线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        //闭锁
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try{
                    test(threadNum);
                }catch(Exception e){
                    LoggerUtil.logger.error("exception",e);
                }finally{
                    countDownLatch.countDown();//让计数器递减，每次减1
                }
            });
        }
        //await等待直到计数器的值为零时，闭锁关闭，才允许其它线程通过。
        countDownLatch.await();
        LoggerUtil.logger.info("finish");
        exec.shutdown();//关闭线程池
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        LoggerUtil.logger.info("{}",threadNum);
        Thread.sleep(100);
    }
}
