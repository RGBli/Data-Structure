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
            List<Integer> level = new ArrayList<>();
            for (int size = 0; size < queue.size(); size++) {
                TreeNode tmp = queue.poll();
                if (tmp.left != null) {
                    queue.offer(tmp.left);
                }
                if (tmp.right != null) {
                    queue.offer(tmp.right);
                }
                level.add(tmp.val);
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

    // 递归判断二叉树是不是镜像对称
    public static boolean isSymmetricRecursive(TreeNode root) {
        return isMirror(root, root);
    }

    // 递归判断二叉树是不是镜像对称的实现方法
    public static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null || t1.val != t2.val) {
            return false;
        }
        return isMirror(t1.left, t2.right)
                && isMirror(t1.right, t2.left);
    }

    // 迭代判断二叉树是不是镜像对称
    // 思路是将每个节点的左右孩子节点入队，两个两个出队，
    // 这样取到的两个节点就是一个节点的两个孩子节点，然后再对这两个节点进行比较
    public static boolean isSymmetricIterative(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root.left);
        q.offer(root.right);
        while (!q.isEmpty()) {
            TreeNode leftNode = q.poll();
            TreeNode rightNode = q.poll();
            if (leftNode == null && rightNode == null) {
                continue;
            }
            if (leftNode == null || rightNode == null || leftNode.val != rightNode.val) {
                return false;
            }
            q.offer(leftNode.left);
            q.offer(rightNode.right);
            q.offer(leftNode.right);
            q.offer(rightNode.left);
        }
        return true;
    }

    // 求二叉树最大深度
    public static int maxDepth(TreeNode root) {
        // 递归出口1
        if (root == null) {
            return 0;
        }
        // 递归出口2
        if (root.left == null && root.right == null) {
            return 1;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // 翻转二叉树
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        // 二叉树交换节点
        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    // 求两节点的最近公共祖先
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode leftNode = lowestCommonAncestor(root.left, p, q);
        TreeNode rightNode = lowestCommonAncestor(root.right, p, q);
        // p q 在节点 node 两侧的情况
        if (leftNode != null && rightNode != null) {
            return root;
        }
        // p q 在节点 node 一侧的情况
        return leftNode == null ? rightNode : leftNode;
    }

    // 循环删除所有具有 target 值的叶子节点
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);
        if (root.left == null && root.right == null && root.val == target) {
            return null;
        }
        return root;
    }
}
