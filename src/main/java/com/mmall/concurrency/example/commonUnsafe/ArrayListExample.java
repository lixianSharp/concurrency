package com.mmall.concurrency.example.commonUnsafe;

import com.mmall.concurrency.ConcurrencyTest;
import com.mmall.concurrency.annoations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:35 2019/5/5
 * @Description:
 */
@NotThreadSafe
public class ArrayListExample {



    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;


    //日志记录器（这是SpringBoot自带的日志记录器）
    private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    public static List<Integer> list = new ArrayList<>();
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
        //wait方法等待计数器达到零，这表示所有需要等待的事件都已经发生
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        logger.info("size:{}",list.size());
    }

    //这个方法是线程不安全的写法
    private static void update(int i) {
        list.add(i);

    }


}
