package pers.zkx.algo.leetcode.mid;

/**
 * 138. 随机链表的复制
 *
 * @author: zhangkuixing
 * @date: 2025/9/6 21:44
 */
public class Solution138 {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }


    public Node copyRandomList(Node head) {
        Node cur = head;
        while (cur != null) {
            Node newNode = new Node(cur.val);
            newNode.next = cur.next;
            cur.next = newNode;
            cur = cur.next.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        Node copy = new Node(0);
        Node copyHead = copy;
        cur = head;
        while (cur != null) {
            copy.next = cur.next;
            cur.next = cur.next.next;
            cur = cur.next;
            copy = copy.next;
        }
        return copyHead.next;
    }

}
