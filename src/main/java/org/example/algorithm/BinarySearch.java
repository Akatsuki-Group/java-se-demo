package org.example.algorithm;

/**
 * 二分查找算法
 * 又叫折半查找，要求待查找的序列有序。每次取中间位置的值与待查关键字比较，如果中间位置
 * 的值比待查关键字大，则在前半部分循环这个查找的过程，如果中间位置的值比待查关键字小，
 * 则在后半部分循环这个查找的过程。直到查找到了为止，否则序列中没有待查的关键字。
 *
 * @author arthur
 */
public class BinarySearch {
    public static void main(String[] args) {
        //int[] arrays = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //int index = biSearch(arrays, 7);
        //System.out.println(arrays[index]);


        int[] arr = {1,3,5,7,9,11};
        int key = 5;
        //int position = recursionBinarySearch(arr,key,0,arr.length - 1);

        int position = commonBinarySearch(arr, key);

        if(position == -1){
            System.out.println("查找的是"+key+",序列中没有该数！");
        }else{
            System.out.println("查找的是"+key+",找到位置为："+position);
        }
    }

    /**
     * 使用递归的二分查找
     * title:recursionBinarySearch
     *
     * @param arr 有序数组
     * @param key 待查找关键字
     * @return 找到的位置
     */
    public static int recursionBinarySearch(int[] arr, int key, int low, int high) {
        if (key < arr[low] || key > arr[high] || low > high) {
            return -1;
        }

        int middle = (low + high) / 2;            //初始中间位置
        if (arr[middle] > key) {
            //比关键字大则关键字在左区域
            return recursionBinarySearch(arr, key, low, middle - 1);
        } else if (arr[middle] < key) {
            //比关键字小则关键字在右区域
            return recursionBinarySearch(arr, key, middle + 1, high);
        } else {
            return middle;
        }

    }


    /**
     * 不使用递归的二分查找
     *title:commonBinarySearch
     *@param arr
     *@param key
     *@return 关键字位置
     */
    public static int commonBinarySearch(int[] arr,int key){
        int low = 0;
        int high = arr.length - 1;
        int middle = 0;			//定义middle

        if(key < arr[low] || key > arr[high] || low > high){
            return -1;
        }

        while(low <= high){
            middle = (low + high) / 2;
            if(arr[middle] > key){
                //比关键字大则关键字在左区域
                high = middle - 1;
            }else if(arr[middle] < key){
                //比关键字小则关键字在右区域
                low = middle + 1;
            }else{
                return middle;
            }
        }

        return -1;		//最后仍然没有找到，则返回-1
    }
}
