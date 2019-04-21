package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.ThreadSage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Logger;


@Slf4j
@ThreadSage
public class AtomicExample6 {
    //AtomicBoolean的使用
    private static AtomicBoolean isHappened = new AtomicBoolean(false);

    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;


    @Getter
    public  volatile   int count = 100;


    public static void main(String[] args) throws Exception{
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();//得到信号量
                    test();//执行目标代码
                    semaphore.release();//释放信号量
                } catch (Exception e) {
                    LoggerUtil.logger.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        LoggerUtil.logger.info("isHappened:{}" + isHappened.get());
    }


    private static void test(){
        if(isHappened.compareAndSet(false,true)){
            //这个例子演示了，让某段代码只执行一次
            System.out.println("executed:");
        }
    }

}


