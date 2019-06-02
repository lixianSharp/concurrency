package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 21:47 2019/4/25
 * @Description: 饿汉模式：单例的实例在类装载的时候进行创建
 */
@ThreadSafe
public class SingletonExample6 {

    //私有的构造函数
    private SingletonExample6() {
    }

    //注意：静态字段和静态代码块是按照顺序执行的。

    //单例对象
    private static SingletonExample6 instance = null;

    static{
        instance = new SingletonExample6();
    }

    //静态的工厂方法
    public static SingletonExample6 getInstance() {
        return instance;
    }

    public static void main(String[] args){
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
}
