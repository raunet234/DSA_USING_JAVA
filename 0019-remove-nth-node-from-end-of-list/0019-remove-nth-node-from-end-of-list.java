/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        // Dummy node helps when we need to remove the first node
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // Two pointers
        ListNode slow = dummy;
        ListNode fast = dummy;

        // Move fast n + 1 steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move both pointers together
        // until fast reaches the end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // slow is now just before the node we want to remove
        slow.next = slow.next.next;

        // Return the actual head
        return dummy.next;
    }
}