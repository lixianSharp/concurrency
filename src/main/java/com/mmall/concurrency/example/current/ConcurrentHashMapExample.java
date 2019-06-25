package com.mmall.concurrency.example.current;

import com.mmall.concurrency.ConcurrencyTest;
import com.mmall.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:35 2019/5/5
 * @Description: 线程安全的HashMap->ConcurrentHashMap
 *  CurrentHashMap并不是将每个方法都在同一个锁上同步并使得每次只能有一个线程访问容器，而是使用一种
 *      力度更细的加锁机制来实现更大程度的共享，这种机制称为 分段锁(Lock Striping).在这种机制下，任意数量
 *      的读取线程可以并发的访问Map，执行读取操作的线程和执行写入操作的线程可以并发地修改Map。CurrentHashMap
 *      带来的结果是：在并发访问环境下将实现更高的吞吐量，而在单线程环境中值损失非常小的性能。
 *
 */
@ThreadSafe
public class ConcurrentHashMapExample {



    //请求总数
    public static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;


    //日志记录器（这是SpringBoot自带的日志记录器）
    private static Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    public static Map<Integer,Integer> map = new ConcurrentHashMap<Integer,Integer>();
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
        logger.info("size:{}",map.size());
    }


    private static void update(int i) {
        map.put(i, i);
    }


}
