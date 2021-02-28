package number;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {
    // 二进制数转十进制
    public static int binaryToDec(int b) {
        int res = 0;
        while (b != 0) {
            int tmp = b % 10;
            res = res * 2 + tmp;
            b /= 10;
        }
        return res;
    }

    /**x 的平方根
     * P69*/
    public static int mySqrt(int x) {
        int left = 0;
        int right = x;
        while (left <= right) {
            int mid = (left + right) / 2;
            if ((long) mid * mid > x) {
                right = mid - 1;
            } else if ((long) mid * mid < x) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return right;
    }

    /**用 rand7() 实现 rand10()
     * 思路是使用两次 rand7()，通过这两次结果的组合就可以得到 [1,49] 的随机数
     * 为了使得取 rand10() 更加方便，剔除了40以上的部分
     * 当然也可以直接剔除10以上的部分，但需要更多的循环时间，效率不高
     * P470*/
    public int rand7() {
        return new Random().nextInt(7) + 1;
    }

    public int rand10() {
        int c = 49;
        while (c > 40) {
            c = rand7() + (rand7() - 1) * 7;
        }
        return c % 10 + 1;
    }

    /**最大交换，至多可以交换数字中的两位，使得数字最大
     * 思路是先将数字逆序排序，然后与原数字从高位开始比较
     * 找到第一个不相等的数字，将二者交换
     * 注意要使用 lastIndexOf() 方法找到元素最后一次出现的 index 再交换
     * P670*/
    public static int maximumSwap(int num) {
        String numStr = String.valueOf(num);
        char[] numCharArray = numStr.toCharArray();
        Arrays.sort(numCharArray);
        // 得到排序的字符串
        String sortedStr = new StringBuilder(String.valueOf(numCharArray)).reverse().toString();
        StringBuilder sb = new StringBuilder(numStr);
        for (int i = 0; i < numStr.length(); i++) {
            if (numStr.charAt(i) != sortedStr.charAt(i)) {
                char tmp = sortedStr.charAt(i);
                int index = numStr.lastIndexOf(tmp);
                sb.setCharAt(i, tmp);
                sb.setCharAt(index, numStr.charAt(i));
                break;
            }
        }
        return Integer.parseInt(sb.toString());
    }

    /**外观数列
     * 思路是递归
     * P38*/
    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        } else if (n == 2) {
            return "11";
        } else {
            String s = countAndSay(n - 1);
            int left = 0;
            int cnt = 1;
            StringBuilder sb = new StringBuilder();
            for (int right = 1; right < s.length(); right++) {
                if (s.charAt(left) == s.charAt(right)) {
                    cnt++;
                } else {
                    sb.append(cnt).append(s.charAt(left));
                    cnt = 1;
                    left = right;
                }
            }
            sb.append(cnt).append(s.charAt(left));
            return sb.toString();
        }
    }

    /**数字的二进制表示中位1的个数
     * 思路是构造掩码，使掩码和 n 做按位与操作
     * P191*/
    public static int hammingWeight(int n) {
        int res = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                res++;
            }
            mask <<= 1;
        }
        return res;
    }

    /**实现 pow(x, n) 的快速幂算法
     * 思路是折半法，时间复杂度 O(logn)
     * P50*/
    public double myPow(double x, int n) {
        double res = 1.0;
        for (int i = n; i != 0; i /= 2) {
            if (i % 2 != 0) {
                res *= x;
            }
            x *= x;
        }
        return n < 0 ? 1 / res : res;
    }

    /**判断丑数
     * 丑数就是只包含质因数 2, 3, 5 的正整数
     * 思路是不断除以5 3 2，能除尽的数就是丑数
     * P263*/
    public boolean isUgly(int num) {
        if (num < 1) {
            return false;
        }
        // 三个 while 的顺序可以任意
        while (num % 5 == 0) {
            num /= 5;
        }
        while (num % 3 == 0) {
            num /= 3;
        }
        while (num % 2 == 0) {
            num /= 2;
        }
        return num == 1;
    }

    /**找到第 n 个丑数
     * 思路是三指针法
     * P264*/
    public int nthUglyNumber(int n) {
        int p2 = 0, p3 = 0, p5 = 0;
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = Math.min(Math.min(2 * res[p2], 3 * res[p3]), 5 * res[p5]);
            // 注意这里不能用 else if
            if (res[i] == 2 * res[p2]) {
                p2++;
            }
            if (res[i] == 3 * res[p3]) {
                p3++;
            }
            if (res[i] == 5 * res[p5]) {
                p5++;
            }
        }
        return res[n - 1];
    }

    /**统计所有小于非负整数 n 的质数的数量
     * P204*/
    public int countPrimes(int n) {
        // 思路1：直接使用 isPrime() 方法判断，简单但会超时
        /*int res = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                res++;
            }
        }
        return res;*/

        // 思路2：埃氏筛，如果 x 为质数，则 x 的倍数为合数
        // isPrime[i]表示数 i 是否为质数
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        int res = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                res++;
                for (int j = 2 * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return res;
    }

    /**判断是否为质数*/
    public boolean isPrime(int n) {
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**求数字位数
     * 思路是使用 log10*/
    public int getLen(int n) {
        if (n == 0) {
            return 1;
        }
        return (int) Math.log10(n) + 1;
    }

    /**完全平方数
     * 思路是动态规划
     * dp[i] 表示和为 i 的完全平方数数量
     * P279*/
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 小于等于 n 的完全平方数数量
        int maxSquareIndex = (int) Math.sqrt(n) + 1;
        // 小于等于 n 的完全平方数数组
        int[] squareNums = new int[maxSquareIndex];
        for (int i = 1; i < maxSquareIndex; i++) {
            squareNums[i] = i * i;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < maxSquareIndex; j++) {
                if (squareNums[j] > i) {
                    break;
                }
                // 状态转移方程
                dp[i] = Math.min(dp[i], dp[i - squareNums[j]] + 1);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(hammingWeight(3));
    }
}
