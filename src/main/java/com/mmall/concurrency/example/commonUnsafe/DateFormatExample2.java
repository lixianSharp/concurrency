package com.mmall.concurrency.example.commonUnsafe;

import com.mmall.concurrency.ConcurrencyTest;
import com.mmall.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:35 2019/5/5
 * @Description:
 */
@ThreadSafe
public class DateFormatExample2 {

    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;

    //日志记录器（这是SpringBoot自带的日志记录器）
    private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    /**
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
            executorService.execute(() -> {
                try {
                    semaphore.acquire();//获得信号量
                    update();//计数，执行目标方法
                    semaphore.release();//释放信号量
                } catch (Exception e) {
                    logger.error("exception",e);
                }
                countDownLatch.countDown();//计数器减1
            });
        }
        //await方法等待计数器到达零，表示所有需要等待的事件都已经发生，之后才能执行之后的代码。
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
    }

    //这个方法是线程不安全的写法
    private static void update() {
        try {
            //SimpleDateFormat是线程不安全的，将对象的声明放在方法中，让其成为局部对象。
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            simpleDateFormat.parse("20190208");
        } catch (ParseException e) {
            logger.error("parse exception",e);
        }

    }


}
