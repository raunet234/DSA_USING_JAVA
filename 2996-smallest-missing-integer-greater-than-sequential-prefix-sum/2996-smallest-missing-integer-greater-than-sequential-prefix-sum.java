class Solution {
    public int missingInteger(int[] nums) {

        // Step 1: Find sum of longest sequential prefix
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Step 2: Find smallest missing integer >= sum
        while (true) {

            boolean found = false;

            for (int num : nums) {
                if (num == sum) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return sum;
            }

            sum++;
        }
    }
}