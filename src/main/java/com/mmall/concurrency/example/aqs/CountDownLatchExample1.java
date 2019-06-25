package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:36 2019/5/7
 * @Description: 闭锁是一种同步工具类。CountDownLatch是一种闭锁。CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。
 *                        每当一个线程完成了自己的任务后，计数器的值就会减1。
 *                        当计数器值到达0时，它表示所有的线程已经完成了任务，
 *                        然后在闭锁上等待的线程就可以恢复执行任务。
 *
 *
 *        闭锁是一种同步工具类，可以延迟线程的进度直到其到达终止状态。
 *           闭锁的作用相当于一扇门：在闭锁到达结束状态之前，这扇门一直是关闭的，并且没有任何线程能通过，当到达结束状态时，
 *                这扇门会打开并允许所有的线程通过。当闭锁到达结束状态后，将不会再改变状态，因此这扇门将永远保持打开状态。
 *           闭锁可以用来确保某些活动直到其他活动都完成后才继续执行，例如：
 *                1、确保某个计算在其需要的所有资源都被初始化之后才继续执行。
 *                2、确保某个服务在其依赖的所有其他服务都已经启动之后才启动。
 *                3、等待直到某个操作的所有参与者。
 *           CountDownLatch是一种灵活的闭锁实现，可以在上述各种情况中使用，他可以是一个或多个线程等待一组事情发生。闭锁状态
 *                包括一个计数器，该计数器初始化为一个正数，表示需要等待的事件数量。coutDown方法递减计数器，表示有一个事件
 *                已经发生了，而wait方法等待计数器达到零，这表示所有需要等待的事件都已经发生。如果计数器的值非零，那么await
 *                会一直阻塞直到计数器为零，或者等待中的线程中断，或者等待超时
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
