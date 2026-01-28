package pers.zkx.algo.leetcode.mid;

/**
 * Solution61
 *
 * @author zhangkuixing
 * @since 2026-01-28
 */
public class Solution61 {
    public class ListNode {
     int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    public ListNode rotateRight(ListNode head, int k) {
        int count = 0;
        ListNode curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        if (count == 0) {
            return head;
        }
        k = k % count;
        if (k == 0) {
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;

        int step = count-k;
        while (step > 0) {
            fast = fast.next;
            if (step>1){
                slow = slow.next;
            }
            step--;
        }


        ListNode newHead = fast;
        slow.next = null;
        ListNode temp = newHead;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = head;
        return newHead;
    }



}
