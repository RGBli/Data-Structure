package linklist;

public class ListNode {

    public int val;
    public ListNode next;

    public ListNode() {}

    public ListNode(int x) {
        this.val = x;
    }

    // 头插法创建链表
    public static ListNode createLinkListHead(int[] a) {
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

    public static int getListLen(ListNode listNode) {
        int size = 0;
        while (listNode != null) {
            size++;
            listNode = listNode.next;
        }
        return size;
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        int size = 0;
        ListNode tmp = head;
        while (tmp != null) {
            size++;
            tmp = tmp.next;
        }

        System.out.println(size);

        if (size == n) {
            return head.next;
        }

        int cnt = 0;
        tmp = head;
        while (cnt < size - n - 1) {
            cnt++;
            tmp = tmp.next;
        }
        tmp.next = tmp.next.next;
        return head;
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p3 = new ListNode();
        while (p2 != null) {
            p3 = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = p3;
        }
        head.next = null;
        return p1;
    }

    public static ListNode reverseListFirstN(ListNode head, int n) {
        if (head == null || head.next == null || n == 1) {
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
        head.next = null;
        return p1;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5};
        ListNode listNode = createLinkListTail(a);
        ListNode.printList(reverseListFirstN(listNode, 3));
    }
}
