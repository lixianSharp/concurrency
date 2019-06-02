package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annoations.NotThreadSafe;

import java.util.Vector;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:31 2019/5/6
 * @Description: 同步容器在有些场合下是线程不安全的
 */
@NotThreadSafe
public class VectorExample2 {
    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            //使用lambda表达式，本质是实现Runnable函数接口中的run方法
            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                }
            });

            thread1.start();
            thread2.start();

            /**
             *  会抛出异常：java.lang.ArrayIndexOutOfBoundsException: Array index out of range
             *      这是因为：如果线程1将25行执行完之后线程2刚好开始执行31行的时候，就有
             *          可能发生索引越界异常，导致31行中要取出来的数据被删掉了
             */

        }
    }
}
