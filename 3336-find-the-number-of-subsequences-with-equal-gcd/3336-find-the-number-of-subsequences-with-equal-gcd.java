class Solution {
    private static final int MOD = 1_000_000_007;
    private int[] nums;
    private Integer[][][] dp;

    public int subsequencePairCount(int[] nums) {
        this.nums = nums;

        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }

        dp = new Integer[nums.length + 1][max + 1][max + 1];
        return (dfs(nums.length, 0, 0) - 1 + MOD) % MOD;
    }

    private int dfs(int i, int g1, int g2) {
        if (i == 0) {
            return g1 == g2 ? 1 : 0;
        }

        if (dp[i][g1][g2] != null) {
            return dp[i][g1][g2];
        }

        int x = nums[i - 1];

        long ans = dfs(i - 1, g1, g2);
        ans += dfs(i - 1, gcd(g1, x), g2);
        ans += dfs(i - 1, g1, gcd(g2, x));

        return dp[i][g1][g2] = (int) (ans % MOD);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}