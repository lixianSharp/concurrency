package com.mmall.concurrency.example.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mmall.concurrency.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheExample2 {

    public static void main(String[] args) {

        Cache<String, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(10) // 最多存放10个数据
                .expireAfterWrite(10, TimeUnit.SECONDS) // 缓存10秒
                .recordStats() // 开启记录状态数据功能
                .build();

        LoggerUtil.logger.info("{}", cache.getIfPresent("key1")); // null
        cache.put("key1", 1);
        LoggerUtil.logger.info("{}", cache.getIfPresent("key1")); // 1
        cache.invalidate("key1");
        LoggerUtil.logger.info("{}", cache.getIfPresent("key1")); // null

        try {
            LoggerUtil.logger.info("{}", cache.get("key2", new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return -1;
                }
            })); // -1
            cache.put("key2", 2);
            LoggerUtil.logger.info("{}", cache.get("key2", new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return -1;
                }
            })); // 2

            LoggerUtil.logger.info("{}", cache.size()); // 1

            for (int i = 3; i < 13; i++) {
                cache.put("key" + i, i);
            }
            LoggerUtil.logger.info("{}", cache.size()); // 10

            LoggerUtil.logger.info("{}", cache.getIfPresent("key2")); // null

            Thread.sleep(11000);

            LoggerUtil.logger.info("{}", cache.get("key5", new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return -1;
                }
            })); // -1

            LoggerUtil.logger.info("{},{}", cache.stats().hitCount(), cache.stats().missCount());

            LoggerUtil.logger.info("{},{}", cache.stats().hitRate(), cache.stats().missRate());
        } catch (Exception e) {
            LoggerUtil.logger.error("cache exception", e);
        }
    }
}
