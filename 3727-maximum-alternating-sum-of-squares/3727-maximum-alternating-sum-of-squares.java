import java.util.*;

class Solution {
    public long maxAlternatingSum(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length;
        long[] squares = new long[n];

        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long answer = 0;
        int half = n / 2;

        for (int i = 0; i < n; i++) {
            if (i < half) {
                answer -= squares[i];
            } else {
                answer += squares[i];
            }
        }

        return answer;
    }
}