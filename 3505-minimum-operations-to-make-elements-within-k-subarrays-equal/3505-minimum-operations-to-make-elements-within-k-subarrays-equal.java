
import java.util.*;

class Solution {

    class Fenwick {
        int n;
        int[] count;
        long[] sum;

        Fenwick(int n) {
            this.n = n;
            count = new int[n + 1];
            sum = new long[n + 1];
        }

        void update(int i, int delta, long value) {
            while (i <= n) {
                count[i] += delta;
                sum[i] += delta * value;
                i += i & -i;
            }
        }

        int queryCount(int i) {
            int result = 0;

            while (i > 0) {
                result += count[i];
                i -= i & -i;
            }

            return result;
        }

        long querySum(int i) {
            long result = 0;

            while (i > 0) {
                result += sum[i];
                i -= i & -i;
            }

            return result;
        }

        // Find the index of the element with the given rank.
        int findKth(int k) {
            int index = 0;
            int bit = Integer.highestOneBit(n);

            while (bit > 0) {
                int next = index + bit;

                if (next <= n && count[next] < k) {
                    index = next;
                    k -= count[next];
                }

                bit >>= 1;
            }

            return index + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int size = 0;

        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[size++] = sorted[i];
            }
        }

        int[] values = Arrays.copyOf(sorted, size);

        Fenwick bit = new Fenwick(size);

        // Insert the first window
        long totalSum = 0;

        for (int i = 0; i < x; i++) {
            int pos = Arrays.binarySearch(values, nums[i]) + 1;

            bit.update(pos, 1, nums[i]);
            totalSum += nums[i];
        }

        int windows = n - x + 1;
        long[] cost = new long[windows];

        // Calculate cost of each window
        for (int start = 0; start < windows; start++) {

            // Lower median
            int medianRank = (x + 1) / 2;
            int medianIndex = bit.findKth(medianRank);

            long median = values[medianIndex - 1];

            int leftCount = bit.queryCount(medianIndex);
            long leftSum = bit.querySum(medianIndex);

            long rightSum = totalSum - leftSum;
            int rightCount = x - leftCount;

            long leftCost = median * leftCount - leftSum;
            long rightCost = rightSum - median * rightCount;

            cost[start] = leftCost + rightCost;

            // Slide the window
            if (start + 1 < windows) {

                int removePos =
                    Arrays.binarySearch(values, nums[start]) + 1;

                bit.update(removePos, -1, nums[start]);
                totalSum -= nums[start];

                int addValue = nums[start + x];
                int addPos =
                    Arrays.binarySearch(values, addValue) + 1;

                bit.update(addPos, 1, addValue);
                totalSum += addValue;
            }
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];

        // Select exactly k non-overlapping subarrays
        for (int j = 1; j <= k; j++) {

            long[] curr = new long[n + 1];
            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Skip the current element
                curr[i] = curr[i - 1];

                // Select a window of size x ending at i - 1
                if (i >= x && prev[i - x] != INF) {

                    long candidate =
                        prev[i - x] + cost[i - x];

                    curr[i] = Math.min(curr[i], candidate);
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}
