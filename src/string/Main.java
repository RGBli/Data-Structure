package string;

import java.util.*;

public class Main {
    /**
     * 在保证字典序列最小的情况下去重
     * 思路是利用栈，遇到一个新字符如果比栈顶元素小，并且在新字符后面还有和栈顶一样的
     * 就循环弹出栈顶元素，这是为了保证字典序列最小
     * 但需要先判断新字符是否在栈中，在栈中则不操作
     */
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

    /**
     * KMP算法
     */
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

    /**
     * 获取 next 数组
     * next[j] 表示 t 串开头与末尾最多的相同字符数
     * 参考 https://blog.csdn.net/cdnight/article/details/11935387
     */
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

    /**
     * 获取修正的 nextVal 数组
     * 从 nextVal[1] 开始，如果某位与它 next 值指向的位(字符)相同
     * 则该位的 nextVal 值就是指向位的 nextVal 值
     * 如果不同，则该位的 nextVal 值就是它自己的 next 值
     * 参考 https://blog.csdn.net/zwqjoy/article/details/78812795
     */
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

    /**
     * 生成括号
     * 思路是 DFS，DFS 的具体实现在 generate() 方法
     * P22
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generate(res, 0, 0, "", n);
        return res;
    }

    // 如果当前的右括号数量大于左括号，或者超过了规定的括号数量，则直接停止
    public void generate(List<String> stringList, int left, int right, String str, int n) {
        if (left > n || right > n || right > left) {
            return;
        }
        if (left == n && right == n) {
            stringList.add(str);
            return;
        }
        generate(stringList, left + 1, right, str + "(", n);
        generate(stringList, left, right + 1, str + ")", n);
    }

    /**
     * 翻转字符串中的单词
     * 思路是双指针遍历字符串，head 记录每个单词的首字母，滑动 i 来找到完整单词
     * 使用栈来保存单词，然后弹出时写入字符串
     * P151
     */
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

    /**
     * 最小覆盖子串
     * 思路是滑动窗
     * 使用了两个哈希表，第一个存储 t 中字符出现的次数，第二个存储窗口中 s 中字符出现的次数
     * check() 方法用于检测改窗口是否满足要求
     * P76
     */
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

    /**
     * 反转字符串
     * 思路是滑动窗
     * P344
     */
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char tmp = s[left];
            s[left++] = s[right];
            s[right--] = tmp;
        }
    }

    /**
     * 移除 k 位数字
     * P402
     */
    public String removeKDigits(String num, int k) {
        // 特判
        if (num.length() == k) {
            return "0";
        }
        StringBuilder s = new StringBuilder(num);
        for (int i = 0; i < k; i++) {
            int index = 0;
            for (int j = 1; j < s.length() && s.charAt(j) >= s.charAt(j - 1); j++) {
                index = j;
            }
            s.delete(index, index + 1);
            // 清除前导0
            while (s.length() > 1 && s.charAt(0) == '0') {
                s.delete(0, 1);
            }
        }
        return s.toString();
    }

    /**
     * 简化路径
     * 思路是使用栈，遇到..则弹出，遇到.不处理，其他情况都入栈
     * 因为 Deque 的栈是在头部进栈，不方便最后的 join 操作，所以可以用 List 或 Stack 来做栈
     * P71
     */
    public String simplifyPath(String path) {
        List<String> l = new LinkedList<>();
        String[] items = path.split("/");
        for (String item : items) {
            if (item.isEmpty() || item.equals(".")) {
                continue;
            }
            if (item.equals("..")) {
                if (!l.isEmpty()) {
                    l.remove(l.size() - 1);
                }
            } else {
                l.add(item);
            }
        }
        return "/" + String.join("/", l);
    }

    /**
     * 解码方法
     * 思路是动态规划
     * 是有条件的 f(n) = f(n - 1) + f(n - 2)
     * P91
     */
    public int numDecodings(String s) {
        int n = s.length();
        if (n == 0) {
            return 0;
        }
        if (s.charAt(0) == '0') {
            return 0;
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            dp[i + 1] = s.charAt(i) == '0' ? 0 : dp[i];
            if (i > 0 && (s.charAt(i - 1) == '1' || (s.charAt(i - 1) == '2' && s.charAt(i) <= '6'))) {
                dp[i + 1] += dp[i - 1];
            }
        }
        return dp[n];
    }

    /**
     * 编辑距离
     * 思路是动态规划
     * dp[i][j] 表示 word1 的前 i 个字母转换成 word2 的前 j 个字母所使用的最少操作
     * P72
     */
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * 删除字符串中的所有重复项
     * 思路是栈，有点像单调栈的感觉
     * P1047
     */
    public String removeDuplicates(String S) {
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < S.length(); i++) {
            if (stack.isEmpty() || S.charAt(i) != stack.peek()) {
                stack.push(S.charAt(i));
            } else {
                while (!stack.isEmpty() && S.charAt(i) == stack.peek()) {
                    stack.pop();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    /**
     * 复原 IP 地址
     * 思路是回溯
     * P93
     */
    List<String> resForRestoreIpAddresses = new ArrayList<>();
    int[] segments = new int[4];

    public List<String> restoreIpAddresses(String s) {
        backtrackForRestoreIpAddresses(s, 0, 0);
        return resForRestoreIpAddresses;
    }

    public void backtrackForRestoreIpAddresses(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == 4) {
            if (segStart == s.length()) {
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < 4; ++i) {
                    ipAddr.append(segments[i]);
                    if (i != 3) {
                        ipAddr.append('.');
                    }
                }
                resForRestoreIpAddresses.add(ipAddr.toString());
            }
            return;
        }
        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯
        if (segStart == s.length()) {
            return;
        }
        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            backtrackForRestoreIpAddresses(s, segId + 1, segStart + 1);
        }
        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int i = segStart; i < s.length(); i++) {
            addr = addr * 10 + (s.charAt(i) - '0');
            if (addr > 0 && addr <= 255) {
                segments[segId] = addr;
                backtrackForRestoreIpAddresses(s, segId + 1, i + 1);
            } else {
                break;
            }
        }
    }

    /**
     * 电话号码的字母组合
     * 思路是回溯
     * P17
     */
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) {
            return res;
        }
        Map<Character, String> phoneMap = Map.of(
                '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
                '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
        );
        backtrackForLetterCombinations(res, phoneMap, digits, 0, new StringBuffer());
        return res;
    }

    public void backtrackForLetterCombinations(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
            return;
        }
        char digit = digits.charAt(index);
        String letters = phoneMap.get(digit);
        int lettersCount = letters.length();
        for (int i = 0; i < lettersCount; i++) {
            combination.append(letters.charAt(i));
            backtrackForLetterCombinations(combinations, phoneMap, digits, index + 1, combination);
            combination.deleteCharAt(index);
        }
    }

    /**
     * 二进制求和
     * 思路是模拟进位，大数相加也可以用这个思路
     * P67
     */
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int flag = 0;
        StringBuilder sb = new StringBuilder();
        while (i >= 0 || j >= 0) {
            int tmp = 0;
            if (i >= 0) {
                tmp += a.charAt(i--) - '0';
            }
            if (j >= 0) {
                tmp += b.charAt(j--) - '0';
            }
            tmp += flag;
            sb.append(tmp % 2);
            flag = tmp > 1 ? 1 : 0;
        }
        if (flag == 1) {
            sb.append(1);
        }
        // 先逆着加，最后再翻转字符串
        return sb.reverse().toString();
    }

    /**
     * 判断回文串
     * 思路是双指针
     * P125
     */
    public static boolean isPalindrome(String s) {
        int n = s.length();
        if (n == 0) {
            return true;
        }
        int left = 0;
        int right = n - 1;
        while (left < right) {
            if (!Character.isLetterOrDigit(s.charAt(left))) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(s.charAt(right))) {
                right--;
                continue;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            } else {
                left++;
                right--;
            }
        }
        return true;
    }

    /**
     * 最后一个单词的长度
     * 思路是从后向前遍历，添加了一个 flag 标识位
     * P58
     */
    public int lengthOfLastWord(String s) {
        int n = s.length();
        int res = 0;
        boolean flag = false;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                res++;
                flag = true;
            } else if (s.charAt(i) == ' ' && flag) {
                break;
            }
        }
        return res;
    }

    /**
     * Excel表列名称
     * P168
     */
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            if (n % 26 == 0) {
                sb.append('Z');
                n = n / 26 - 1;
            } else {
                sb.append((char) ('A' + n % 26 - 1));
                n /= 26;
            }
        }
        return sb.reverse().toString();
    }

    /**
     * 重复的子字符串
     * 思路是暴力求解
     * P459
     */
    public static boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        // 枚举可能的字串长度
        for (int i = 1; i < n / 2; i++) {
            if (n % i == 0) {
                boolean match = true;
                for (int j = i; j < n; j++) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 字母异位词分组
     * 思路是哈希表
     * P49
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        /*思路1：排序，然后用哈希表来存，时间复杂度O(n * klogk)
        Map<String, List<String>> m = new HashMap<>();
        for (String str : strs) {
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);
            List<String> list = m.getOrDefault(key, new ArrayList<>());
            list.add(str);
            m.put(key, list);
        }
        return new ArrayList<>(m.values());*/

        /*思路2：使用哈希表存自己构造的键而不是排序的键*/
        Map<String, List<String>> m = new HashMap<>();
        for (String str : strs) {
            int[] counts = new int[26];
            for (int i = 0; i < str.length(); i++) {
                counts[str.charAt(i) - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i)).append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> list = m.getOrDefault(key, new ArrayList<>());
            list.add(str);
            m.put(key, list);
        }
        return new ArrayList<>(m.values());
    }

    /**
     * 同构字符串
     * P205
     */
    public boolean isIsomorphic(String s, String t) {
        for (int i = 0; i < s.length(); i++) {
            if (s.indexOf(s.charAt(i)) != t.indexOf(t.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单词规律
     * 思路是采用上一题（P205）的思路
     * P290
     */
    public boolean wordPattern(String pattern, String s) {
        String[] strs = s.split(" ");
        List<String> strList = new ArrayList<>(Arrays.asList(strs));
        if (strs.length != pattern.length()) {
            return false;
        }
        for (int i = 0; i < strs.length; i++) {
            if (pattern.indexOf(pattern.charAt(i)) != strList.indexOf(strList.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 有效的字母异位词
     * P242
     */
    public boolean isAnagram(String s, String t) {
        /*思路1：排序
        if (s.length() != t.length()) {
            return false;
        }
        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);*/

        /*思路2：使用数组做哈希表，保证空间复杂度为O(1)*/
        if (s.length() != t.length()) {
            return false;
        }
        int[] hash = new int[26];
        for (int i = 0; i < s.length(); i++) {
            hash[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            if (--hash[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
    }
}
