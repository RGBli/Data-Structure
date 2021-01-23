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

    public static void main(String[] args) {
        System.out.println(mySqrt(2147395599));
    }
}
