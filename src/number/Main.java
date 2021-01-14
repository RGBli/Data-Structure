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

    public static void main(String[] args) {

    }
}
