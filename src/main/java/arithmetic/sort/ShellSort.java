package arithmetic.sort;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 19:48 2019/6/10
 * @Description: 希尔排序
 *      流程：
 *          1、将有n个元素的数组分成 n/2个数字序列，第1个数据和第 n/2+1 个数据为一对，....
 *          2、一次循环使每一个序列对排好顺序
 *          3、然后，在变为n/4个序列，再次排序。
 *          4、不断重复上诉过程，随着序列减少最后变为一个，也就完成了整个排序。
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {127,118,105,101,112,100,3,5,1};
        System.out.println("排序前");
        printArrs(arr);
        System.out.println("");

        shellSort(arr);

        System.out.println("排序后");
        printArrs(arr);
    }


    /**
     * 希尔排序 9
     * @param arr 要进行希尔排序的数组 8j 7 5i
     */
    public static void shellSort(int[] arr){
        for(int r=arr.length/2;r>0;r/=2){ // 用来分解数组元素为多个序列，每次比较两数的间距，直到其值为零循环就结束
            for(int i = r;i<arr.length;i++){//按设置的间距r，分别比较对应的数组元素
                int temp=arr[i];
                int j=i-r;
                while(j>=0 && temp<arr[j]){
                    arr[j+r]=arr[j];
                    j-=r;
                }
                arr[j+r]=temp;
            }
            printArrs(arr);
            System.out.println();
        }

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
