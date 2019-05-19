package com.mmall.concurrency.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mmall.concurrency.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheExample1 {

    public static void main(String[] args) {

        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(10) // 最多存放10个数据
                .expireAfterWrite(10, TimeUnit.SECONDS) // 缓存10秒
                .recordStats() // 开启记录状态数据功能
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return -1;
                    }
                });

        LoggerUtil.logger.info("{}", cache.getIfPresent("key1")); // null
        cache.put("key1", 1);
        LoggerUtil.logger.info("{}", cache.getIfPresent("key1")); // 1
        cache.invalidate("key1");
        LoggerUtil.logger.info("{}", cache.getIfPresent("key1")); // null

        try {
            LoggerUtil.logger.info("{}", cache.get("key2")); // -1
            cache.put("key2", 2);
            LoggerUtil.logger.info("{}", cache.get("key2")); // 2

            LoggerUtil.logger.info("{}", cache.size()); // 1

            for (int i = 3; i < 13; i++) {
                cache.put("key" + i, i);
            }
            LoggerUtil.logger.info("{}", cache.size()); // 10

            LoggerUtil.logger.info("{}", cache.getIfPresent("key2")); // null

            Thread.sleep(11000);

            LoggerUtil.logger.info("{}", cache.get("key5")); // -1

            LoggerUtil.logger.info("{},{}", cache.stats().hitCount(), cache.stats().missCount());

            LoggerUtil.logger.info("{},{}", cache.stats().hitRate(), cache.stats().missRate());
        } catch (Exception e) {
            LoggerUtil.logger.error("cache exception", e);
        }
    }
}
