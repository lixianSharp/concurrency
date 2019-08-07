package com.mmall.concurrency.example.threadPool;

import com.mmall.concurrency.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 14:25 2019/6/12
 * @Description: Executor是基于生产者-消费者模式，提交的操作相当于生产者(生产待完成的工作单元)， 执行任务的操作相当于消费者(执行完这些工作单元)。
 *          如果要在程序中实现一个生产者--消费者的设计，那么最简单的方式通常就是使用Executor。
 *
 *          每当看到下面这种形式的代码时：
 *              new Thread(runnable).start()
 *              并且你希望获得一种更灵活的执行策略时，请考虑使用Executor来代替Thread。
 *
 *    类库提供了一个灵活的线程池以及一些有用的默认配置。可以通过调用Executors中的静态工厂方法之一来创建一个线程池：
 *          newFixedThreadPool: newFixedThreadPool将创建一个固定长度的线程池，每当提交一个任务时就创建一个线程，直到达到
 *              线程池的最大数量，这是线程池的规模将不再变化。(如果某个线程由于发生了未预期的Exception而结束，那么线程池会补充一个新线程).
 *
 *          newCachedThreadPool: newCachedThreadPool将创建一个可缓存的线程池，如果线程池的当前规模超过了处理需求时，那么将回收空闲的线程，
 *              而当需求增加时，则可以添加新的线程，线程池的规模不存在任何限制。
 *
 *          newSingleThreadExecutor: newSingleThreadExecutor是一个单线程的Executor，它创建单个工作者线程来执行任务，如果这个线程异常结束，
 *              会创建另一个线程来替代。newSingleThreadExecutor能确保依照任务在队列中的顺序来串行执行。(例如FIFO、LIFO、优先级).
 *
 *          newScheduledThreadPool: newScheduledThreadPool创建了一个固定长度的线程池，而且以延迟或定时的方式来执行任务
 *
 *   Executor的生命周期：
 *      Executor的实现通常会创建线程来执行任务，但JVM只有在所有(非守护)线程全部终止后才会退出。因此，如果无法
 *          正确的关闭Executor，那么JVM将无法结束。
 *      由于Executor以一部方式来执行任务，因此在任何时刻，之前提交任务的状态不是立即可见的。有些任务可能已经完成，有些可能正在运行，
 *          而其他的任务可能在队列中等待执行。
 *      为了解决执行服务的什么周期，Executor扩展了ExecutorService接口，添加了一些用于生命周期管理的方法(同时还有
 *          一些用于任务提交的遍历方法)。
 *      ExecutorService的生命周期有3种状态：运行、关闭和已终止。
 *              ExecutorService在初始创建是处于运行状态。
 *              shutdown方法将执行平缓的关闭过程:不再接受新的任务，同时等待已经提交的任务执行完成————包括那些已提交但还未开始执行的任务。
 *              shutNow方法将执行粗暴的关闭过程：他讲尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务。
 *              在ExecutorService关闭后提交的任务将由“拒绝执行处理器(Rejected Execution Handler)”来处理，它会抛弃任务，或者使用execute
 *                  方法抛出一个未检查的RejectedExcutionException.
 *              等待所有任务都完成后，ExecutorService将转入终止状态。可以调用awaitTermination来等待ExecutorService来到达终止状态，或者
 *                  通过调用isTerminated来轮训ExecutorService是否终止。通常在awaitTermination之后会立即调用shutdown，从而产生同步地关
 *                  闭ExecutorService效果。
 *       Executor执行的任务有4个什么周期：创建、提交、开始和完成。在Executor中，已提交但尚未开始的任务可以取消，
 *          但对于那些已经开始执行的任务，只有当他们能响应中断时，才能取消。取消一个已经完成的任务不会有任何影响。
 *
 */
@Slf4j
public class ThreadPoolExample1 {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    LoggerUtil.logger.info("task:{}", index);
                }
            });
        }
        executorService.shutdown();
    }
}
