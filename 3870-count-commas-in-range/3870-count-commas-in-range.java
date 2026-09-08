class Solution {
    public int countCommas(int n) {
        int ans = 0;

        if (n >= 1000) {
            ans += n - 999;
        }

        if (n >= 1_000_000) {
            ans += n - 999_999;
        }

        if (n >= 1_000_000_000) {
            ans += n - 999_999_999;
        }

        return ans;
    }
}