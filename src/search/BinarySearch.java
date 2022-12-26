package search;

import java.util.ArrayList;

//注意：使用二分查找的前提是 该数组是有序的。
public class BinarySearch {
    public static void main(String[] args) {
        int arr[]={1,8,10,89,1000,1000,1000,1234};
        ArrayList<Integer> arrayList = binarySearch2(arr, 0, arr.length - 1, 1000);
        System.out.println("arrayList="+arrayList);
    }

    //二分查找算法

    /**
     *
     * @param arr 数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，如果没有找到，就返回-1
     */
    public static int binarySearch(int[] arr,int left,int right,int findVal){
        //当left>right时，说明递归了整个数组，但是没有找到
        if(left>right){
            return -1;
        }
        int mid=(left+right)/2;
        int midVal=arr[mid];
        if (findVal>midVal){
            //向右递归
            return binarySearch(arr,mid+1,right,findVal);
        } else if (findVal<midVal) {
            //向左递归
            return binarySearch(arr,left,mid-1,findVal);
        }else {
            return mid;
        }
    }

    /**
     *
     * 思考题：{1,8,10,89,1000,1000,1234}
     * 有多个相同的数值时，如何将所有的数值查找到，比如这里的1000
     *
     * 思路分析
     * 1.在找到mid索引值，不要马上返回
     * 2.向mid 索引值的左边扫描，将所有满足1000，的元素的下标，加入到集合ArrayList
     * 3.向mid 索引值的右边扫描，将所有满足1000，的元素的下标, 加入到集合ArrayList
     * 4.将ArrayList返回
     */
    public static ArrayList<Integer> binarySearch2(int[] arr,int left,int right,int findVal){
        //当left>right时，说明递归了整个数组，但是没有找到
        if(left>right){
            return new ArrayList<>();
        }
        int mid=(left+right)/2;
        int midVal=arr[mid];
        if (findVal>midVal){
            //向右递归
            return binarySearch2(arr,mid+1,right,findVal);
        } else if (findVal<midVal) {
            //向左递归
            return binarySearch2(arr,left,mid-1,findVal);
        }else {
            /**
             * 思路分析
             * 1.在找到mid索引值，不要马上返回
             * 2.向mid 索引值的左边扫描，将所有满足1000，的元素的下标，加入到集合ArrayList
             * 3.向mid 索引值的右边扫描，将所有满足1000，的元素的下标, 加入到集合ArrayList
             * 4.将ArrayList返回
             */
            ArrayList<Integer> resIndexList = new ArrayList<>();
            int temp=mid-1;
            while (true){
                if (temp<0 ||arr[temp]!=findVal){
                    break;
                }
                //否则，就temp 放入到resIndexList
                resIndexList.add(temp);
                temp-=1;
            }
            resIndexList.add(mid);

            //向mid 索引值的右边扫描,将
            temp=mid+1;
            while (true){
                if (temp>arr.length-1||arr  [temp]!=findVal) {
                    break;
                }
                //否则，就temp 放入到resIndexList
                resIndexList.add(temp);
                temp+=1;//右移
            }
            return resIndexList;
        }

    }
}
