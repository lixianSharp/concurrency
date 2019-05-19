package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 10:19 2019/5/9
 * @Description: 关于Condition的使用  多线程间协调通信的类。
 */
public class LockExample6 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                LoggerUtil.logger.info("wait signal");//1
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LoggerUtil.logger.info("get signal");//4
            reentrantLock.unlock();
        }).start();


        new Thread(() -> {
            reentrantLock.lock();
            LoggerUtil.logger.info("get lock");//2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            LoggerUtil.logger.info("send singal ~");//3
            reentrantLock.unlock();
        }).start();

        /**
         * 线程1调用了reentrantLock.lock();的lock方法，线程就加入到了aqs的等待队列中，
         * 线程1会输出等待信号的操作，调用condition的await方法之后，这个线程就从正常的aqs队列中移除了(锁的释放)。
         * 线程1马上就加入到了condition的等待队列中。
         * 因为线程1释放锁的关系，线程2被唤醒，并判断是否可以获取锁，线程2获取锁，
         * 也加入到了aqs的等待队列中，之后输出“get lock”,之后调用condition的发送信号的方法，紧接着输出“send signal ~”,
         * 这个时候，condition的等待队列中有线程1的节点，线程1就被取出来了，加入到了aqs的等待队列中，之后线程2调用unlock方法，释放锁。
         * aqs按从头到尾的顺序唤醒线程1，之后线程1继续执行，输出"get signal",之后线程1释放锁，整个过程执行完毕。
         */
    }
}
