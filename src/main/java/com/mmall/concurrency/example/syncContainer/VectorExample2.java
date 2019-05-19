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
        }
    }
}
