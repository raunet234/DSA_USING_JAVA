import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;

    private long[] sum;
    private long[] squares;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        squares = new long[4 * n];
        lazy = new long[4 * n];

        Map<Integer, Integer> last = new HashMap<>();
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = last.getOrDefault(nums[i], -1);

            update(1, 0, n - 1, prev + 1, i);

            ans = (ans + squares[1]) % MOD;
            last.put(nums[i], i);
        }

        return (int) ans;
    }

    private void update(int node, int start, int end, int left, int right) {
        if (left > end || right < start) {
            return;
        }

        if (left <= start && end <= right) {
            apply(node, start, end, 1);
            return;
        }

        push(node, start, end);

        int mid = start + (end - start) / 2;

        update(node * 2, start, mid, left, right);
        update(node * 2 + 1, mid + 1, end, left, right);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        squares[node] =
            (squares[node * 2] + squares[node * 2 + 1]) % MOD;
    }

    private void apply(int node, int start, int end, long val) {
        long len = end - start + 1L;

        // (x + val)^2 = x^2 + 2 * val * x + val^2
        squares[node] = (
            squares[node]
            + 2 * val % MOD * sum[node] % MOD
            + val * val % MOD * len % MOD
        ) % MOD;

        sum[node] = (sum[node] + val * len) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }

    private void push(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = start + (end - start) / 2;

        apply(node * 2, start, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, end, lazy[node]);

        lazy[node] = 0;
    }
}