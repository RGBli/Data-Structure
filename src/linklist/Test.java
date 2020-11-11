package linklist;

public class Test {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        ListNode res = new ListNode();
        ListNode tmp = res;
        while (l1 != null || l2 != null) {
            if (l1.val <= l2.val) {
                tmp.next = new ListNode(l1.val);
                tmp = tmp.next;
                if (l1.next != null) {
                    l1 = l1.next;
                } else {
                    break;
                }
            } else {
                tmp.next = new ListNode(l2.val);
                tmp = tmp.next;
                if (l2.next != null) {
                    l2 = l2.next;
                } else {
                    break;
                }
            }
        }

        if (l1.next == null) {
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
}
