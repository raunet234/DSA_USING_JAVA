
import java.util.*;

class Solution {

    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;
        int LOG = 18;

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Step 1: Find next strictly greater element
        int[] nextGreater = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {

            while (!stack.isEmpty() &&
                   nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }

            nextGreater[i] =
                stack.isEmpty() ? n : stack.peek();

            stack.push(i);
        }

        // Step 2: Build binary lifting tables
        int[][] up = new int[LOG][n + 1];
        long[][] cost = new long[LOG][n + 1];

        for (int i = 0; i < n; i++) {

            int next = nextGreater[i];

            up[0][i] = next;

            cost[0][i] =
                (long) nums[i] * (next - i)
                - (prefix[next] - prefix[i]);
        }

        up[0][n] = n;

        for (int j = 1; j < LOG; j++) {

            for (int i = 0; i <= n; i++) {

                int mid = up[j - 1][i];

                up[j][i] = up[j - 1][mid];

                cost[j][i] =
                    cost[j - 1][i] + cost[j - 1][mid];
            }
        }

        // Step 3: Sliding window
        int left = 0;
        long answer = 0;

        for (int right = 0; right < n; right++) {

            while (left <= right &&
                   getCost(left, right, nums, prefix,
                           up, cost) > k) {

                left++;
            }

            answer += right - left + 1;
        }

        return answer;
    }

    // Calculate the minimum operations for nums[l...r]
    private long getCost(
        int l,
        int r,
        int[] nums,
        long[] prefix,
        int[][] up,
        long[][] cost
    ) {

        long total = 0;
        int current = l;

        int LOG = up.length;

        // Jump over complete blocks
        for (int j = LOG - 1; j >= 0; j--) {

            int next = up[j][current];

            if (next <= r) {

                total += cost[j][current];
                current = next;
            }
        }

        // Calculate the final incomplete block
        total += (long) nums[current] * (r - current + 1)
                 - (prefix[r + 1] - prefix[current]);

        return total;
    }
}
