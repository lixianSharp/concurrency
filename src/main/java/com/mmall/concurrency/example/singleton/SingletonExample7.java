package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.Recommend;
import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 21:47 2019/4/25
 * @Description: 枚举模式：最安全
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {

    //私有的构造函数
    private SingletonExample7() {
    }

    public static SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    //在类的内部定义一个枚举
    private enum  Singleton{
        INSTANCE;

        private SingletonExample7 singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton(){
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getInstance(){
            return singleton;
        }

    }


    public static void main(String[] args){
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }

}
