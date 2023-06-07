package tree;

public class HeapSort {
    public static void main(String[] args) {
        //要求将数组进行升序排序
        int arr[] = {4, 6, 8, 5, 9};

    }

    //编写一个堆排序的方法
    public static void heapSort(int arr[]) {
        System.out.println("堆排序！！");
    }

    //将一个数组（二叉树），调整成一个大顶堆

    /**
     * 功能： 完成将以i对应的非叶子结点的树调整成大顶堆
     * 举例：
     * int arr[]={4,6,8,5,9} => i =1 => adjustHeap => 得到{4,9,8,5,6}
     * 如果我们再次调用adjustHeap 传入的是 i = 0 => 得到{4,9,8,5,6} => {9,6,8,5,4}
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子结点在数组索引
     * @param length 表示堆多少个元素继续调整，length是在逐渐减少
     */
    public static void adjustHeap(int arr[], int i, int length) {
        int temp = arr[i]; //先取出当前元素的值，保存在临时变量
        //开始调整
        //说明
        //1. k = i*2+1 k 是 i结点的左子结点
        for (int k = i * 2 + 1; k < length; k = k * 2) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                //说明左子结点的值小于右子结点的值
                k++;//k指向左子节点
            }
            if (arr[k] > temp) {//如果子结点大于父结点
                arr[i] = arr[k];//把较大的值赋给当前的结点
                i = k;//！！！ i指向k,继续循环比较
            }else {
                break;//
            }
        }

        //当for循环结束后，我们已经将以i 为父结点的树的最大值，放在了最顶上（局部）
        arr[i]=temp;//将temp放到调整后的位置
    }
}
