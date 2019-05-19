package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 21:35 2019/4/25
 * @Description:不安全的发布对象
 */
@Slf4j
@NotThreadSafe
public class UnsafePublish {
    private String[] states = {"a", "b", "c"};

    public String[] getStates(){
        return states;
    }
    public static void main(String[] args){
        UnsafePublish unsafePublish = new UnsafePublish();
        LoggerUtil.logger.info("{}", Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";

        LoggerUtil.logger.info("{}", Arrays.toString(unsafePublish.getStates()));
    }

}
