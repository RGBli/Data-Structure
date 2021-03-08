package tree;


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**填充每个节点的下一个右侧节点指针
 * P116，P117*/
public class NodeWithNextPointer {
    public int val;
    public NodeWithNextPointer left;
    public NodeWithNextPointer right;
    public NodeWithNextPointer next;

    public NodeWithNextPointer() {}

    public NodeWithNextPointer(int _val) {
        val = _val;
    }

    public NodeWithNextPointer(int _val, NodeWithNextPointer _left, NodeWithNextPointer _right, NodeWithNextPointer _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

    /*给定一个完美二叉树，定义是其所有叶子节点都在同一层，每个父节点都有两个子节点
    * 思路是递归
    * 是下一题的特例，也可以直接使用下一题的代码*/
    public NodeWithNextPointer connect1(NodeWithNextPointer root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root;
        }
        root.left.next = root.right;
        if (root.next != null) {
            root.right.next = root.next.left;
        }
        connect1(root.left);
        connect1(root.right);
        return root;
    }

    /*不是完美二叉树
    * 思路是层级遍历*/
    public NodeWithNextPointer connect2(NodeWithNextPointer root) {
        if (root == null) {
            return null;
        }
        Deque<NodeWithNextPointer> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            // last 记录层级遍历中上次遍历的节点
            NodeWithNextPointer last = null;
            for (int i = 0; i < size; i++) {
                NodeWithNextPointer node = q.poll();
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
                if (i != 0) {
                    last.next = node;
                }
                last = node;
            }
        }
        return root;
    }
}
