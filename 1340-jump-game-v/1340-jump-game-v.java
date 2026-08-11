class Solution {
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int[] dp = new int[n];
        int answer = 1;

        for (int i = 0; i < n; i++) {
            dp[i] = dfs(i, arr, d, dp);
            answer = Math.max(answer, dp[i]);
        }

        return answer;
    }

    private int dfs(int i, int[] arr, int d, int[] dp) {
        if (dp[i] != 0) {
            return dp[i];
        }

        dp[i] = 1;

        // Jump to the left
        for (int j = i - 1; j >= Math.max(0, i - d); j--) {

            if (arr[j] >= arr[i]) {
                break;
            }

            dp[i] = Math.max(
                dp[i],
                1 + dfs(j, arr, d, dp)
            );
        }

        // Jump to the right
        for (int j = i + 1; j <= Math.min(arr.length - 1, i + d); j++) {

            if (arr[j] >= arr[i]) {
                break;
            }

            dp[i] = Math.max(
                dp[i],
                1 + dfs(j, arr, d, dp)
            );
        }

        return dp[i];
    }
}