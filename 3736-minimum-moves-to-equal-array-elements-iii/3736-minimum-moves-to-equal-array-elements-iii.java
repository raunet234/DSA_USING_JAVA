
class Solution {
    public int minMoves(int[] nums) {
        int max = nums[0];

        // Find the maximum element
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        // Calculate total moves
        int moves = 0;

        for (int i = 0; i < nums.length; i++) {
            moves += max - nums[i];
        }

        return moves;
    }
}
