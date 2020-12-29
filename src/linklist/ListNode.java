package linklist;

import java.util.ArrayList;
import java.util.List;

public class ListNode {
    // 链表定义
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int x) {
        this.val = x;
    }

    // 头插一个结点
    public static ListNode lPushNode(ListNode head, int a) {
        ListNode p = new ListNode();
        p.val = a;
        p.next = head;
        return p;
    }

    // 尾插一个结点
    public static ListNode rPushNode(ListNode head, int a) {
        ListNode p = new ListNode();
        p.val = a;
        p.next = null;
        if (head == null) {
            return p;
        } else {
            ListNode tmp = head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = p;
        }
        return head;
    }

    // 头插法创建链表
    public static ListNode createLinkListHead(int[] a) {
        // 这里的 head 是头结点
        ListNode head = new ListNode();
        for (int i : a) {
            ListNode tmp = new ListNode(i);
            tmp.next = head.next;
            head.next = tmp;
        }
        return head.next;
    }

    // 尾插法创建链表
    public static ListNode createLinkListTail(int[] a) {
        // 这里的 head 是头结点
        ListNode head = new ListNode();

        // 传引用
        ListNode tail = head;
        for (int i : a) {
            ListNode tmp = new ListNode(i);
            tail.next = tmp;
            tail = tmp;
        }
        return head.next;
    }

    // 顺序打印链表
    public static void printList(ListNode listNode) {
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    // 获取链表长度
    public static int getListLen(ListNode listNode) {
        int size = 0;
        while (listNode != null) {
            size++;
            listNode = listNode.next;
        }
        return size;
    }

    // 删除倒数第 n 个节点
    // 快慢指针
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            return head.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return head;
    }

    // 链表反转
    // 三指针法
    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = new ListNode();
        while (p2 != null) {
            p3 = p2.next;
            // 切断 p2 和 p3的联系
            p2.next = p1;
            // 将 p2 和 p3 都向后移动一次
            p1 = p2;
            p2 = p3;
        }
        head.next = null;
        return p1;
    }

    // 反转前 n 个节点
    // 三指针法
    public static ListNode reverseListFirstN(ListNode head, int n) {
        if (head == null || n == 1) {
            return head;
        }

        int i = 1;
        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = new ListNode();
        while (p2 != null && i + 1 <= n) {
            p3 = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = p3;
            i++;
        }
        head.next = p2;
        return p1;
    }

    // k 个一组反转链表，入果节点总数不是 k 的整数倍，将最后剩余的节点保持原有顺序
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0), prev = dummy, curr = head, next;
        dummy.next = head;
        int length = 0;
        while(head != null) {
            length++;
            head = head.next;
        }
        head = dummy.next;
        for(int i = 0; i < length / k; i++) {
            for(int j = 0; j < k - 1; j++) {
                next = curr.next;
                curr.next = next.next;
                next.next = prev.next;
                prev.next = next;
            }
            prev = curr;
            curr = curr.next;
        }
        return dummy.next;
    }

    // 合并两个升序链表
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        ListNode res = new ListNode();
        ListNode tmp = res;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tmp.next = new ListNode(l1.val);
                tmp = tmp.next;
                l1 = l1.next;
            } else {
                tmp.next = new ListNode(l2.val);
                tmp = tmp.next;
                l2 = l2.next;
            }
        }

        if (l1 == null) {
            while (l2 != null) {
                tmp.next = l2;
                tmp = tmp.next;
                l2 = l2.next;
            }
        } else {
            while (l1 != null) {
                tmp.next = l1;
                tmp = tmp.next;
                l1 = l1.next;
            }
        }
        return res.next;
    }

    // 循环右移 k 位
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        int size = 0;
        ListNode tmp = head;
        while (tmp != null) {
            size++;
            tmp = tmp.next;
        }

        k = k % size;
        if (size == 1 || k == 0) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode newHead = slow.next;
        slow.next = null;
        tmp = newHead;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = head;
        return newHead;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5};
        ListNode listNode1 = createLinkListTail(a);
        printList(reverseKGroup(listNode1, 2));
    }
}
