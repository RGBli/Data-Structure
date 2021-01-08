package array;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {5,7,7,8,8,10};
        System.out.println(binarySearchLast(arr, 8));
    }

    /*二分查找循环实现
    * 要求数组严格递增，不能出现相等元素
    * 一般用循环二分查找多一些
    * 参考 https://blog.csdn.net/maoyuanming0806/article/details/78176957
    * */
    public static int commonBinarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            // 在循环里更新 mid
            int mid = (left + right) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /*二分查找递归实现
     * 要求数组严格递增，不能出现相等元素
     * 参考 https://blog.csdn.net/maoyuanming0806/article/details/78176957
     * */
    public static int recursiveBinarySearch(int[] nums, int left, int right, int target) {
        if (target < nums[left] || target > nums[right] || left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (target == nums[mid]) {
            return mid;
        } else if (target > nums[mid]) {
            return recursiveBinarySearch(nums, mid + 1, right, target);
        } else {
            return recursiveBinarySearch(nums, left, mid - 1, target);
        }
    }

    /*搜索第一次出现的位置，算法更具有鲁棒性
    * 注意算法与 commonBinarySearch() 方法的改动
    * 没有在循环中返回，而是比较 left 元素是否等于 target 来返回
    * 出循环时 left 必定等于 right
    * 参考 https://blog.csdn.net/u014221279/article/details/50903515
    * */
    public static int binarySearchFirst(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        // 因为 right 的赋值没有减1，<= 会造成死循环
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 加一层判断
        return target == nums[left] ? left : -1;
    }

    /*搜索最后一次出现的位置，算法更具有鲁棒性
     * 注意算法与 commonBinarySearch() 方法的改动
     * 没有在循环中返回，而是比较 left 元素是否等于 target 来返回
     * 出循环时 left 必定等于 right
     * 参考 https://blog.csdn.net/u014221279/article/details/50903515*/
    public static int binarySearchLast(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        // 因为 left 的赋值没有加1，<= 会造成死循环
        while (left < right) {
            int mid = (left + right) / 2 + 1;
            if (nums[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        // 加一层判断
        return target == nums[left] ? left : -1;
    }

    /*二分法对旋转排序数组搜索
    * 思路是一分为二，对有序的一半使用二分查找，对无序的一半再一分为二
    * 再对有序的一半使用二分查找，以此循环
    * 遇到排好序的数组第一反应就应该是二分查找*/
    public static int binarySearchForRotatedSortedArray(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int mid = (left + right) / 2;
        while (left <= right) {
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < nums[right]) {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    /*数组逆序
    * 思路是滑动窗，left 和 right 两两交换*/
    public static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int tmp = nums[right];
            nums[right--] = nums[left];
            nums[left++] = tmp;
        }
    }

    /*数组旋转 k 位
    * 思路是三次旋转，用到了 reverse() 方法*/
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    public static int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int leftIdx = binarySearchFirst(nums, target);
        int rightIdx = binarySearchLast(nums, target);
        if (leftIdx <= rightIdx && leftIdx != -1 && rightIdx != -1) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }
}
