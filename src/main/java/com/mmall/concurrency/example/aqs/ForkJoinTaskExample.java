package com.mmall.concurrency.example.aqs;

import com.mmall.concurrency.LoggerUtil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 20:33 2019/5/12
 * @Description: ForkJoin
 */
public class ForkJoinTaskExample extends RecursiveTask<Integer> {

    public static final int threshold = 2;
    private int start;
    private int end;

    public ForkJoinTaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        //如果任务足够小就计算任务
        boolean canCampute = (end - start) <= threshold;//这里的 <= 是小于等于
        if (canCampute) {
            for (int i = start; i <= end; i++) {
                sum +=i;
            }
        }else{
            //如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start +end )/2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start,middle);
            ForkJoinTaskExample rightTash = new ForkJoinTaskExample(middle + 1, end);

            //执行子任务
            leftTask.fork();
            rightTash.fork();

            //等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTash.join();

            //合并子任务
            sum = leftResult + rightResult;

        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        //生成一个计算任务，计算1+2+3+4+...+100
        ForkJoinTaskExample task = new ForkJoinTaskExample(1,100);

        //执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);

        try {
            LoggerUtil.logger.info("result:{}",result.get());
        } catch(Exception e) {
            LoggerUtil.logger.info("exception",e);
        }
    }

}
