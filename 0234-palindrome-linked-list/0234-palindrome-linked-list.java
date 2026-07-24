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
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // STEP 1: Find the middle
        ListNode slow = head;
        ListNode fast = head;
       while(fast.next!=null && fast.next.next!=null){
         slow = slow.next;
         fast = fast.next.next;
       }

        // STEP 1b: Cut the chain
       ListNode secondHalf = slow.next;
       slow.next = null; 
        // STEP 2: Reverse the second half
        ListNode prev = null;
        ListNode curr = secondHalf;
       while(curr!=null){
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
       }
        // STEP 3: Compare both halves
        ListNode first = head;
        ListNode second = prev;
        while(second!=null){
            if(first.val!=second.val){
                return false;
            }
            first = first.next;
            second = second.next;
        }
        return true;
    }
}