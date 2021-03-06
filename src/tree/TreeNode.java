package tree;

import java.util.*;

public class TreeNode {
    // 二叉树定义
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }


    /******************************二叉树遍历*************************************/


    // 先序遍历递归
    public static void preOrderRecursive(TreeNode root, List<Integer> res) {
        if (root != null) {
            preOrderRecursive(root.left, res);
            preOrderRecursive(root.right, res);
            res.add(root.val);
        }
    }

    /**
     * 先序遍历迭代实现，用到了栈
     * 思路是用一个栈来保存节点，出栈时访问
     * 因为栈先进后出，因此先将节点右孩子进栈，这一点一定要注意
     * 当栈不为空时循环上述过程
     */
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
    public static void inOrderRecursive(TreeNode root, List<Integer> res) {
        if (root != null) {
            inOrderRecursive(root.left, res);
            res.add(root.val);
            inOrderRecursive(root.right, res);
        }
    }

    /**
     * 中序遍历迭代实现，同样用到了栈
     * 首先左链进栈，然后弹出栈顶元素并访问
     * 然后将 root 设为右孩子，继续循环
     */
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

    /**
     * 后序遍历的迭代实现，同样用到了栈
     * 与中序遍历有相同的地方，都先左链进栈
     * 不同点是后序遍历需要一个 prev 保存上一次访问的节点
     * 并且当右孩子非空且没有访问过时，会将 root 再次进栈
     */
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
            // 一定要提前计算 size，因为在循环中会改变队列，即会改变 size
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode tmp = queue.poll();
                level.add(tmp.val);
                if (tmp.left != null) {
                    queue.offer(tmp.left);
                }
                if (tmp.right != null) {
                    queue.offer(tmp.right);
                }
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

    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            // 一定要提前计算 size，因为在循环中会改变队列，即会改变 size
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode tmp = queue.poll();
                level.add(tmp.val);
                if (tmp.left != null) {
                    queue.offer(tmp.left);
                }
                if (tmp.right != null) {
                    queue.offer(tmp.right);
                }
            }
            res.add(level);
        }
        Collections.reverse(res);
        return res;
    }

    /**
     * 锯齿形层级遍历二叉树
     * 思路是将层级遍历得到的奇数层的 List 翻转一下
     * 翻转用到了经典的滑动窗法
     * P103
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        int height = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }

            if (height % 2 == 1) {
                int left = 0;
                int right = level.size() - 1;
                while (left < right) {
                    int tmp = level.get(right);
                    level.set(right--, level.get(left));
                    level.set(left++, tmp);
                }
            }
            res.add(level);
            height++;
        }
        return res;
    }

    /**
     * 深度优先遍历二叉树
     * 需要在外面定义 res 的 List
     * 广度优先就是层级遍历，因此不做过多讨论
     * 参考文章：https://blog.csdn.net/qq_37638061/article/details/89598413
     */
    private static List<Integer> resForDfsTraverse = new ArrayList<>();

    public static List<Integer> dfs(TreeNode root) {
        if (root == null) {
            return null;
        }
        resForDfsTraverse.add(root.val);
        dfs(root.left);
        dfs(root.right);
        return resForDfsTraverse;
    }

    /**
     * 深度优先遍历二叉树的应用
     * 打印所有的到叶子节点的路径
     */
    private static List<List<Integer>> resForGetAllPath = new LinkedList<>();
    private static Deque<Integer> pathForGetAllPath = new LinkedList<>();

    public static List<List<Integer>> getAllPath(TreeNode root) {
        if (root == null) {
            return null;
        }
        pathForGetAllPath.offerLast(root.val);
        // 判断是否为叶子节点
        if (root.left == null && root.right == null) {
            resForGetAllPath.add(new LinkedList<>(pathForGetAllPath));
        }
        getAllPath(root.left);
        getAllPath(root.right);
        pathForGetAllPath.pollLast();
        return resForGetAllPath;
    }

    /**
     * 二叉树的右视图
     * 思路是层级遍历，保留每层最后一个元素即可
     * P199
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            res.add(level.get(level.size() - 1));
        }
        return res;
    }


    /******************************二叉树操作*************************************/


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

    /**
     * 求二叉树最大深度
     * P104
     */
    public static int maxDepth(TreeNode root) {
        // 递归出口1
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * 求二叉树最小深度
     * 与求最大深度的不同之处在于如果左孩子或右孩子为空，为空的孩子孩子不是叶子节点
     * 因此不能直接使用1 + min(left, right) 公式计算，当左右孩子都不为空时才能这样计算
     * P111
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right != null) {
            return 1 + minDepth(root.right);
        }
        if (root.left != null && root.right == null) {
            return 1 + minDepth(root.left);
        }
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
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

    /**
     * 求两节点的最近公共祖先
     * P236
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
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

    /**
     * 从先序遍历和中序遍历数组重建出二叉树
     */
    public TreeNode buildTreeFromPreAndIn(int[] preorder, int[] inorder) {
        int n = preorder.length;
        if (n == 0) {
            return null;
        }
        // 创建根节点
        TreeNode root = new TreeNode(preorder[0]);
        for (int i = 0; i < n; i++) {
            // 找到中序遍历中的根节点，然后对左右两侧分别递归
            if (preorder[0] == inorder[i]) {
                root.left = buildTreeFromPreAndIn(
                        Arrays.copyOfRange(preorder, 1, i + 1),
                        Arrays.copyOfRange(inorder, 0, i));
                root.right = buildTreeFromPreAndIn(
                        Arrays.copyOfRange(preorder, i + 1, n),
                        Arrays.copyOfRange(inorder, i + 1, n));
                break;
            }
        }
        return root;
    }

    /**
     * 递归实现从后序遍历和中序遍历数组重建出二叉树
     */
    public TreeNode buildTreeFromPostAndIn(int[] postorder, int[] inorder) {
        int n = postorder.length;
        if (n == 0) {
            return null;
        }
        // 创建根节点
        TreeNode root = new TreeNode(postorder[n - 1]);
        for (int i = 0; i < n; i++) {
            // 找到中序遍历中的根节点，然后对左右两侧分别递归
            if (postorder[n - 1] == inorder[i]) {
                root.left = buildTreeFromPostAndIn(
                        Arrays.copyOfRange(postorder, 0, i),
                        Arrays.copyOfRange(inorder, 0, i));
                root.right = buildTreeFromPostAndIn(
                        Arrays.copyOfRange(postorder, i, n - 1),
                        Arrays.copyOfRange(inorder, i + 1, n));
                break;
            }
        }
        return root;
    }

    /**
     * 二叉树中的最大路径和
     * 全局变量 maxSum 用于记录递归中更新的结果
     * maxGain() 方法递归更新结果
     * P124
     */
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    public int maxGain(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftGain = Math.max(maxGain(root.left), 0);
        int rightGain = Math.max(maxGain(root.right), 0);
        int totalGain = root.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, totalGain);
        return root.val + Math.max(leftGain, rightGain);
    }

    /**
     * 将二叉树展开为链表
     * 思路是递归
     * P114
     */
    public void flatten(TreeNode root) {
        // 递归出口
        if (root == null) {
            return;
        }
        // 递归调用
        flatten(root.left);
        flatten(root.right);
        // 实际操作
        TreeNode right = root.right;
        root.right = root.left;
        // 别忘了将左子树置空
        root.left = null;
        TreeNode tmp = root;
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        tmp.right = right;
    }

    /**
     * 路径总和（1）
     * P112
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        // 只在叶子节点判断
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * 路径总和(2)
     * 思路是使用深度优先遍历二叉树
     * P113
     */
    List<List<Integer>> resForPathSum = new LinkedList<>();
    Deque<Integer> pathForPathSum = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfsForPathSum(root, targetSum);
        return resForPathSum;
    }

    public void dfsForPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        pathForPathSum.offerLast(root.val);
        targetSum -= root.val;
        if (root.left == null && root.right == null && targetSum == 0) {
            resForPathSum.add(new LinkedList<>(pathForPathSum));
        }
        dfsForPathSum(root.left, targetSum);
        dfsForPathSum(root.right, targetSum);
        pathForPathSum.pollLast();
    }

    /**
     * 二叉树的所有路径
     * 思路是 dfs 递归遍历
     * P257
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfsForBinaryTreePaths(root, res, new StringBuilder());
        return res;
    }

    public void dfsForBinaryTreePaths(TreeNode root, List<String> res, StringBuilder sb) {
        if (root == null) {
            return;
        }
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            res.add(sb.toString());
        } else {
            // 注意这里要用 new StringBuilder
            // 不然传递的是引用，后续的递归会影响到之前的结果
            dfsForBinaryTreePaths(root.left, res, new StringBuilder(sb).append("->"));
            dfsForBinaryTreePaths(root.right, res, new StringBuilder(sb).append("->"));
        }
    }

    /**
     * 二叉树的直径
     * 思路是找左右子树高度和最大的节点，并返回其左右子树高度和
     * 使用递归计算高度，并更新 res
     * P543
     */
    private int resForDiameterOfBinaryTree = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        depthForDiameterOfBinaryTree(root);
        return resForDiameterOfBinaryTree;
    }

    public int depthForDiameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = depthForDiameterOfBinaryTree(root.left);
        int rightDepth = depthForDiameterOfBinaryTree(root.right);
        resForDiameterOfBinaryTree = Math.max(resForDiameterOfBinaryTree, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 判断是否为完全二叉树
     * 思路是层级遍历，遍历到空节点则置 flag为 true
     * 如果在 flag 为 true 时又遍历到其他节点，则不是完全二叉树
     * P958
     */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                flag = true;
                continue;
            }
            if (flag) {
                return false;
            }
            queue.offer(node.left);
            queue.offer(node.right);
        }
        return true;
    }

    /**
     * 根到叶子节点数字之和
     * 思路是深度优先遍历
     * P129
     */
    public int sumNumbers(TreeNode root) {
        return dfsForSumNumbers(root, 0);
    }

    public int dfsForSumNumbers(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        sum = sum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        } else {
            return dfsForSumNumbers(root.left, sum) + dfsForSumNumbers(root.right, sum);
        }
    }

    /**
     * 所有节点之和
     */
    public int sumOfNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return root.val + sumOfNodes(root.left) + sumOfNodes(root.right);
    }

    /**
     * 所有叶子节点之和
     */
    public int sumOfLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        }
        return sumOfLeaves(root.left) + sumOfLeaves(root.right);
    }

    /**
     * 左叶子节点之和
     * 加个判断记录左叶子节点的值即可
     * P404
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int tmp = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {
            tmp = root.left.val;
        }
        return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right) + tmp;
    }

    /**
     * 二叉树的层平均值
     * P637
     */
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return new LinkedList<>();
        }
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int size;
        double sum;
        TreeNode tmp;
        while (!q.isEmpty()) {
            size = q.size();
            sum = 0;
            for (int i = 0; i < size; i++) {
                tmp = q.poll();
                assert tmp != null;
                if (tmp.left != null) {
                    q.offer(tmp.left);
                }
                if (tmp.right != null) {
                    q.offer(tmp.right);
                }
                sum += tmp.val;
            }
            res.add(sum * 1.0 / size);
        }
        return res;
    }

    /**
     * 二叉树的节点个数
     * P222
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    /**
     * 判断相同的树
     * P100
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 不相邻节点和的最大值
     * P337
     */
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = root.val;
        if (root.left != null) {
            res += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
            res += rob(root.right.left) + rob(root.right.right);
        }
        return Math.max(res, rob(root.left) + rob(root.right));
    }

    /******************************二叉排序树*************************************/


    /**
     * 判断一颗二叉树是不是二叉排序树
     * 二叉排序树(Binary Sort Tree, BST),又叫二叉搜索树(Binary Search Tree, BST)
     * 性质如下
     * 节点的左子树只包含小于当前节点的数
     * 节点的右子树只包含大于当前节点的数
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * 判断思路是对该二叉树中序遍历，如果得到的结果是递增的则是二叉排序树
     */
    public boolean isValidBST(TreeNode root) {
        List<Integer> inOrderList = new ArrayList<>();
        inOrderRecursive(root, inOrderList);
        if (inOrderList.size() == 1) {
            return true;
        }
        int tmp = inOrderList.get(0);
        for (int i = 1; i < inOrderList.size(); i++) {
            if (inOrderList.get(i) > tmp) {
                tmp = inOrderList.get(i);
            } else {
                return false;
            }
        }
        return true;
    }

    // 递归实现向 BST 插入节点
    public static TreeNode insertNodeToBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val < root.val) {
            root.left = insertNodeToBST(root.left, val);
        } else {
            root.right = insertNodeToBST(root.right, val);
        }
        return root;
    }

    // 递归实现从 BST 删除节点
    public TreeNode deleteNodeFromBST(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNodeFromBST(root.left, key);
            return root;
        } else if (key > root.val) {
            root.right = deleteNodeFromBST(root.right, key);
            return root;
        } else {
            TreeNode left = root.left;
            TreeNode right = root.right;
            /*如果 left 节点不为空，则找到 left 子树的最大值节点
             * 使 right 子树作为该节点的右子树
             * 如果 left 节点为空，则直接返回 right 节点即可*/
            if (left != null) {
                while (left.right != null) {
                    left = left.right;
                }
                left.right = right;
                return root.left;
            } else {
                return right;
            }
        }
    }

    /**
     * 计算组成二叉排序树的数量
     * 思路是二叉排序树只要规定节点个数则排列数量就确定了
     * 遍历每个节点，其左子树的排列数量加右子树的排列数量就是该节点的数量，加起来即可
     * P96
     */
    public static int numTrees(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += numTrees(i - 1) * numTrees(n - i);
        }
        return res;
    }

    /**
     * 二叉搜索树中第 K 小的元素
     * 思路是利用 BST 的中序遍历序列是升序的特性
     * P230
     */
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> res = new ArrayList<>();
        inOrderForKthSmallest(root, res);
        return res.get(k - 1);
    }

    public void inOrderForKthSmallest(TreeNode root, List<Integer> res) {
        if (root != null) {
            inOrderForKthSmallest(root.left, res);
            res.add(root.val);
            inOrderForKthSmallest(root.right, res);
        }
    }

    /**
     * 将有序数组转为高度平衡的 BST
     * 思路是贪心，可以选择中间数字作为 BST 的根节点
     * 这样分给左右子树的数字个数相同或只相差 1
     * P108
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums, left, mid));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums, mid + 1, right + 1));
        return root;
    }

    /**
     * 二叉搜索树中两节点的最近公共祖先
     * 利用 BST 的性质做递归很简单，也可以用普通二叉树的最近祖先求法
     * P235
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q || (root.val - p.val) * (root.val - q.val) < 0) {
            return root;
        }
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestorBST(root.left, p, q);
        }
        return lowestCommonAncestorBST(root.right, p, q);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode7 = new TreeNode(7);
        TreeNode treeNode8 = new TreeNode(8);
        TreeNode treeNode9 = new TreeNode(9);
        TreeNode treeNode10 = new TreeNode(10);
        TreeNode treeNode11 = new TreeNode(11);
        TreeNode treeNode12 = new TreeNode(12);

        root.left = treeNode2;
        root.right = treeNode3;

        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;

        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;

        treeNode4.left = treeNode8;
        treeNode5.left = treeNode9;
        treeNode6.left = treeNode10;

        treeNode7.left = treeNode11;
        treeNode7.right = treeNode12;

//        List<Integer> result = dfs(root);
//        System.out.println(result);

        System.out.println(getAllPath(root));
    }
}
