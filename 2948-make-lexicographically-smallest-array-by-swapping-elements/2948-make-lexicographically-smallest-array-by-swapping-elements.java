class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;

        // Store {value, original index}
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by value
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int start = 0;

        while (start < n) {

            int end = start;

            // Find one connected group
            while (end + 1 < n &&
                   (long) arr[end + 1][0] - arr[end][0] <= limit) {
                end++;
            }

            // Collect original indices
            int[] indices = new int[end - start + 1];
            int[] values = new int[end - start + 1];

            for (int i = start; i <= end; i++) {
                indices[i - start] = arr[i][1];
                values[i - start] = arr[i][0];
            }

            // Sort indices
            Arrays.sort(indices);

            // Values are already sorted
            for (int i = 0; i < indices.length; i++) {
                nums[indices[i]] = values[i];
            }

            start = end + 1;
        }

        return nums;
    }
}