class Solution {

    public int stoneGameV(int[] stoneValue) {

        // Number of stones
        int n = stoneValue.length;

        // prefix[i] = sum of stones before index i
        int[] prefix = new int[n + 1];

        // Build prefix sum
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }


        // dp[i][j] = maximum score Alice can get
        // from stones between i and j
        int[][] dp = new int[n][n];


        // Try different lengths of subarrays
        // We start from length 2 because
        // one stone cannot be divided
        for (int len = 2; len <= n; len++) {

            // Choose the starting index
            for (int i = 0; i + len <= n; i++) {

                // Find the ending index
                int j = i + len - 1;


                // Try every possible split
                for (int k = i; k < j; k++) {

                    // Sum of left part
                    int leftSum =
                        prefix[k + 1] - prefix[i];

                    // Sum of right part
                    int rightSum =
                        prefix[j + 1] - prefix[k + 1];


                    // Left side is smaller
                    if (leftSum < rightSum) {

                        // Bob throws away right side
                        // Alice keeps left side
                        dp[i][j] = Math.max(
                            dp[i][j],
                            leftSum + dp[i][k]
                        );
                    }


                    // Right side is smaller
                    else if (rightSum < leftSum) {

                        // Bob throws away left side
                        // Alice keeps right side
                        dp[i][j] = Math.max(
                            dp[i][j],
                            rightSum + dp[k + 1][j]
                        );
                    }


                    // Both sides have the same sum
                    else {

                        // Alice can keep the left side
                        dp[i][j] = Math.max(
                            dp[i][j],
                            leftSum + dp[i][k]
                        );

                        // Alice can keep the right side
                        dp[i][j] = Math.max(
                            dp[i][j],
                            rightSum + dp[k + 1][j]
                        );
                    }
                }
            }
        }


        // Answer for the entire array
        return dp[0][n - 1];
    }
}