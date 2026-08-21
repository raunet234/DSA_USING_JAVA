class Solution {
    public long findKthSmallest(int[] coins, int k) {

        // Binary search range
        long left = 1;
        long right = (long) coins[0] * k;

        // Find the smallest number that has at least k
        // valid amounts <= it
        while (left < right) {

            long mid = left + (right - left) / 2;

            // Count how many valid amounts are <= mid
            long count = countAmounts(coins, mid);

            if (count >= k) {
                // Answer can be mid or smaller
                right = mid;
            } else {
                // Need a bigger number
                left = mid + 1;
            }
        }

        return left;
    }


    private long countAmounts(int[] coins, long x) {

        long count = 0;
        int n = coins.length;

        // Try every non-empty combination of coins
        // using Inclusion-Exclusion
        for (int mask = 1; mask < (1 << n); mask++) {

            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {

                // Check if this coin is included
                if ((mask & (1 << i)) != 0) {

                    bits++;

                    // Find LCM
                    long g = gcd(lcm, coins[i]);
                    lcm = lcm / g * coins[i];

                    // If LCM is bigger than x,
                    // no multiple can be <= x
                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            // Number of multiples of LCM <= x
            long multiples = x / lcm;

            // Odd number of coins -> add
            // Even number of coins -> subtract
            if (bits % 2 == 1) {
                count += multiples;
            } else {
                count -= multiples;
            }
        }

        return count;
    }


    // Find Greatest Common Divisor
    private long gcd(long a, long b) {

        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}