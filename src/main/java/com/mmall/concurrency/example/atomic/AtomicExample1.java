package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.ThreadSage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全性：
 *      当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些进程将如何交替执行，
 *      并且【在主调代码中不需要任何二外的同步或协同】，这个类都能表现出正确的行为，
 *      那么就成这个类是线程安全的。
 *
 * 原子性：提供了互斥访问，同一时刻只能有个一个线程来对它进行操作。
 * 可见性：一个线程对主内存的修改可以及时的被其它线程观察到。
 * 有序性：一个线程观察其他县城中的指令执行顺序，由于指令 重排序 的存在，
 *              该观察结果一般杂乱无序
 *
 *  原子性---Atomic包
 */
@Slf4j
@ThreadSage
public class AtomicExample1 {

    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;

    //使用原子性：Atomic包中的AtomicInteger操作int类型的数据。
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {

        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                //这个Lambda表达式主体本质是实现了Runnable函数接口中的run方法。
                try {
                    semaphore.acquire();//获取信号量
                    add();
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
        LoggerUtil.logger.info("count:{}",count);//5000
    }


    private static void add() {
        count.incrementAndGet();
       // count.getAndIncrement();
        /**
         * increamentAndGet方法和getAndIncrement方法的区别：
         *  一个是先增加后获取，一个是先获取后增加。（类似++i和i++）
         */
    }
}
