package linklist;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制带随机指针的链表
 * 思路是使用 Hash 表存储旧结点和新结点的映射
 * P138
 */
public class NodeWithRandomPointer {
    int val;
    NodeWithRandomPointer next;
    NodeWithRandomPointer random;

    public NodeWithRandomPointer(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }

    public NodeWithRandomPointer copyRandomList(NodeWithRandomPointer head) {
        if (head == null) {
            return null;
        }
        NodeWithRandomPointer node = head;
        Map<NodeWithRandomPointer, NodeWithRandomPointer> map = new HashMap<>();
        while (node != null) {
            NodeWithRandomPointer clone = new NodeWithRandomPointer(node.val);
            map.put(node, clone);
            node = node.next;
        }
        node = head;
        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }
        return map.get(head);
    }
}
