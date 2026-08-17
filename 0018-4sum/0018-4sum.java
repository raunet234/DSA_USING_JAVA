class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {

        // Sort the array
        Arrays.sort(nums);

        // Store the final answers
        List<List<Integer>> result = new ArrayList<>();

        int n = nums.length;

        // Choose the first number
        for (int i = 0; i < n - 3; i++) {

            // Skip duplicate first numbers
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Choose the second number
            for (int j = i + 1; j < n - 2; j++) {

                // Skip duplicate second numbers
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // Two pointers for the remaining two numbers
                int left = j + 1;
                int right = n - 1;

                while (left < right) {

                    // Calculate the sum
                    long sum = (long) nums[i]
                            + nums[j]
                            + nums[left]
                            + nums[right];

                    // If sum is too small,
                    // move left forward
                    if (sum < target) {
                        left++;
                    }

                    // If sum is too large,
                    // move right backward
                    else if (sum > target) {
                        right--;
                    }

                    // We found a quadruplet
                    else {

                        result.add(Arrays.asList(
                            nums[i],
                            nums[j],
                            nums[left],
                            nums[right]
                        ));

                        // Move both pointers
                        left++;
                        right--;

                        // Skip duplicate left values
                        while (left < right &&
                               nums[left] == nums[left - 1]) {
                            left++;
                        }

                        // Skip duplicate right values
                        while (left < right &&
                               nums[right] == nums[right + 1]) {
                            right--;
                        }
                    }
                }
            }
        }

        return result;
    }
}