class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {

        int n = nums.length;

        // Prefix sums can range from -n to n.
        // Shift them by n so they become positive indices.
        int offset = n + 1;

        int[] bit = new int[2 * n + 3];

        long answer = 0;
        int prefix = 0;

        // Prefix sum 0 occurs before the array starts.
        add(bit, offset, 1);

        for (int num : nums) {

            if (num == target) {
                prefix++;
            } else {
                prefix--;
            }

            // We need:
            // prefix[j] - prefix[i] > 0
            //
            // Therefore:
            // prefix[i] < prefix[j]
            //
            // Count previous prefix sums smaller than current.
            answer += query(bit, prefix + offset - 1);

            add(bit, prefix + offset, 1);
        }

        return answer;
    }

    private void add(int[] bit, int index, int value) {

        while (index < bit.length) {
            bit[index] += value;
            index += index & -index;
        }
    }

    private int query(int[] bit, int index) {

        int sum = 0;

        while (index > 0) {
            sum += bit[index];
            index -= index & -index;
        }

        return sum;
    }
}