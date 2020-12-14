package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode {
    // 二叉树定义
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }

    // 层次遍历二叉树
    // 每一层都是一个 List
    public List<List<Integer>> levelOrder1(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            while (size > 0) {
                TreeNode tmp = queue.poll();
                level.add(tmp.val);
                if (tmp.left != null) {
                    queue.offer(tmp.left);
                }
                if (tmp.right != null) {
                    queue.offer(tmp.right);
                }
                size--;
            }
            res.add(level);
        }
        return res;
    }


    // 层次遍历二叉树
    // 只有一个 List
    public List<Integer> levelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode tmp = queue.poll();
            res.add(tmp.val);
            if (tmp.left != null) {
                queue.offer(tmp.left);
            }
            if (tmp.right != null) {
                queue.offer(tmp.right);
            }
        }
        return res;
    }


    // 判断二叉树是否是平衡二叉树
    public static boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    // 用于统计树高, 如果不平衡则返回 -1
    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lChildHeight = height(root.left);
        int rChildHeight = height(root.right);
        // 左右子树都平衡, 并且高度差不大于1, 则返回该结点的高度
        // 否则该节点不是平衡二叉树, 返回-1
        if (lChildHeight != -1 && rChildHeight != -1 && Math.abs(lChildHeight - rChildHeight) <= 1) {
            return 1 + Math.max(lChildHeight, rChildHeight);
        } else {
            return -1;
        }
    }
}
