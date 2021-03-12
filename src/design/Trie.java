package design;

/**
 * 前缀树
 * P208
 */
public class Trie {

    // 前缀树节点静态内部类
    private static class TrieNode {
        // 是否到达末尾
        private boolean isEnd;
        // 每个节点最多有26个不同的小写字母
        private TrieNode[] next;

        public TrieNode() {
            isEnd = false;
            next = new TrieNode[26];
        }
    }

    // 前缀树节点
    private TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.next[index] == null) {
                node.next[index] = new TrieNode();
            }
            node = node.next[index];
        }
        // 加上一个标记，表示为一个单词
        node.isEnd = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.next[index] == null) {
                return false;
            }
            node = node.next[index];
        }
        return node.isEnd;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            // 若还没遍历完给定的前缀子串，则直接返回 false
            if (node.next[index] == null) {
                return false;
            }
            node = node.next[index];
        }
        return true;
    }
}
/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */