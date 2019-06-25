package arithmetic.sort;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 19:35 2019/6/5
 * @Description: 直接插入排序：每次将一个待排序的记录，
 *      按其关键字大小插入到前面已经排序号的子序列中的适当位置，
 *          直到全部记录插入完成位置。
 *
 *  设数组为a[0…n-1]。
 *          1.  初始时，a[0]自成1个有序区，无序区为a[1..n-1]。令i=1
 *          2.  将a[i]并入当前的有序区a[0…i-1]中形成a[0…i]的有序区间。
 *          3.  i++并重复第二步直到i==n-1。排序完成。
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {6,3,8,2,9,1,4};

        int j;
        for(int i=1;i<arr.length;i++){
            // 为a[i]在前面的a[0...i-1]有序区间中找一个合适的位置。现在只是找到合适的位置j+1
            for(j=i-1;j>=0;j--){
                if(arr[i]>arr[j]){
                    break;
                }
            }

            //如果j=i-1;则说明arr[i]的值不需要调换，arr[i]的位置就在arr[j]的后一位,也就是相邻
            //如果j!=i-1;则将其arr[i]放在合适的位置j+1上
            if(j!=i-1){
                //将比arr[i]大的数据向后移
                int temp = arr[i];
                for(int k=i-1;k>j;k--){
                    arr[k+1]=arr[k];
                }
                arr[j+1]=temp;//a[k + 1] = temp;等价
            }
        }

        printArrs(arr);
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
