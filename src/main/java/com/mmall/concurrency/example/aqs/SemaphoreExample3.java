package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:36 2019/5/7
 * @Description:
 */
public class SemaphoreExample3 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception{

        //创建一个线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try{
                    if(semaphore.tryAcquire()) {//尝试获取一个许可，如果获取到就允许执行
                        test(threadNum);
                        semaphore.release();//释放一个许可
                    }
                }catch(Exception e){
                    LoggerUtil.logger.error("exception",e);
                }
            });
        }
        LoggerUtil.logger.info("finish");
        exec.shutdown();//关闭线程池
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        LoggerUtil.logger.info("{}",threadNum);
        Thread.sleep(100);
    }
}
