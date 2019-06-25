package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.FutureTask;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:34 2019/5/10
 * @Description: FutureTask: 可以返回执行结果
 *      FutureTask也可以用作闭锁。（FutureTask实现了Future语义，表示一种抽象的可生成结果的计算）。FutureTask
 *      表示的计算是通过Callable来实现的，相当于一种可生成结果的Runnable，并且可以处于以下3种状态：等待运行、
 *      正在运行(Running)和运行完成(Completed)。"执行结果"表示计算的所有可能结束方式，包括正常结束、由于取消
 *      而结束和由于异常而结束等。当FutureTask进入完成状态后，它会永远停止在这个状态上。
 *
 *      FutureTask.get的行为取决于任务的状态。如果任务已经完成，那么get会立即返回结果，否则get将阻塞知道任务
 *          进入完成状态，然后返回结果或者抛出异常。FutureTask将计算结果从执行计算的线程传递到获取这个结果的线程,
 *          而FutureTask的规范确保了这种传递过程能实现结果的安全发布。
 */
public class FutureTaskExample {

    public static void main(String[] args) throws Exception{
//        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                LoggerUtil.logger.info("do something in callable");
//                Thread.sleep(5000);
//                return "Done";
//            }
//        });

        //将上面注释掉的用Lambda表达式来替代
        FutureTask<String> futureTask = new FutureTask<>(()->{
            LoggerUtil.logger.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        });

        new Thread(futureTask).start();
        LoggerUtil.logger.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        LoggerUtil.logger.info("result:{}",result);

    }

    /**
     * 打印结果：
     * 00:12:22.406 [main] INFO java.lang.Object - do something in main
     * 00:12:22.406 [Thread-0] INFO java.lang.Object - do something in callable
     * 00:12:27.410 [main] INFO java.lang.Object - result:Done
     *
     */


}
