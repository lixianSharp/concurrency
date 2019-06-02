package com.mmall.concurrency.example.threadPool;

import com.mmall.concurrency.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadPoolExample2 {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);//指定线程池中的线程数量

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
