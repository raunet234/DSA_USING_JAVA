class Solution {

    static final long LIMIT = 1000001L;
    long[][] C;

    public String smallestPalindrome(String s, int k) {

        int[] freq = new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;

        String mid = "";

        int[] half = new int[26];
        int len = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1)
                mid = String.valueOf((char) ('a' + i));

            half[i] = freq[i] / 2;
            len += half[i];
        }

        // Pascal combinations
        C = new long[len + 1][len + 1];

        for (int i = 0; i <= len; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; j++) {
                C[i][j] = Math.min(LIMIT, C[i - 1][j - 1] + C[i - 1][j]);
            }
        }

        long total = countWays(half);

        if (total < k)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < len; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                long ways = countWays(half);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        String right = new StringBuilder(left).reverse().toString();

        return left.toString() + mid + right;
    }

    private long countWays(int[] cnt) {

        int remaining = 0;

        for (int x : cnt)
            remaining += x;

        long ans = 1;

        int rem = remaining;

        for (int i = 0; i < 26; i++) {

            if (cnt[i] == 0)
                continue;

            ans *= C[rem][cnt[i]];

            if (ans > LIMIT)
                ans = LIMIT;

            rem -= cnt[i];
        }

        return ans;
    }
}