class Solution {
    public boolean uniformArray(int[] nums1) {

        boolean hasOdd = false;
        boolean hasEven = false;

        for (int num : nums1) {
            if (num % 2 == 0) {
                hasEven = true;
            } else {
                hasOdd = true;
            }
        }

        // If all are already the same parity
        if (!hasOdd || !hasEven) {
            return true;
        }

        // If we have both odd and even,
        // every element can be made odd by subtracting
        // an element of opposite parity.
        return true;
    }
}