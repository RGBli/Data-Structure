package number;

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
    * 使用二分法
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
//    public int rand10() {
//        int c = 49;
//        while (c > 40) {
//            c = rand7() + (rand7() - 1) * 7;
//        }
//        return c % 10 + 1;
//    }

    public static void main(String[] args) {
        System.out.println(mySqrt(2147395599));
    }
}
