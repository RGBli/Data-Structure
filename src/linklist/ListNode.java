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

    /*链表反转，并返回翻转后链表的第一个节点
    * 三指针法，迭代实现
    * 时间复杂度 O(n)，空间复杂度 O(1)
    * P206*/
    public static ListNode reverseListIterative(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3;
        while (p2 != null) {
            p3 = p2.next;
            // 切断 p2 和 p3的联系
            p2.next = p1;
            // 将 p2 和 p3 都向后移动一次
            p1 = p2;
            p2 = p3;
        }
        // 使 head 成为最后一个节点
        head.next = null;
        return p1;
    }

    /*链表反转，并返回翻转后链表的第一个节点
     * 递归实现，时间复杂度 O(n)，空间复杂度 O(n)
     * P206*/
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
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
        ListNode p3;
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

    /*判断回文链表
    * 首先找到链表中点
    * 然后翻转中点之后的节点，与从 head 开始的节点值相比较
    * P234*/
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        // 找中点的方法，很巧妙
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 翻转中点之后的节点
        slow = reverseListRecursive(slow.next);
        while (slow != null) {
            if (slow.val != head.val) {
                return false;
            }
            slow = slow.next;
            head = head.next;
        }
        return true;
    }

    /*判断链表中是否有环
    * 用到了快慢指针的方法
    * P141*/
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /*找到两个链表相交的节点
    * P160*/
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != p2){
            if (p1 != null){
                p1 = p1.next;
            } else {
                p1 = headB;
            }

            if (p2 != null){
                p2 = p2.next;
            } else {
                p2 = headA;
            }
        }
        return p1;
    }

    /*重排链表
    * 首先找到中点
    * 然后逆序第二段链表（中点之后的部分）
    * 然后将第二段链表合并到第一段中
    * P143*/
    public static void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = slow.next;
        ListNode first = head;
        slow.next = null;
        second = reverseListIterative(second);

        ListNode tmp1;
        ListNode tmp2;
        while (first != null && second != null) {
            tmp1 = first.next;
            tmp2 = second.next;
            first.next = second;
            first.next.next = tmp1;
            first = tmp1;
            second = tmp2;
        }
    }

    /*寻找链表的中间节点
    * 思路是快慢指针，slow 每次前进一次，fast 每次前进两次
    * 注意：节点数为偶数时以下代码返回后面的那个，如果想返回前面那个则使用注释中的 while 语句
    * 并且需要额外判断 head 是否为空*/
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        // while (fast.next != null && fast.next.next != null)
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4};
        ListNode listNode1 = createLinkListTail(a);
        //printList(listNode1);
        reorderList(listNode1);
        printList(listNode1);
    }
}
