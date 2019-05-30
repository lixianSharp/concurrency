package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.ThreadSafe;
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
 * 有序性：一个线程观察其他线程中的指令执行顺序，由于指令 重排序 的存在，
 *              该观察结果一般杂乱无序
 *
 *  原子性---Atomic包
 *
 *
 */
@Slf4j
@ThreadSafe
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

        /**
         * 计数信号量(Counting Semaphore)用来 控制 同时访问某个特定资源 的操作数量，或者同时执行某个指定操作的数量。
         * 计数信号量还可以用来实现某种资源池，或者对容器施加边界。
         * Semaphore中管理着一组虚拟的许可(permit),许可的初始数量可通过构造函数来指定。在执行操作时，
         *    首先获得许可(只要还有剩余的许可)，并在使用以后释放许可。如果没有许可，那么acquire将阻塞直到有许可(或者直
         *    到被中断或者操作超时)。release方法将返回一个许可给信号量。计算信号量的一种简化形式是二值信号量，即初始值
         *    为1的Semaphore。二值信号量可以用作互斥体，并具备不可重入的加锁语义：谁拥有这个唯一的许可，就拥有了互斥锁。
         *
         * Semaphore可以用来实现资源池，例如数据库连接池。我们可以构造一个固定长度的资源池，当池为空时，请求资源将会失败，
         *     但你真正希望看到的行为是阻塞而不是失败，并且当池非空时解除阻塞。如果将Semaphore的计数值初始化为池的大小，
         *     并在池中获取一个资源之前首先调用acquire方法获取一个许可，在将资源返回给池之后调用release释放许可，那么
         *     acquire将一直阻塞直到资源池不为空。
         *
         * 计数信号量(Counting Semaphore)用来 控制 同时访问某个特定资源 的操作数量(而不是访问该特定资源的总次数，而是某时刻最多可以多少个操作访问该资源)
         */
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);

        /**
         * 闭锁是一种同步工具类，可以延迟线程的进度直到其到达终止状态。
         * 闭锁的作用相当于一扇门：在闭锁到达结束状态之前，这扇门一直是关闭的，并且没有任何线程能通过，当到达结束状态时，
         *      这扇门会打开并允许所有的线程通过。当闭锁到达结束状态后，将不会再改变状态，因此这扇门将永远保持打开状态。
         * 闭锁可以用来确保某些活动直到其他活动都完成后才继续执行，例如：
         *      1、确保某个计算在其需要的所有资源都被初始化之后才继续执行。
         *      2、确保某个服务在其依赖的所有其他服务都已经启动之后才启动。
         *      3、等待直到某个操作的所有参与者。
         * CountDownLatch是一种灵活的闭锁实现，可以在上述各种情况中使用，他可以是一个或多个线程等待一组事情发生。闭锁状态
         *      包括一个计数器，该计数器初始化为一个正数，表示需要等待的事件数量。coutDown方法递减计数器，表示有一个事件
         *      已经发生了，而wait方法等待计数器达到零，这表示所有需要等待的事件都已经发生。如果计数器的值非零，那么await
         *      会一直阻塞直到计数器为零，或者等待中的线程中断，或者等待超时
         */
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
                countDownLatch.countDown();//coutDown方法递减计数器，表示有一个事件已经发生了
            });
        }
        //wait方法等待计数器达到零，这表示所有需要等待的事件都已经发生
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
