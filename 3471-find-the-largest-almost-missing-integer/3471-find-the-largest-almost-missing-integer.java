class Solution {
    public int largestInteger(int[] nums, int k) {

        // Map each number to the number of
        // different subarrays of size k where it appears
        Map<Integer, Integer> map = new HashMap<>();

        // Check every subarray of size k
        for (int i = 0; i <= nums.length - k; i++) {

            // Set is used so that if the same number
            // appears twice in one subarray,
            // we count that subarray only once
            Set<Integer> set = new HashSet<>();

            // Go through the current subarray
            for (int j = i; j < i + k; j++) {
                set.add(nums[j]);
            }

            // Count this subarray for each number
            for (int num : set) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

        // Store the largest valid number
        int answer = -1;

        // Find numbers that appear in exactly one subarray
        for (int num : map.keySet()) {
            if (map.get(num) == 1) {

                // Take the largest one
                answer = Math.max(answer, num);
            }
        }

        return answer;
    }
}