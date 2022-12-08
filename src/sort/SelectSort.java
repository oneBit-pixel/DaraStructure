package sort;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSort {
    //选择排序
    public static void selectSort(int[] arr) {

        //先假定第0个为最小值,随后加1假定，进行比较 交换
        //选择排序时间复杂度是O(^2)
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) { //说明假定的最小值,并不是最小
                    min = arr[j]; //重置Min
                    minIndex = j; //重置minIndex
                }
            }
            //将最小值，放在arr[0],把arr[0]，即交换
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            System.out.println("第" + (i + 1) + "轮后");
            System.out.println(Arrays.toString(arr));
        }

//        //使用逐步推到方式
//        //原始的数组:      101,34,119,1
//        //第一轮排序:      1,34,119,101
//
//        //第一轮
//        int minIndex = 0;
//        int min = arr[0];
//        for (int j = 0 + 1; j < arr.length; j++) {
//            if (min > arr[j]) { //说明假定的最小值,并不是最小
//                min = arr[j]; //重置Min
//                minIndex = j; //重置minIndex
//            }
//        }
//
//        //将最小值，放在arr[0],把arr[0]，即交换
//        if (minIndex != 0) {
//            arr[minIndex] = arr[0];
//            arr[0] = min;
//        }
//
//
//        //第二轮
//        minIndex = 1;
//        min = arr[1];
//        for (int j = 1 + 1; j < arr.length; j++) {
//            if (min > arr[j]) { //说明假定的最小值,并不是最小
//                min = arr[j]; //重置Min
//                minIndex = j; //重置minIndex
//            }
//        }
//
//        //将最小值，放在arr[0],把arr[0]，即交换
//        if (minIndex!=1){
//            arr[minIndex] = arr[1];
//            arr[1] = min;
//        }
//
//        //第三轮
//        minIndex = 2;
//        min = arr[2];
//        for (int j = 2 + 1; j < arr.length; j++) {
//            if (min > arr[j]) { //说明假定的最小值,并不是最小
//                min = arr[j]; //重置Min
//                minIndex = j; //重置minIndex
//            }
//        }
//
//        //将最小值，放在arr[0],把arr[0]，即交换
//        if (minIndex!=2){
//            arr[minIndex] = arr[2];
//            arr[2] = min;
//        }
    }

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};
        selectSort(arr);
    }

}
