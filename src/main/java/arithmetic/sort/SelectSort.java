package arithmetic.sort;

import com.mmall.concurrency.LoggerUtil;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 22:24 2019/6/4
 * @Description: 选择排序
 *      选择排序和直接插入排序类似，都将数据分为有序区和无序区。
 *      所不同的是直接插入排序是将无序区的第一个元素直接插入到有序区形成一个更大的有序区。
 *      而选择排序是从无序区选一个最小的元素直接放到有序区的最后。
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arrs = {5,2,8,4,9,1};

        for (int i=0;i<arrs.length-1;i++){//一共要进行排序的趟次
            for(int j=i+1;j<arrs.length;j++){//第i趟排序要进行比较的次数，每趟都找出无需区间中最小的数放在数组索引为i的位置
                if (arrs[i] > arrs[j]) {
                    //交换他们的值
                    int temp = arrs[i];
                    arrs[i] = arrs[j];
                    arrs[j] = temp;
                }
            }
        }

        LoggerUtil.logger.info("经过选择排序后的数组为:");
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
