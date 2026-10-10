import java.util.*;

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {

        int n = nums1.length;
        long k = (long) k1 + k2;

        int[] diff = new int[n];
        long maxDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        if (k >= Arrays.stream(diff).asLongStream().sum()) {
            return 0;
        }

        long left = 0;
        long right = maxDiff;

        // Find the maximum difference level we can reduce to
        while (left < right) {
            long mid = left + (right - left) / 2;

            long operations = 0;

            for (int i = 0; i < n; i++) {
                if (diff[i] > mid) {
                    operations += diff[i] - mid;
                }
            }

            if (operations <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        long level = left;
        long operations = 0;
        long answer = 0;

        for (int i = 0; i < n; i++) {
            if (diff[i] > level) {
                operations += diff[i] - level;
                diff[i] = (int) level;
            }
            answer += (long) diff[i] * diff[i];
        }

        // Use remaining operations to reduce some differences by one
        long remaining = k - operations;

        for (int i = 0; i < n && remaining > 0; i++) {
            if (diff[i] == level && level > 0) {
                answer -= (long) diff[i] * diff[i];
                diff[i]--;
                answer += (long) diff[i] * diff[i];
                remaining--;
            }
        }

        return answer;
    }
}