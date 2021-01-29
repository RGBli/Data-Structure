package array;

import com.sun.xml.internal.fastinfoset.tools.XML_SAX_StAX_FI;

import java.util.*;

public class Main {

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

    /*查找非严格递增数组中 targe 值的 index 范围
    * 思路是使用二分查找第一次出现的位置和最后一次出现的位置
    * 这两个位置就是结果*/
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

    /*矩阵置零
    * 思路是用第一行和第一列来存储该行该列是否应该被置零
    * 为了记录第一行和第一列是否应该被置零，用了两个 boolean 变量*/
    public void setZeroes(int[][] matrix) {
        // 判断第一行和第一列是否有0，用两个变量表示，时间复杂度 O(1)
        boolean rowFlag = false;
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                rowFlag = true;
                break;
            }
        }
        boolean colFlag = false;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                colFlag = true;
                break;
            }
        }
        // 遍历矩阵，如果是0则置该行和该列的首元素为0
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }
        // 遍历第一行，如果是0则将所在列置零
        for (int i = 1; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 1; j < matrix.length; j++) {
                    matrix[j][i] = 0;
                }
            }
        }
        // 遍历第一列，如果是0则将所在行置零
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 对第一行和第一列置零
        if (rowFlag) {
            Arrays.fill(matrix[0], 0);
        }
        if (colFlag) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    /*求数组的子集
    * 思路是每次向之前的所有子集中插入新元素，最初的子集为空
    * 缺点是要求数组无重复元素*/
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // 最初的子集
        res.add(new ArrayList<>());
        for (int num : nums) {
            int size = res.size();
            for (int i = 0; i < size; i++) {
                // 不能写成 List<Integer> tmp = res.get(i)
                // 因为 res.get(i) 是 res 内存里的，需要新开辟内存
                List<Integer> tmp = new ArrayList<>(res.get(i));
                tmp.add(num);
                res.add(tmp);
            }
        }
        return res;
    }

    /*最长需要排序的长度
    * 思路是创建一个排好序的数组，然后与原数组对比，找到最左端和最右端的不同的位置
    * 这两个位置就是 left 和 right*/
    public static int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        int[] newNums = Arrays.copyOfRange(nums, 0, n);
        Arrays.sort(newNums);
        int left = n;
        int right = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != newNums[i]) {
                left = Math.min(left, i);
                right = Math.max(right, i);
            }
        }
        return right >= left ? right - left + 1 : 0;
    }

    /*使数组唯一的最小增量
    * 思路是排序*/
    public int minIncrementForUnique(int[] A) {
        Arrays.sort(A);
        int res = 0;
        for (int i = 1; i < A.length; i++) {
            // 合并了 A[i] == A[i - 1] 和 A[i] < A[i - 1] 的两种情况
            if (A[i] <= A[i - 1]) {
                res += A[i - 1] - A[i] + 1;
                A[i] = A[i - 1] + 1;
            }
        }
        return res;
    }

    /*能被5整除的二进制数组
    * 思路1：直接转十进制，但在数组大的时候会溢出
    * 思路2：只记录十进制数组的末尾数字
    * 代码用的是思路2*/
    public List<Boolean> prefixesDivBy5(int[] A) {
        List<Boolean> res = new ArrayList<>();
        int last = 0;
        for (int x : A) {
            last = (last * 2 + x) % 10;
            res.add(last % 5 == 0);
        }
        return res;
    }

    /*对排序后的数组去重
    * 思路是滑动窗法
    * P26*/
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums.length;
        }
        int left = 0;
        int right = 1;
        while (right < nums.length) {
            if (nums[right] == nums[right - 1]) {
                right++;
            } else {
                nums[++left] = nums[right++];
            }
        }
        return left + 1;
    }

    // 从数组中删除指定的元素（可重复）
    public int removeElement(int[] nums, int val) {
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != val) {
                nums[res++] = nums[i];
            }
        }
        return res;
    }

    /*排序数组插入元素的位置
    * 使用二分查找，与二分查找唯一的不同是最后的返回值由-1改为 low
    * P35*/
    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    /*寻找仅出现一次的数字，其他数字都出现了2次
    * 使用按位异或，按位异或的性质如下
    * 1⃣️ a ^ a = 0
    * 2⃣️ a ^ 0 = a
    * 3⃣️ a ^ b ^ c = a ^ (b ^ c)
    * P136*/
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

    /*单词拆分
    * 思路是动态规划，dp[i] 表示前 i 个字符构成的字串是否满足条件
    * P139*/
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    /*查找缺失的第一个正数
    * 思路是第一次循环使得各数归位
    * 第二次循环返回第一个没有归位的 index + 1，如果全部归位则返回 n + 1
    * P41*/
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[i] != i + 1
                    && nums[nums[i] - 1] != nums[i]) {
                int tmp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = tmp;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }

    /*不相邻数组和的最大值
    * 思路是动态规划，用两个变量代替 dp 数组来减少空间复杂度
    * prev1 保存到当前元素的前前个元素的最大值
    * prev2 保存到当前元素的前一个元素的最大值
    * P198*/
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }
        int res = 0;
        int prev1 = 0;
        int prev2 = nums[0];
        for (int i = 1; i < nums.length; i++) {
            res = Math.max(prev1 + nums[i], prev2);
            prev1 = prev2;
            prev2 = res;
        }
        return res;
    }

    /*下一个排列
    * 思路是第一次逆序循环找到最后一个比相邻的后一个元素小的元素
    * 第二次逆序循环找到第一个比 nums[i] 大的元素，并与 nums[i] 交换
    * 最后将 nums[i + 1:] 逆序
    * P31*/
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        int j = n - 1;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        reverse(nums, i + 1, n - 1);
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<>();
        deque.add(0);
        deque.offer(1);
        System.out.println(deque);
        deque.offerFirst(2);
        System.out.println(deque);
        deque.offerLast(3);
        System.out.println(deque);
        deque.poll();
        System.out.println(deque);
        deque.pollFirst();
        System.out.println(deque);
        deque.pollLast();
        System.out.println(deque);
        deque.push(4);
        System.out.println(deque);
        deque.pop();
        System.out.println(deque);
    }
}
