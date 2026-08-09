class Solution {

    int[][] memo;
    int[] suffix;
    int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        memo = new int[n][n + 1];

        return dfs(0, 1);
    }

    private int dfs(int i, int m) {

        if (i >= n)
            return 0;

        if (i + 2 * m >= n)
            return suffix[i];

        if (memo[i][m] != 0)
            return memo[i][m];

        int best = 0;

        for (int x = 1; x <= 2 * m; x++) {

            int opponent = dfs(i + x, Math.max(m, x));

            best = Math.max(best, suffix[i] - opponent);
        }

        memo[i][m] = best;

        return best;
    }
}