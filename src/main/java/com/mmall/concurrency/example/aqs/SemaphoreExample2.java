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
public class SemaphoreExample2 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception{

        //创建一个线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try{
                    semaphore.acquire(3);//获取多个许可
                    test(threadNum);
                    semaphore.release(3);//释放多个许可
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
