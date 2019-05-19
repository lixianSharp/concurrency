package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ThreadSafe
public class AtomicExample4 {
    //AtomicReference的使用
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args){
        count.compareAndSet(0,2);//表示如果count是0，则将count更新为2.
        count.compareAndSet(0,1);
        count.compareAndSet(1,3);
        count.compareAndSet(2,4);
        count.compareAndSet(3,5);
        LoggerUtil.logger.info("count:{},"+count.get());//4
    }
}


