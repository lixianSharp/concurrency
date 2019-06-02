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

    /**
     * 打印结果：
     * 23:53:59.051 [pool-1-thread-1] INFO java.lang.Object - 0 is ready
     * 23:53:59.932 [pool-1-thread-2] INFO java.lang.Object - 1 is ready
     * 23:54:00.934 [pool-1-thread-3] INFO java.lang.Object - 2 is ready
     * 23:54:01.934 [pool-1-thread-4] INFO java.lang.Object - 3 is ready
     * 23:54:02.934 [pool-1-thread-5] INFO java.lang.Object - 4 is ready
     * 23:54:02.934 [pool-1-thread-5] INFO java.lang.Object - callback is running
     * 23:54:02.934 [pool-1-thread-5] INFO java.lang.Object - 4 continue
     * 23:54:02.934 [pool-1-thread-1] INFO java.lang.Object - 0 continue
     * 23:54:02.934 [pool-1-thread-4] INFO java.lang.Object - 3 continue
     * 23:54:02.934 [pool-1-thread-2] INFO java.lang.Object - 1 continue
     * 23:54:02.934 [pool-1-thread-3] INFO java.lang.Object - 2 continue
     * 23:54:03.935 [pool-1-thread-6] INFO java.lang.Object - 5 is ready
     * 23:54:04.936 [pool-1-thread-5] INFO java.lang.Object - 6 is ready
     * 23:54:05.936 [pool-1-thread-3] INFO java.lang.Object - 7 is ready
     * 23:54:06.937 [pool-1-thread-4] INFO java.lang.Object - 8 is ready
     * 23:54:07.938 [pool-1-thread-2] INFO java.lang.Object - 9 is ready
     * 23:54:07.938 [pool-1-thread-2] INFO java.lang.Object - callback is running
     * 23:54:07.938 [pool-1-thread-6] INFO java.lang.Object - 5 continue
     * 23:54:07.938 [pool-1-thread-3] INFO java.lang.Object - 7 continue
     * 23:54:07.938 [pool-1-thread-5] INFO java.lang.Object - 6 continue
     * 23:54:07.938 [pool-1-thread-2] INFO java.lang.Object - 9 continue
     * 23:54:07.939 [pool-1-thread-4] INFO java.lang.Object - 8 continue
     */
}
