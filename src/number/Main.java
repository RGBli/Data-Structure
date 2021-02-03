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

    /*x 的平方根
    * P69*/
    public static int mySqrt(int x) {
        int left = 0;
        int right = x;
        while (left <= right) {
            int mid = (left + right) / 2;
            if ((long)mid * mid > x) {
                right = mid - 1;
            } else if ((long)mid * mid < x) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return right;
    }

    /*用 rand7() 实现 rand10()
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

    /*最大交换，至多可以交换数字中的两位，使得数字最大
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

    public static void main(String[] args) {
        System.out.println(maximumSwap(2736));
    }
}
