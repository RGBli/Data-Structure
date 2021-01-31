package array;

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
    * 思路是回溯，公式如下
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

    public static void main(String[] args) {

    }
}
