package arithmetic;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 14:25 2019/6/12
 * @Description:
 */
public class TestC {
    public static void main(String[] args) {

 
    }

    /**
     *
     * @param arr
     * @param _left 0
     * @param _right arr.length-1
     */
    public static void quickSort(int[] arr,int _left,int _right){
        int temp = 0 ;
        int left = _left;
        int right = _right;

        if(left<right){
            temp = arr[left];
            while (left<right) {
                while (left < right && arr[right] >= temp) {
                    right--;
                }
                arr[left] = arr[right];

                while (left < right && arr[left] <= temp) {
                    left++;
                }
                arr[right] = arr[left];
            }
            arr[right] = temp;
            quickSort(arr,_left,left-1);
            quickSort(arr,left+1,_right);
        }
    }
    /**
     * 直接插入排序
     * @param arr
     */
    public static void insertSort(int[] arr){
        int j=0,k=0;
        for(int i=1;i<arr.length;i++){
            //// 为a[i]在前面的a[0...i-1]有序区间中找一个合适的位置。现在只是找到合适的位置j+1
            for(j = i-1;j>=0;j--){
                if(arr[i]>arr[j]){
                    break;
                }
            }
            // 如果找到了一个合适的位置。 将arr[i]放置到合适的位置上
            if(j!=i-1){
                // 将比a[i]大的数据向后移
                int temp = arr[i];
                for(k=i-1;k>j;k--){
                    arr[k+1] = arr[k];
                }
                arr[j+1] = temp;
            }

        }
    }

    /**
     * 选择排序
     * @param arr
     */
    public static void selectSort(int[] arr){
        for(int i=0;i<arr.length-1;i++){//每趟要比较的次数
            for(int j=i+1;j<arr.length-1;j++){
                if(arr[i]>arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }

        }
    }
    /**
     * 冒泡排序
     * @param arr
     */
    public static void popSort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
