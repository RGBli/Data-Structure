package string;

import java.util.*;

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

    /*翻转字符串中的单词
    * 思路是双指针遍历字符串，head 记录每个单词的首字母，滑动 i 来找到完整单词
    * 使用栈来保存单词，然后弹出时写入字符串
    * P151*/
    public static String reverseWords(String s) {
        s = s.trim();
        if (s.length() == 0 || s.length() == 1) {
            return s;
        }
        Deque<String> stack = new LinkedList<>();
        int head = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && s.charAt(i - 1) == ' ') {
                head = i;
            }
            if (s.charAt(i) == ' ' && s.charAt(i - 1) != ' ') {
                stack.push(s.substring(head, i));
                System.out.println(s.substring(head, i));
            } else if (i == s.length() - 1) {
                stack.push(s.substring(head));
                System.out.println(s.substring(head));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
            sb.append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /*最小覆盖子串
    * 思路是滑动窗
    * 使用了两个哈希表，第一个存储 t 中字符出现的次数，第二个存储窗口中 s 中字符出现的次数
    * check() 方法用于检测改窗口是否满足要求
    * P76*/
    public String minWindow(String s, String t) {
        Map<Character, Integer> m1 = new HashMap<>();
        Map<Character, Integer> m2 = new HashMap<>();
        int tLen = t.length();
        int sLen = s.length();
        for (int i = 0; i < tLen; i++) {
            char tmp = t.charAt(i);
            m1.put(tmp, m1.getOrDefault(tmp, 0) + 1);
        }
        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int resLeft = -1;
        int resRight = -1;
        while (right < sLen) {
            // 增加
            if (m1.containsKey(s.charAt(right))) {
                m2.put(s.charAt(right), m2.getOrDefault(s.charAt(right), 0) + 1);
            }
            while (check(m1, m2) && left <= right) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    resLeft = left;
                    resRight = left + minLen;
                }
                // 删除
                if (m1.containsKey(s.charAt(left))) {
                    m2.put(s.charAt(left), m2.getOrDefault(s.charAt(left), 0) - 1);
                }
                left++;
            }
            right++;
        }
        return resLeft == -1 ? "" : s.substring(resLeft, resRight);
    }
    public boolean check(Map<Character, Integer> m1, Map<Character, Integer> m2) {
        for (Character c : m1.keySet()) {
            if (m2.get(c) == null || m2.get(c) < m1.get(c)) {
                return false;
            }
        }
        return true;
    }

    /*反转字符串
    * 思路是滑动窗
    * P344*/
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char tmp = s[left];
            s[left++] = s[right];
            s[right--] = tmp;
        }
    }

    public static void main(String[] args) {
        String s = "hello";
        StringBuilder sb = new StringBuilder(s);

    }
}
