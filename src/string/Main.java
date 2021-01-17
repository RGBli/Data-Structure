package string;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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

    // 暴力模式匹配算法
    public int kmpBF(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (n == 0) {
            return 0;
        }
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == n) {
            return i - j;
        } else {
            return -1;
        }
    }

    /*KMP算法*/
    public int kmp(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (n == 0) {
            return 0;
        }
        int i = 0;
        int j = 0;
        int[] next = getNext(t);
        while (i < m && j < n) {
            if (j == -1 || s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == n) {
            return i - j;
        } else {
            return -1;
        }
    }

    /*获取 next 数组
    * next[j] 表示 t 串开头与末尾最多的相同字符数
    * 参考 https://blog.csdn.net/cdnight/article/details/11935387*/
    public int[] getNext(String t) {
        int n = t.length();
        int[] next = new int[n];
        int j = 0;
        next[0] = -1;
        int k = -1;
        while (j < n - 1) {
            if (k == -1 || t.charAt(j) == t.charAt(k)) {
                j++;
                k++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /*获取修正的 nextVal 数组
    * 从 nextVal[1] 开始，如果某位与它 next 值指向的位(字符)相同
    * 则该位的 nextVal 值就是指向位的 nextVal 值
    * 如果不同，则该位的 nextVal 值就是它自己的 next 值
    * 参考 https://blog.csdn.net/zwqjoy/article/details/78812795*/
    public int[] getNextVal(String t) {
        int n = t.length();
        int[] nextVal = new int[n];
        int j = 0;
        nextVal[0] = -1;
        int k = -1;
        while (j < n - 1) {
            if (k == -1 || t.charAt(j) == t.charAt(k)) {
                j++;
                k++;
                if (t.charAt(j) == t.charAt(k)) {
                    nextVal[j] = nextVal[k];
                } else {
                    nextVal[j] = k;
                }
            } else {
                k = nextVal[k];
            }
        }
        return nextVal;
    }

    /*生成括号
    * 思路是 DFS，DFS 的具体实现在 generate() 方法
    * P22*/
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generate(res, 0, 0, "", n);
        return res;
    }

    // 如果当前的右括号数量大于左括号，或者超过了规定的括号数量，则直接停止
    public void generate(List<String> stringList, int left, int right, String str, int n){
        if (left > n || right > n || right > left) {
            return;
        }
        if (left == n && right == n) {
            stringList.add(str);
            return;
        }
        generate(stringList,left + 1, right, str + "(", n);
        generate(stringList, left, right + 1, str + ")", n);
    }



    public static void main(String[] args) {
        String s = "aabc";
        System.out.println(s.indexOf("a", 1));
    }
}
