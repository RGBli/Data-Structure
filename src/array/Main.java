package array;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    /*二分查找循环实现
    * 要求数组严格递增，不能出现相等元素
    * 注意循环条件是 left <= right，有个等于号
    * 一般用循环二分查找多一些
    * 参考 https://blog.csdn.net/maoyuanming0806/article/details/78176957
    * */
    public static int commonBinarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left <= right) {
            // 在循环里更新 mid
            mid = (left + right) / 2;
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
        // 递归出口
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
    * 遇到排好序的数组第一反应就应该是二分查找
    * P33*/
    public static int binarySearchForRotatedSortedArray(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int mid = (left + right) / 2;
        while (left <= right) {
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < nums[right]) {   // 右侧排好序了
                // 判断 target 是否在右侧
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {    // 左侧排好序了
                // 判断 target 是否在左侧
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    /*寻找旋转排序数组中的最小值，数组不存在重复元素
    * 思路是二分法
    * P153*/
    public int findMin1(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left < right) {
            mid = (left + right) / 2;
            // 如果右侧有序
            if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }

    /*寻找旋转排序数组中的最小值，数组可能存在重复元素
    * 思路是二分法
    * 与上一题唯一的区别在于多了对 nums[mid] == nums[right] 的讨论
    * P154*/
    public int findMin2(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left < right) {
            mid = (left + right) / 2;
            // 如果右侧有序
            if (nums[mid] < nums[right]) {
                right = mid;
            } else if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right--;
            }
        }
        return nums[left];
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
    * 思路是使用按位异或
    * 按位异或的性质如下
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

    /*旋转图片，将二维数组顺时针旋转90度
    * P48*/
    public void rotate(int[][] matrix) {
        /*方法1:使用辅助二维数组
        * 思路是发现规律元素所在原数组的第 i 行就在新数组的 n - i - 1 列
        * 并且元素所在原数组的第 j 列就在新数组的第 j 行
        * 如果是逆时针旋转则同理
        * 缺点是使用了额外的空间，空间复杂度为 O(n^2)
        int n = matrix.length;
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newMatrix[j][n - i - 1] = matrix[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = newMatrix[i][j];
            }
        }*/

        /*方法2:原地交换
        * 思路是两次循环，第一次循环上下交换，第二次循环反对角交换
        * 如果是逆时针则第二次循环对角交换即可
        * 就是用时间换空间的做法*/
        int n = matrix.length;
        int tmp;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                tmp = matrix[i][j];
                matrix[i][j] = matrix[n - i - 1][j];
                matrix[n - i - 1][j] = tmp;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    /*翻转图像
    * P832*/
    public int[][] flipAndInvertImage(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int k = n % 2 == 0 ? n / 2 : n / 2 + 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                if (j == n - j - 1) {
                    A[i][j] = 1 - A[i][j];
                } else if (A[i][j] == A[i][n - j - 1]) {
                    A[i][j] = 1 - A[i][j];
                    A[i][n - j - 1] = 1 - A[i][n - j - 1];
                }
            }
        }
        return A;
    }

    /*合并两个有序数组
    * P88*/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        /*方法1:合并后排序
        * 因为有排序，所以时间复杂度为 O((m+n)log(m+n))，空间复杂度 O(1)
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);*/

        /*方法2:双指针
        * 时间复杂度 O(m+n)，因为需要额外拷贝一份 nums1，所以空间复杂度 O(m)
        int[] nums1Copy = Arrays.copyOf(nums1, m);
        int p1 = 0;
        int p2 = 0;
        int p = 0;
        while (p1 < m && p2 < n) {
            if (nums1Copy[p1] <= nums2[p2]) {
                nums1[p++] = nums1Copy[p1++];
            } else {
                nums1[p++] = nums2[p2++];
            }
        }
        if (p1 < m) {
            System.arraycopy(nums1Copy, p1, nums1, p, m - p1);
        }
        if (p2 < n) {
            System.arraycopy(nums2, p2, nums1, p, n - p2);
        }*/

        /*思路3:仍然采用双指针法，但从后向前填入 nums1，这样可以不用开辟新数组
        * 时间复杂度 O(m+n)，空间复杂度 O(1)*/
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] >= nums2[p2]) {
                nums1[p--] = nums1[p1--];
            } else {
                nums1[p--] = nums2[p2--];
            }
        }
        if (p2 >= 0) {
            System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
        }
    }

    /*查找数组的峰值
    * P162*/
    public int findPeakElement(int[] nums) {
        /*方法1:线性扫描
        * 时间复杂度 O(n)，空间复杂度 O(1)
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return i;
            }
        }
        return nums.length - 1;*/

        /*方法2:二分扫描*/
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (left < right) {
            mid = (left + right) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /*全排列，输入不含重复元素
    * 思路是回溯，看到题目中带有"组合"，就该考虑到回溯。
    * 回溯的公式如下：
    result = []
    def backtrack(路径, 选择列表):
        if 满足结束条件:
            result.add(路径)
            return

        for 选择 in 选择列表:
            做选择
            backtrack(路径, 选择列表)
            撤销选择
    * P46*/
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> perm = new ArrayList<>();
        backtrackForPermute(nums, res, perm);
        return res;
    }
    public void backtrackForPermute(int[] nums, List<List<Integer>> res, List<Integer> perm) {
        if (perm.size() == nums.length) {
            res.add(new ArrayList<>(perm));
            return;
        }
        for (int num : nums) {
            if (!perm.contains(num)) {
                perm.add(num);
                backtrackForPermute(nums, res, perm);
                perm.remove(perm.size() - 1);
            }
        }
    }

    /*全排列，输入可能包含重复元素
    * 思路还是回溯
    * 需要提前排序，并且多了一个 visited 数组来保存访问情况
    * P47*/
    boolean[] visited;
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> perm = new ArrayList<>();
        visited = new boolean[nums.length];
        Arrays.sort(nums);
        backtrackForPermuteUnique(nums, res, perm);
        return res;
    }
    public void backtrackForPermuteUnique(int[] nums, List<List<Integer>> res, List<Integer> perm) {
        if (perm.size() == nums.length) {
            res.add(new ArrayList<>(perm));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] || (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1])) {
                continue;
            }
            perm.add(nums[i]);
            visited[i] = true;
            backtrackForPermuteUnique(nums, res, perm);
            visited[i] = false;
            perm.remove(perm.size()- 1);
        }
    }

    /*所有的组合，输入不含重复元素，数字可以无限制重复选取
    * 思路是回溯
    * P39*/
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        backtrackFroCombinationSum(candidates, target, res, combination, 0);
        return res;
    }
    public void backtrackFroCombinationSum(int[] candidates, int target, List<List<Integer>> res, List<Integer> combination, int index) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            if (!res.contains(combination)) {
                res.add(new ArrayList<>(combination));
            }
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            combination.add(candidates[i]);
            // 因为可以重复选取，所以是 i 而不是 i + 1
            backtrackFroCombinationSum(candidates, target - candidates[i], res, combination, i);
            combination.remove(combination.size() - 1);
        }
    }

    /*所有的组合，输入可能含有重复元素，并且数字只能使用一次
    * 思路是回溯，与上一题唯一的差别就是多了一个 if 判断去重
    * P40*/
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        Arrays.sort(candidates);
        backtrackFroCombinationSum2(candidates, target, res, combination, 0);
        return res;
    }
    public void backtrackFroCombinationSum2(int[] candidates, int target, List<List<Integer>> res, List<Integer> combination, int index) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            if (!res.contains(combination)) {
                res.add(new ArrayList<>(combination));
            }
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            combination.add(candidates[i]);
            // 因为不能重复选取，所以是 i + 1 而不是 i
            backtrackFroCombinationSum2(candidates, target - candidates[i], res, combination, i + 1);
            combination.remove(combination.size() - 1);
        }
    }

    /*零钱兑换1
    * 思路1：回溯，类似与组合的做法，但对于该题会超时（不代表不对
    * 见方法 coinChange1()
    * 思路2：动态规划，dp[i] 表示凑够 i 的金额时需要的最小硬币个数
    * 见方法 coinChange2()
    * P322*/
    private static int res = -1;
    public static int coinChangeBackTrack(int[] coins, int amount) {
        ArrayList<Integer> perm = new ArrayList<>();
        backtrackForCoinChange(coins, amount, perm, 0);
        return res;
    }
    public static void backtrackForCoinChange(int[] coins, int amount, List<Integer> perm, int index) {
        if (amount == 0) {
            if (res == -1) {
                res = perm.size();
            } else {
                res = Math.min(res, perm.size());
            }
            return;
        }
        if (amount < 0) {
            return;
        }
        for (int i = index; i < coins.length; i++) {
            perm.add(coins[i]);
            backtrackForCoinChange(coins, amount - coins[i], perm, i);
            perm.remove(perm.size() - 1);
        }
    }
    public int coinChangeDP(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /*零钱兑换2，返回构成指定金额的组合数
    * 思路是动态规划，dp[i] 表示金额为 i 时有多少种组合
    * P518*/
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = 0; i <= amount; i++) {
                if (i + coin <= amount) {
                    dp[i + coin] += dp[i];
                }
            }
        }
        return dp[amount];
    }

    /*和为 k 的连续子数组
    * 思路是暴力枚举，虽然是暴力方法，但做了一次优化，每次固定左侧边界，滑动右侧边界
    * 因此不是三层循环
    * P560*/
    public int subarraySum(int[] nums, int k) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int tmp = 0;
            for (int j = i; j < nums.length; j++) {
                tmp += nums[j];
                if (tmp == k) {
                    res++;
                }
            }
        }
        return res;
    }

    /*每日温度
    * P739*/
    public int[] dailyTemperatures(int[] T) {
        /*思路1:暴力法
        int n = T.length;
        for (int i = 0; i < n; i++) {
            int tmp = T[i];
            T[i] = 0;
            for (int j = i + 1; j < n; j++) {
                if (T[j] > tmp) {
                    T[i] = j - i;
                    break;
                }
            }
        }
        return T;*/

        /*思路2:栈*/
        int n = T.length;
        int[] res = new int[n];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int temperature = T[i];
            while (!stack.isEmpty() && temperature > T[stack.peek()]) {
                int prevIndex = stack.pop();
                res[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return res;
    }

    /*数字连续的最长序列
    * P128*/
    public static int longestConsecutive(int[] nums) {
        /*思路1:先排序，然后暴力法
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int res = 1;
        for (int i = 0; i < n - 1; i++) {
            int tmp = 1;
            for (int j = i + 1; j < n; j++) {
                if (nums[j] == nums[j - 1] + 1) {
                    tmp++;
                } else if (nums[j] != nums[j - 1]) {
                    break;
                }
            }
            res = Math.max(res, tmp);
        }
        return res;*/

        /*思路2:哈希表*/
        int res = 0;
        Set<Integer> set = new HashSet<>();
        // 首先去重
        for (int num : nums) {
            set.add(num);
        }
        for (int ele : set) {
            if (set.contains(ele)) {
                int currNum = ele;
                int currLen = 1;
                while (set.contains(currNum + 1)) {
                    currNum++;
                    currLen++;
                }
                res = Math.max(res, currLen);
            }
        }
        return res;
    }

    /*寻找重复数
    * P287*/
    public int findDuplicate(int[] nums) {
        /*方法1:哈希表
        Set<Integer> s = new HashSet<>();
        for (int num : nums) {
            if (s.contains(num)) {
                return num;
            }
            s.add(num);
        }
        return -1;*/

        /*方法2:找入环节点
        * 思路类似与环形链表2*/
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /*搜索二维矩阵，矩阵中每行中的整数从左到右按升序排列，每行的第一个整数大于前一行的最后一个整数
    * 思路是二分查找，将矩阵的每行连接起来，看作一个一维数组
    * 时间复杂度 o(log(mn))
    * P74*/
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        int mid;
        int row, col;
        while (left <= right) {
            mid = (left + right) / 2;
            row = mid / n;
            col = mid % n;
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    /*搜索二维矩阵2，矩阵中每行的元素从左到右升序排列，每列的元素从上到下升序排列
    * P240*/
    public boolean searchMatrix2(int[][] matrix, int target) {
        /*思路1:对每行都使用二分搜索
        * 时间复杂度为 O(mlogn)
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            if (commonBinarySearch(matrix[i], target) != -1) {
                return true;
            }
        }
        return false;*/

        /*思路是从右上角开始搜索（从左下角也可以）
        * 如果 target 比 matrix[i][j] 大则向下搜索，小则向左搜索，相等则返回 true
        * 时间复杂度为 O(m+n)*/
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int m = 0;
        int n = matrix[0].length - 1;
        while (m < matrix.length && n >= 0) {
            if (matrix[m][n] == target) {
                return true;
            } else if (matrix[m][n] > target) {
                n--;
            } else {
                m++;
            }
        }
        return false;
    }

    /*数组交集
    * 思路是使用 set 的 retainAll() 方法计算交集
    * P349*/
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        for (int i : nums1) {
            s1.add(i);
        }
        for (int i : nums2) {
            s2.add(i);
        }
        s1.retainAll(s2);
        int[] res = new int[s1.size()];
        int index = 0;
        for (int i : s1) {
            res[index++] = i;
        }
        return res;
    }

    /*两个有序数组的中位数
    * 思路是二分法
    * P4*/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int totalLength = length1 + length2;
        // 长度为奇数，则找下标为 n/2 的元素
        // 长度为偶数，则找下标为 n/2-1 和 n/2 的元素
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            // 因为 getKthElement() 方法是找第 k 小的元素，而不是排序后下标为 k 的元素
            // 所以要加1
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1;
            int midIndex2 = totalLength / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
        }
    }
    public int getKthElement(int[] nums1, int[] nums2, int k) {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
         * 这里的 "/" 表示整除
         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
         * 这样 pivot 本身最大也只能是第 k-1 小的元素
         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
         */
        int length1 = nums1.length;
        int length2 = nums2.length;
        int index1 = 0;
        int index2 = 0;
        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }
            // 正常情况
            int newIndex1 = Math.min(index1 + k / 2, length1) - 1;
            int newIndex2 = Math.min(index2 + k / 2, length2) - 1;
            int pivot1 = nums1[newIndex1];
            int pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

    /*丢失的数字
    * P268*/
    public int missingNumber(int[] nums) {
        /*思路1：通过求和，减去数组的和就知道缺少哪个数字了*/
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return (1 + nums.length) * nums.length / 2 - sum;

        /*思路2:通过按位异或
        int res = nums.length;
        for (int i = 0; i < nums.length; i++){
            res ^= nums[i] ^ i;
        }
        return res;*/
    }

    /*跳跃游戏1
    * P55*/
    public boolean canJump(int[] nums) {
        /*思路1:从后向前找到0的各个位置，看能否被 cover
        * 如果有0不能被 cover，则返回 false
        int n = nums.length;
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        if (!list.contains(0) || list.indexOf(0) == n - 1) {
            return true;
        }
        int index = list.lastIndexOf(0);
        boolean res = false;
        while (index >= 0) {
            res = false;
            list.set(index, 1);
            if (index != n - 1) {
                for (int i = index - 1; i >= 0; i--) {
                    if (list.get(i) > index - i) {
                        res = true;
                        break;
                    }
                    if (i == 0) {
                        return false;
                    }
                }
            }
            index = list.lastIndexOf(0);
        }
        return res;*/

        /*思路2:贪心*/
        int n = nums.length;
        // 最远能到达的位置
        int rightmost = 0;
        for (int i = 0; i < n - 1; i++) {
            // 当最远能达到 i 时才会判断要不要返回 true
            // 这个判断非常重要
            if (rightmost >= i) {
                rightmost = Math.max(rightmost, i + nums[i]);
                if (rightmost >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /*跳跃游戏2
    * 思路是贪心
    * P45*/
    public int jump(int[] nums) {
        int length = nums.length;
        int end = 0;
        int rightmost = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            rightmost = Math.max(rightmost, nums[i] + i);
            if (i == end) {
                end = rightmost;
                steps++;
            }
        }
        return steps;
    }

    /*移动0
    * P283*/
    public void moveZeroes(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index++] = num;
            }
        }
        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    /*加油站
    * 思路是一次遍历
    * 只要 gas[] 的总和 >= cost[] 总和就可以走完
    * P134*/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int rest = 0, run = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            run += (gas[i] - cost[i]);
            rest += (gas[i] - cost[i]);
            if (run < 0){
                start = i + 1;
                run = 0;
            }
        }
        return rest < 0 ? -1 : start;
    }

    /*视野总和
    * 有 n 个人站队，所有的人全部向右看，个子高的可以看到个子低的发型，给出每个人的身高，问所有人能看到其他人发现总和是多少。
    * 输入：4 3 7 1
    * 输出：2
    * 思路是单调栈，单调栈的格式如下
    for (遍历这个数组)
    {
        if (栈空 || 栈顶元素大于等于当前比较元素)
        {
            入栈;
        }
        else
        {
            while (栈不为空 && 栈顶元素小于当前元素)
            {
                栈顶元素出栈;
                更新结果;
            }
            当前数据入栈;
        }
    }
    * 题目来自网络https://blog.csdn.net/lucky52529/article/details/89155694*/
    public static int viewSum(List<Integer> nums) {
        int res = 0;
        // 在最后插入一个巨人
        nums.add(Integer.MAX_VALUE);
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < nums.size(); i++) {
            if (stack.isEmpty() || nums.get(stack.peek()) > nums.get(i)) {
                stack.push(i);
            } else {
                while (!stack.isEmpty() && nums.get(stack.peek()) <= nums.get(i)) {
                    int peek = stack.pop();
                    res += (i - peek - 1);
                }
                stack.push(i);
            }
        }
        return res;
    }

    /*下一个更大元素
    * 思路是单调栈
    * 先使用单调栈考虑 nums[] 数组中每个元素的下一个更大元素，并保存在哈希表中
    * 然后遍历 findNums[] 数组，从哈希表中找到答案填入
    * P496*/
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Deque<Integer> stack = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<> ();
        int[] res = new int[findNums.length];
        for (int num : nums) {
            while (!stack.isEmpty() && num > stack.peek()) {
                int peek = stack.pop();
                map.put(peek, num);
            }
            stack.push(num);
        }
        while (!stack.isEmpty()) {
            map.put(stack.pop(), -1);
        }
        for (int i = 0; i < findNums.length; i++) {
            res[i] = map.get(findNums[i]);
        }
        return res;
    }

    /*买卖股票的最佳时机1
    * 你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子 卖出该股票
    * 思路是动态规划，前 i 天的最大收益 = max{前 i - 1 天的最大收益，第 i 天的价格 - 前 i - 1 天中的最小价格}
    * P121*/
    public int maxProfit1(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int res = 0;
        // 不能初始化为0
        int min = prices[0];
        for (int price : prices) {
            // 计算 dp[i] 也就是 res
            res = Math.max(res, price - min);
            // 更新 min
            min = Math.min(min, price);
        }
        return res;
    }

    /*买卖股票的最佳时机2
     * 可以多次买卖股票
     * 思路是只要第二天比第一天的票价贵，就买第一天的，并在第二天出售
     * P122*/
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        int res = 0;
        for (int i = 1; i < n; i++) {
           if (prices[i] > prices[i - 1]) {
               res += prices[i] - prices[i - 1];
           }
        }
        return res;
    }

    /*买卖股票的最佳时机3
     * 最多可以两次买卖股票
     * 思路是动态规划
     * 在任意一天结束之后，我们会处于以下五个状态中的一种：
     * 1⃣️未进行过任何操作；
     * 2⃣️只进行过一次买操作；
     * 3⃣️进行了一次买操作和一次卖操作，即完成了一笔交易；
     * 4⃣️在完成了一笔交易的前提下，进行了第二次买操作；
     * 5⃣️完成了全部两笔交易。
     * 因为第一种状态的利润为0，因此仅考虑后四种状态
     * 分别用 buy1, sell1, buy2, sell2 来表示达到这种状态的利润
     * P123*/
    public int maxProfit3(int[] prices) {
        int buy1 = -prices[0];
        int sell1 = 0;
        int buy2 = -prices[0];
        int sell2 = 0;
        for (int i = 1; i < prices.length; ++i) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }

    /*买卖股票的最佳时机4
     * 最多可以 k 次买卖股票
     * 思路是动态规划
     * dp[i][0] 表示在第 i 次买卖后不持有股票这个状态的收益
     * dp[i][1] 表示在第 i 次买卖后仍持有股票这个状态的收益
     * P188*/
    public int maxProfit4(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[][] dp = new int[k + 1][2];
        for (int i = 1; i <= k; i++) {
            dp[i][1] = Integer.MIN_VALUE;
        }
        for (int price : prices) {
            for (int j = 1; j <= k; j++) {
                dp[j][0] = Math.max(dp[j][0], dp[j][1] + price);
                dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - price);
            }
        }
        return dp[k][0];
    }

    /*有序数组的平方
    * 思路是双指针
    * P977*/
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int i = 0;
        int j = n - 1;
        int pos = n - 1;
        while (i <= j) {
            if (nums[i] * nums[i] > nums[j] * nums[j]) {
                res[pos] = nums[i] * nums[i];
                i++;
            } else {
                res[pos] = nums[j] * nums[j];
                j--;
            }
            pos--;
        }
        return res;
    }

    /*矩阵转置
    * P867*/
    public int[][] transpose(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] res = new int[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res[j][i]= matrix[i][j];
            }
        }
        return res;
    }

    public static void main(String[] args) {
    }
}
