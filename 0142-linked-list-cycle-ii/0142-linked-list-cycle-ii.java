/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
class Solution {
    public ListNode detectCycle(ListNode head) {

        if(head==null || head.next==null){
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast  = fast.next.next;
            if(fast==slow){
                ListNode ptr = head;
                while(ptr!=slow){
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }
}