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

    // 递归合并两个升序链表
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {   // 边界条件1
            return l2;
        } else if (l2 == null) {    // 边界条件2
            return l1;
        } else if (l1.val <= l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l2.next, l1);
            return l2;
        }
    }

    /*循环右移 k 位
    * 首先求长度，并将链表连成环
    * 然后根据计数找到最后一个节点，返回 next，并将 next 赋为 null
    * 链表节点的 next 节点不能赋值给另一个节点，只能是指向另一个节点
    * 而树节点可以节点赋值*/
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        int n = 1;
        ListNode tmp = head;
        while (tmp.next != null) {
            n++;
            tmp = tmp.next;
        }
        tmp.next = head;
        k = k % n;
        for (int i = 0; i < n - k; i++) {
            tmp = tmp.next;
        }
        ListNode newHead = tmp.next;
        tmp.next = null;
        return newHead;
    }

    // 节点两两交换
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    // 合并 K 个升序链表
    // 分治法，用到了 mergeTwoLists()
    public static ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        if (n == 0) {
            return null;
        }
        if (n == 1) {
            return lists[0];
        }
        for (int i = 1; i < n; i++) {
            lists[i] = mergeTwoLists(lists[i - 1], lists[i]);
        }
        return lists[n - 1];
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5};
        ListNode listNode1 = createLinkListTail(a);
        printList(reverseKGroup(listNode1, 2));
    }
}
