class Solution {

    Integer[][][] memo;

    public int maxProfit(int k, int[] prices) {

        memo = new Integer[prices.length][2][k + 1];

        return dfs(0, 1, k, prices);
    }

    private int dfs(int day, int canBuy, int transactionsLeft, int[] prices) {

        if (day == prices.length || transactionsLeft == 0)
            return 0;

        if (memo[day][canBuy][transactionsLeft] != null)
            return memo[day][canBuy][transactionsLeft];

        int profit;

        if (canBuy == 1) {

            int buy =
                    -prices[day]
                    + dfs(day + 1, 0, transactionsLeft, prices);

            int skip =
                    dfs(day + 1, 1, transactionsLeft, prices);

            profit = Math.max(buy, skip);

        } else {

            int sell =
                    prices[day]
                    + dfs(day + 1, 1, transactionsLeft - 1, prices);

            int skip =
                    dfs(day + 1, 0, transactionsLeft, prices);

            profit = Math.max(sell, skip);
        }

        return memo[day][canBuy][transactionsLeft] = profit;
    }
}