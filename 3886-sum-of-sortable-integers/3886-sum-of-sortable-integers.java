import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        List<Integer> divisors = new ArrayList<>();

        for (int k = 1; k * k <= n; k++) {
            if (n % k == 0) {
                divisors.add(k);

                if (k != n / k) {
                    divisors.add(n / k);
                }
            }
        }

        int answer = 0;

        for (int k : divisors) {
            boolean valid = true;

            for (int start = 0; start < n; start += k) {
                if (!isCyclicRotation(nums, sorted, start, k)) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                answer += k;
            }
        }

        return answer;
    }

    private boolean isCyclicRotation(
            int[] nums, int[] sorted, int start, int k) {

        int[] pattern = new int[k];
        int[] prefix = new int[k];

        for (int i = 0; i < k; i++) {
            pattern[i] = sorted[start + i];
        }

        // Build the KMP prefix array.
        int j = 0;

        for (int i = 1; i < k; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = prefix[j - 1];
            }

            if (pattern[i] == pattern[j]) {
                j++;
            }

            prefix[i] = j;
        }

        // Search the sorted block in the original block repeated twice.
        j = 0;

        for (int i = 0; i < 2 * k - 1; i++) {
            int value = nums[start + (i % k)];

            while (j > 0 && value != pattern[j]) {
                j = prefix[j - 1];
            }

            if (value == pattern[j]) {
                j++;
            }

            if (j == k) {
                return true;
            }
        }

        return false;
    }
}