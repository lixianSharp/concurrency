package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.annoations.ThreadSafe;
import com.mmall.concurrency.example.count.CountExample1;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@ThreadSafe
public class LockExample1 {
    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

    //日志记录器（这是SpringBoot自带的日志记录器）
    private static Logger logger = LoggerFactory.getLogger(CountExample1.class);

    public static void main(String[] args) throws Exception {

        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    logger.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        logger.info("count:{}",count);
    }


    private synchronized static void add() {
        count++;
    }
}
