package com.mmall.concurrency.example.sync;

import com.mmall.concurrency.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample2 {

    //修改一个类，作用与所有对象
    public static void test1(int j){
        synchronized (SynchronizedExample2.class){
            for(int i=0;i<10;i++){
                LoggerUtil.logger.info("test1 {} - {}",j, i);
            }
        }
    }

    //修饰一个静态方法，作用于所有对象
    public static synchronized  void test2(int j){
        for (int i = 0; i < 10; i++) {
            LoggerUtil.logger.info("test2 {} - {}",j,i);
        }
    }


    public static void main(String[] args){
        SynchronizedExample2 example1 = new SynchronizedExample2();
        SynchronizedExample2 example2 = new SynchronizedExample2();
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(() -> {
//            example1.test1(1);
//        });
//

        executorService.execute(()->{
            example2.test1(2);
        });
        executorService.execute(() -> {
            example1.test2(1);
        });

        executorService.execute(()->{
            example2.test2(2);
        });

    }

}
