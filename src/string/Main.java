package string;

import java.util.Deque;
import java.util.LinkedList;

public class Main {
    /*在保证字典序列最小的情况下去重
    * 思路是利用栈，遇到一个新字符如果比栈顶元素小，并且在新字符后面还有和栈顶一样的
    * 就循环弹出栈顶元素，这是为了保证字典序列最小
    * 但需要先判断新字符是否在栈中，在栈中则不操作*/
    public String removeDuplicateLetters(String s) {
        int n = s.length();
        if (n <= 1) {
            return s;
        }
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char tmp = s.charAt(i);
            if (!stack.contains(tmp)) {
                while (!stack.isEmpty()
                        && tmp < stack.peek()
                        && s.indexOf(stack.peek(), i) != -1) {
                    stack.pop();
                }
                stack.push(tmp);
            }
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        return res.reverse().toString();
    }

    public static void main(String[] args) {
        String s = "aabc";
        System.out.println(s.indexOf("a", 1));
    }
}
