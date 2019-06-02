package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:28 2019/5/8
 * @Description: CyclicBarrier:允许一组线程相互等待，直到到达某个公共的屏障点。
 *                  通过他可以完成多个线程之间相互等待，只有当每个线程都准备就绪之后，才能各自继续往下后面的操作
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
    /**
     * 打印结果如下：(效果是每隔一秒出现一个 x is ready，当出现5个之后，就会几乎同时打印出5个 x is continue)
     * 23:45:44.888 [pool-1-thread-1] INFO java.lang.Object - 0 is ready
     * 23:45:45.778 [pool-1-thread-2] INFO java.lang.Object - 1 is ready
     * 23:45:46.778 [pool-1-thread-3] INFO java.lang.Object - 2 is ready
     * 23:45:47.779 [pool-1-thread-4] INFO java.lang.Object - 3 is ready
     * 23:45:48.780 [pool-1-thread-5] INFO java.lang.Object - 4 is ready
     * 23:45:48.780 [pool-1-thread-5] INFO java.lang.Object - 4 continue
     * 23:45:48.781 [pool-1-thread-1] INFO java.lang.Object - 0 continue
     * 23:45:48.781 [pool-1-thread-2] INFO java.lang.Object - 1 continue
     * 23:45:48.781 [pool-1-thread-3] INFO java.lang.Object - 2 continue
     * 23:45:48.781 [pool-1-thread-4] INFO java.lang.Object - 3 continue
     * 23:45:49.780 [pool-1-thread-6] INFO java.lang.Object - 5 is ready
     * 23:45:50.781 [pool-1-thread-2] INFO java.lang.Object - 6 is ready
     * 23:45:51.782 [pool-1-thread-3] INFO java.lang.Object - 7 is ready
     * 23:45:52.782 [pool-1-thread-5] INFO java.lang.Object - 8 is ready
     * 23:45:53.783 [pool-1-thread-4] INFO java.lang.Object - 9 is ready
     * 23:45:53.783 [pool-1-thread-6] INFO java.lang.Object - 5 continue
     * 23:45:53.783 [pool-1-thread-4] INFO java.lang.Object - 9 continue
     * 23:45:53.783 [pool-1-thread-3] INFO java.lang.Object - 7 continue
     * 23:45:53.792 [pool-1-thread-5] INFO java.lang.Object - 8 continue
     * 23:45:53.791 [pool-1-thread-2] INFO java.lang.Object - 6 continue
     */
}
