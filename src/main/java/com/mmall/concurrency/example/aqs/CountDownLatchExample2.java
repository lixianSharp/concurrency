package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:36 2019/5/7
 * @Description:
 */
public class CountDownLatchExample2 {
    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception{

        //创建一个线程池
        ExecutorService exec = Executors.newCachedThreadPool();

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
        countDownLatch.await(10,TimeUnit.MILLISECONDS);//等待10毫秒，如果超过了10毫秒，就会只想37行之后的代码。
        LoggerUtil.logger.info("finish");
        exec.shutdown();//关闭线程池
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        LoggerUtil.logger.info("{}",threadNum);
    }
}
