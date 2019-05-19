package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.NotRecommend;
import com.mmall.concurrency.annoations.NotThreadSafe;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 21:40 2019/4/25
 * @Description: 对象溢出
 */
@NotThreadSafe
@NotRecommend
public class Escape {

    private int thisCanBeEscape = 0;

    public Escape(){
        new InnerClass();
    }

    private  class InnerClass{
        public InnerClass(){
            LoggerUtil.logger.info("{}",Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args){
        new Escape();
    }
}
