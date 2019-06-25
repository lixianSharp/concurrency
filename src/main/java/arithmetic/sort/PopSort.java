package arithmetic.sort;

import com.mmall.concurrency.LoggerUtil;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 11:04 2019/6/4
 * @Description: 冒泡排序
 * 原理：比较两个相邻的元素，将值大的元素交换至右端。
 *      外层控制循环的趟次，内层循环控制每一趟需要比较的次数。
 */
public class PopSort {
    public static void main(String[] args) {
        int[] arrs = {6,3,8,2,9,1};

        for (int i = 0; i < arrs.length-1; i++) {//一共要比较的趟次
            for (int j = 0; j < arrs.length-1 - i; j++) {//第i趟需要比较的次数
                if(arrs[j]>arrs[j+1]){
                    int temp = arrs[j];
                    arrs[j] = arrs[j+1];
                    arrs[j+1] = temp;
                }
            }
        }

        LoggerUtil.logger.info("经过冒泡排序后的数组为:");
        printArrs(arrs);

    }


    /**
     * 打印数组
     *
     * @param arrs
     */
    private static void printArrs(int[] arrs) {
        for (int i = 0; i < arrs.length; i++) {
            System.out.print(arrs[i] + " ");
        }
    }
}
