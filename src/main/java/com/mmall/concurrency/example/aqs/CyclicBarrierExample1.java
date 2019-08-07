package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:28 2019/5/8
 * @Description: CyclicBarrier:允许一组线程相互等待，直到到达某个公共的屏障点。
 *                  通过他可以完成多个线程之间相互等待，只有当每个线程都准备就绪之后，才能各自继续往下后面的操作。
 *
 *           栅栏（Barrier）相当于闭锁，他能阻塞一组线程直到某个时间发生。
 *           栅栏与闭锁的区别在于：
 *                  所有线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其它线程。
 *
 *           CyclicBarrier可以使一定数量的参与方反复地在栅栏位置汇集，他在并行迭代算法中非常有用：这种算法通常
 *              将一个问题拆分成一些互相独立的子问题。当线程到达栅栏位置时将调用await方法，这个方法将阻塞直到
 *              所有线程都到达栅栏位置。如果所有线程都到达了栅栏位置，那么栅栏将打开，此时所有线程都被释放，
 *              而栅栏将被【重置】以便下次使用。(如果对await的调用超时、或者await阻塞的线程被中断，那么栅栏就
 *              被认为是打破了，所有阻塞的await调用都将终止并抛出BrokenBarrierException)。
 *              (如果成功地通过栅栏，那么await将为每个线程返回一个唯一的到达索引号，我们可以利用这些索引来“选举”
 *              产生一个领导线程，并在下一次迭代中由该领导线程执行一些特殊的工作)。
 *              CyclicBarrier还可以使你将一个栅栏操作传递给构造函数， 这是一个Runnbale，当成功通过
 *              栅栏时会(在一个子任务线程中)执行它，但在阻塞线程被释放之前是不能执行的。
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
