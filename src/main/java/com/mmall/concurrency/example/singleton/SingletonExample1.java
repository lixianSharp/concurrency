package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.NotThreadSafe;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 21:47 2019/4/25
 * @Description: 懒汉模式：单例的实例在第一次使用的时候创建
 */
@NotThreadSafe
public class SingletonExample1 {

    //私有的构造函数
    private SingletonExample1() {
    }

    //单例对象
    private static SingletonExample1 instance = null;

    //静态的工厂方法
    public static SingletonExample1 getInstance() {
        //当有多个线程同时访问第23、24行代码的时候，有可能会创建多个对象
        if (instance == null) {
            instance = new SingletonExample1();
        }

        return instance;
    }
}
