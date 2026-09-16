class Solution {
    public int numberOfSets(int n, int k) {
        long MOD = 1000000007;

        long ans = 1;

        // C(n + k - 1, 2k)
        int a = n + k - 1;
        int b = 2 * k;

        for (int i = 1; i <= b; i++) {
            ans = ans * (a - b + i) % MOD;

            long inv = modPow(i, MOD - 2, MOD);
            ans = ans * inv % MOD;
        }

        return (int) ans;
    }

    private long modPow(long a, long b, long mod) {
        long result = 1;

        while (b > 0) {
            if ((b & 1) == 1) {
                result = result * a % mod;
            }

            a = a * a % mod;
            b >>= 1;
        }

        return result;
    }
}