package com.mmall.concurrency.example.sync;

import com.mmall.concurrency.LoggerUtil;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class SynchronizedExample1 {

    //修饰一个代码块，作用于调用的对象
    public void test1(int j){
        synchronized (this){
            for(int i=0;i<10;i++){
                LoggerUtil.logger.info("test1 {} - {}",j, i);
            }
        }
    }

    /**
     *  修饰一个方法，作用于调用的对象。
     *  注意：子类继承test2(int j)的时候是带不上synchronized关键字的，
     *          synchronized不属于方法声明的一部分，如果子类想使用synchronized的，
     *              必须在方法声明的时候手动添加上synchronized。
     */
    public synchronized void test2(int j){
        for (int i = 0; i < 10; i++) {
            LoggerUtil.logger.info("test2 {} - {}",j,i);
        }
    }


    public static void main(String[] args){
        SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

//        executorService.execute(() -> {
//            example1.test1(1);
//        });
//
//        executorService.execute(()->{
//            example2.test1(2);
//        });

        executorService.execute(() -> {
            example1.test2(1);
        });

        executorService.execute(()->{
            example2.test2(2);
        });


        //synchronized修改代码块的时候执行顺序是无序的；synchronized修饰方法的时候执行顺序是有序的。
    }

}
