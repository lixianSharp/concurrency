package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:36 2019/5/7
 * @Description: Semaphore信号量：常用于仅能提供有限访问的资源。
 *      计数信号量Semaphore用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。
 *      计数信号量还可以用来实现某种资源池，或者对容器施加边界。
 *      Semaphore中管理者一组虚拟许可(permit),许可的初始数量可通过构造函数来指定，在执行操作时首先
 *      获得许可(只要还有剩余的许可)，并在使用以后释放许可。如果没有许可，那么acquire将阻塞知道有许可(或
 *      者直到被中断或者操作超时)。release方法将返回一个许可给信号量。计算信号量的一种简化形式是二值信号量，
 *      即初始值为1的Semaphore，二值信号量可以用作互斥体(mutex),并具备不可重入的加锁语义：谁拥有这个唯一的许可，
 *      谁就拥有了互斥锁。
 *
 *      Semaphore可以用来实现资源池，例如数据库连接池。我们可以构造一个固定长度的资源池，当池为空时，请求资源将会失败，
 *               但你真正希望看到的行为是阻塞而不是失败，并且当池非空时解除阻塞。如果将Semaphore的计数值初始化为池的大小，
 *              并在池中获取一个资源之前首先调用acquire方法获取一个许可，在将资源返回给池之后调用release释放许可，那么
 *               acquire将一直阻塞直到资源池不为空。
 *
 *           计数信号量(Counting Semaphore)用来 控制 同时访问某个特定资源 的操作数量(而不是访问该特定资源的总次数，而是某时刻最多可以多少个操作访问该资源)
 *
 */
public class SemaphoreExample1 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception{

        //创建一个线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(()->{
                try{
                    semaphore.acquire();//获取一个许可
                    test(threadNum);
                    semaphore.release();//释放一个许可
                }catch(Exception e){
                    LoggerUtil.logger.error("exception",e);
                }
            });
        }
        LoggerUtil.logger.info("finish");
        exec.shutdown();//关闭线程池
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        LoggerUtil.logger.info("{}",threadNum);
        Thread.sleep(100);
    }
}
