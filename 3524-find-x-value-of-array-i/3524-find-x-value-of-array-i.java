class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] result = new long[k];
        long[] dp = new long[k];

        for (int i = 0; i < nums.length; i++) {

            long[] next = new long[k];

            // Start a new subarray with nums[i]
            int current = nums[i] % k;
            next[current]++;

            // Extend all previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] == 0) {
                    continue;
                }

                int newRemainder = (int) ((long) r * current % k);

                next[newRemainder] += dp[r];
            }

            // Add all subarrays ending at i to the answer
            for (int r = 0; r < k; r++) {
                result[r] += next[r];
            }

            dp = next;
        }

        return result;
    }
}