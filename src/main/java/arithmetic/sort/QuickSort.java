package arithmetic.sort;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 22:56 2019/6/5
 * @Description: 快速排序
 */
public class QuickSort {

    public static void main(String[] args) {
        int array[] = {10,5,3,1,7,2,8};
        System.out.println("排序之前：");
        for(int element : array){
            System.out.print(element+" ");
        }

        quickSort(array,0,array.length-1);

        System.out.println("\n排序之后：");
        for(int element : array){
            System.out.print(element+" ");
        }
    }


    /**
     * 快速排序代码
     * @param arr 要进行快速排序的数组
     * @param _left 数组的初始索引，默认为0
     * @param _right 数组的最大索引，默认为 arr.length-1
     */
    public static void quickSort(int arr[],int _left,int _right){
        int left = _left;
        int right = _right;
        int temp = 0;
        if(left <= right){   //待排序的元素至少有两个的情况
            temp = arr[left];  //待排序的第一个元素作为基准元素

            while(left != right){   //从左右两边交替扫描，直到left = right

                //从右往左扫描，找到第一个比基准元素小的元素
                while(left<right && arr[right] >= temp) {
                    right--;
                }
                arr[left] = arr[right];  //找到这种元素arr[right]后与arr[left]交换

                //从左往右扫描，找到第一个比基准元素大的元素
                while(left < right && arr[left] <= temp) {
                    left++;
                }
                arr[right] = arr[left];  //找到这种元素arr[left]后，与arr[right]交换

            }//最大的while的括号
            arr[right] = temp;    //基准元素归位
            quickSort(arr,_left,left-1);  //对基准元素左边的元素进行递归排序
            quickSort(arr, right+1,_right);  //对基准元素右边的进行递归排序
        }//if的括号
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
