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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        // Dummy node helps us easily build the new list
        ListNode dummy = new ListNode(0);

        // Current points to the last node of our merged list
        ListNode current = dummy;

        // Compare both lists while both have nodes
        while (list1 != null && list2 != null) {

            // If list1 value is smaller
            if (list1.val <= list2.val) {

                // Add list1 node to the merged list
                current.next = list1;

                // Move list1 forward
                list1 = list1.next;

            } else {

                // Add list2 node to the merged list
                current.next = list2;

                // Move list2 forward
                list2 = list2.next;
            }

            // Move current to the newly added node
            current = current.next;
        }

        // If list1 still has nodes, add them
        if (list1 != null) {
            current.next = list1;
        }

        // If list2 still has nodes, add them
        if (list2 != null) {
            current.next = list2;
        }

        // Return the first real node
        // dummy itself is not part of the answer
        return dummy.next;
    }
}