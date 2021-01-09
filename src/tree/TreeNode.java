package tree;

import linklist.ListNode;

import java.util.*;

public class TreeNode {
    // 二叉树定义
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }


    // 先序遍历递归
    public void preOrderRecursive(TreeNode root, List<Integer> res) {
        if (root != null) {
            preOrderRecursive(root.left, res);
            preOrderRecursive(root.right, res);
            res.add(root.val);
        }
    }

    /*先序遍历迭代实现，用到了栈
    * 思路是用一个栈来保存节点，出栈时访问
    * 因为栈先进后出，因此先将节点右孩子进栈，这一点一定要注意
    * 当栈不为空时循环上述过程*/
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            // 当左右孩子不为空时进栈
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return res;
    }


    // 中序遍历递归
    public void inOrderRecursive(TreeNode root, List<Integer> res) {
        if (root != null) {
            inOrderRecursive(root.left, res);
            res.add(root.val);
            inOrderRecursive(root.right, res);
        }
    }

    /*中序遍历迭代实现，同样用到了栈
    * 首先左链进栈，然后弹出栈顶元素并访问
    * 然后将 root 设为右孩子，继续循环*/
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        // 多了一个 root 不为空的判断，因为并没有先入栈
        while (root != null || !stack.isEmpty()) {
            // 左链进栈
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 弹出并访问
            root = stack.pop();
            res.add(root.val);
            // 设 root 为右孩子
            root = root.right;
        }
        return res;
    }

    // 后序遍历递归
    public void postOrderRecursive(TreeNode root, List<Integer> res) {
        if (root != null) {
            postOrderRecursive(root.left, res);
            postOrderRecursive(root.right, res);
            res.add(root.val);
        }
    }

    /*后序遍历的迭代实现，同样用到了栈
    * 与中序遍历有相同的地方，都先左链进栈
    * 不同点是后序遍历需要一个 prev 保存上一次访问的节点
    * 并且当右孩子非空且没有访问过时，会将 root 再次进栈*/
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();

            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            } else {
                // root 再次入栈
                stack.push(root);
                root = root.right;
            }
        }
        return res;
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
