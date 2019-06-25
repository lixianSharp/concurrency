package com.mmall.concurrency.example.current;

import com.mmall.concurrency.ConcurrencyTest;
import com.mmall.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:35 2019/5/5
 * @Description: 并发容器CopyOnWriteArrayList
 *      CopyOnWriteArrayList用于替代同步List，在某些情况下它提供了更好的并发性能，并且在迭代期间不需要
 *        对容器进行加锁或复制.
 *        写入时复制(Copy-On-Write)容器的线程安全性在于，只要正确地发布一个事实不可变的对象，那么在访问
 *          该对象的时就不在需要进一步的同步。
 */
@ThreadSafe
public class CopyOnWriteArrayListExample {



    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;


    //日志记录器（这是SpringBoot自带的日志记录器）
    private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    public static List<Integer> list = new CopyOnWriteArrayList<Integer>();
    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();//获得信号量
                    update(count);//计数，执行目标方法
                    semaphore.release();//释放信号量
                } catch (Exception e) {
                    logger.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        logger.info("size:{}",list.size());
    }


    private static void update(int i) {
        list.add(i);

    }


}
