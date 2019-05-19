package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;


@Slf4j
@ThreadSafe
public class AtomicExample5 {
    //AtomicIntegerFieldUpdater的使用
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");

    @Getter
    public  volatile   int count = 100;


    public static void main(String[] args) {
        AtomicExample5 example5 = new AtomicExample5();
        if(updater.compareAndSet(example5,100,120)){//如果example5是100，则将example5更新为120
            LoggerUtil.logger.info("update success1,{}," + example5.count);// update success1,{},120
        }

        if(updater.compareAndSet(example5,100,120)){
            LoggerUtil.logger.info("update success2,{}," + example5.count);
        }else{
            LoggerUtil.logger.info("update field,{}," + example5.count);//update field,{},120
        }
    }
}


