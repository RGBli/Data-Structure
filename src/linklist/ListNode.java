package linklist;

import java.util.*;

public class ListNode {
    // 链表定义
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int x) {
        this.val = x;
    }

    public ListNode(int x, ListNode next) {
        this.val = x;
        this.next = next;
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

    /**
     * 链表反转
     * 三指针法，迭代实现
     * 时间复杂度 O(n)，空间复杂度 O(1)
     * P206
     */
    public static ListNode reverseListIterative(ListNode head) {
        ListNode p1 = null;
        ListNode p2 = head;
        ListNode p3;
        while (p2 != null) {
            // 让 p3 存储下一次 p2 的位置
            p3 = p2.next;
            // 开始反转 p1 和 p2
            p2.next = p1;
            // 将 p1 和 p2 都向后移动
            p1 = p2;
            p2 = p3;
        }
        return p1;
    }

    /**
     * 链表反转
     * 递归实现，时间复杂度 O(n)，空间复杂度 O(n)
     * P206
     */
    public ListNode reverseListRecursive(ListNode head) {
        // 递归出口的写法需要注意
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 链表反转，仅反转从位置 m 到 n 的部分
     * P92
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }

    /**
     * K 个一组翻转链表
     * P26
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        ListNode cur = head;
        ListNode next;
        // 先求链表长度
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        for (int i = 0; i < len / k; i++) {
            for (int j = 0; j < k - 1; j++) {
                next = cur.next;
                cur.next = next.next;
                next.next = pre.next;
                pre.next = next;
            }
            pre = cur;
            cur = cur.next;
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

    /**
     * 循环右移 k 位
     * 首先求长度，并将链表连成环
     * 然后根据计数找到最后一个节点，返回 next，并将 next 赋为 null
     * 链表节点的 next 节点不能赋值给另一个节点，只能是指向另一个节点
     * 而树节点可以节点赋值
     */
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

    /**
     * 判断回文链表
     * 首先找到链表中点
     * 然后翻转中点之后的节点，与从 head 开始的节点值相比较
     * P234
     */
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

    /**
     * 判断链表中是否有环
     * P141
     */
    public boolean hasCycle(ListNode head) {
        /*方法1:哈希表
         * 用哈希表记录之前遍历过的节点，如果再次遍历则就是入环节点
        Set<ListNode> set = new HashSet<>();
        ListNode tmp = head;
        while (tmp != null && !set.contains(tmp)) {
            set.add(tmp);
            tmp = tmp.next;
        }
        return tmp != null;*/

        /*方法2:快慢指针
         * slow 指针每次前进一步，fast 指针前进两步
         * 如果 fast 到头则返回 false，如果相遇则返回 true*/
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        // 使用 do-while 的原因是最开始 slow = fast，因此无法循环
        do {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);
        return true;
    }

    /**
     * 检测环形链表的入环节点
     * P142
     */
    public ListNode detectCycle(ListNode head) {
        /*方法1:哈希表
        * 用哈希表记录之前遍历过的节点，如果再次遍历则就是入环节点
        Set<ListNode> set = new HashSet<>();
        ListNode tmp = head;
        while (tmp != null && !set.contains(tmp)) {
            set.add(tmp);
            tmp = tmp.next;
        }
        return tmp;*/

        /*方法2:快慢指针*/
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        do {
            // 无环，直接返回
            if (fast == null || fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);
        ListNode tmp = head;
        while (tmp != slow) {
            tmp = tmp.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 找到两个链表相交的节点
     * P160
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != p2) {
            if (p1 != null) {
                p1 = p1.next;
            } else {
                p1 = headB;
            }

            if (p2 != null) {
                p2 = p2.next;
            } else {
                p2 = headA;
            }
        }
        return p1;
    }

    /**
     * 重排链表
     * 首先找到中点
     * 然后逆序第二段链表（中点之后的部分）
     * 然后将第二段链表合并到第一段中
     * P143
     */
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

    /**
     * 寻找链表的中间节点
     * 思路是快慢指针，slow 每次前进一次，fast 每次前进两次
     * 注意：节点数为偶数时以下代码返回后面的那个，如果想返回前面那个则使用注释中的 while 语句
     * 并且需要额外判断 head 是否为空
     */
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

    /**
     * 链表排序
     * 思路是递归实现归并排序
     * 首先找到中点，然后分别对第一段和第二段递归排序
     * 最后调用合并两个有序链表的方法进行合并
     * P148
     */
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = sortList(slow.next);
        slow.next = null;
        ListNode left = sortList(head);
        return mergeTwoLists(left, right);
    }

    /**
     * 删除排序链表中的重复元素
     * P83
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode curr = head.next;
        while (true) {
            while (curr != null && curr.val == prev.val) {
                curr = curr.next;
            }
            prev.next = curr;
            prev = prev.next;
            // 判断是否因为遍历遍历完成还是不相等而退出的循环
            if (curr != null) {
                curr = curr.next;
            } else {
                break;
            }
        }
        return head;
    }

    /**
     * 奇偶链表
     * P328
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode evenHead = head.next;
        ListNode odd = head;
        ListNode even = head.next;
        while (odd.next != null && even.next != null) {
            odd.next = odd.next.next;
            odd = odd.next;
            even.next = even.next.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * 分隔链表
     * 思路是创建了两个链表节点 small 和 large，分别存储小于 x 的和大于等于 x 的元素
     * 然后将这两个链表拼接起来即可
     * P86
     */
    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(0);
        ListNode smallHead = small;
        ListNode large = new ListNode(0);
        ListNode largeHead = large;
        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null;
        small.next = largeHead.next;
        return smallHead.next;
    }

    /**
     * 链表插入排序
     * 思路跟数组的插入排序一样，就是多了一步从头结点向后查找比 i 大的元素
     * i 和 j 的含义与数组的插入排序相同
     * P147
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode j = head;
        ListNode i = head.next;
        while (i != null) {
            if (i.val >= j.val) {
                j = j.next;
            } else {
                // prev.next 记录有序区中第一个大于等于 i 节点值的节点
                ListNode prev = dummyHead;
                while (prev.next.val < i.val) {
                    prev = prev.next;
                }
                // 这三行逻辑非常重要
                j.next = i.next;
                i.next = prev.next;
                prev.next = i;
            }
            i = j.next;
        }
        return dummyHead.next;
    }

    /**
     * 移除链表中特定值的元素
     * 用了头节点
     * P203
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode tmp = dummy;
        while (tmp.next != null) {
            if (tmp.next.val == val) {
                tmp.next = tmp.next.next;
            } else {
                tmp = tmp.next;
            }
        }
        return dummy.next;
    }

    /**
     * 删除除末尾节点的给定节点
     * 没有给 head 节点，只需要修改目标节点值为 next 的值，再删除 next 节点即可
     * P237
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }


    public static void main(String[] args) {
        int[] a = new int[]{4, 3, 2, 1};
        ListNode listNode1 = createLinkListTail(a);
        ListNode p1 = listNode1;
        ListNode p2 = p1;
        p1.val = 5;
        System.out.println(listNode1.val);
    }
}
