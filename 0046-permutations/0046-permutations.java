class Solution {

    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        backtrack(nums, used, current, ans);

        return ans;
    }

    private void backtrack(int[] nums, boolean[] used,
                           List<Integer> current,
                           List<List<Integer>> ans) {

        // Base Case
        if (current.size() == nums.length) {
            ans.add(new ArrayList<>(current));
            return;
        }

        // Try every number
        for (int i = 0; i < nums.length; i++) {

            if (used[i])
                continue;

            // Choose
            used[i] = true;
            current.add(nums[i]);

            // Explore
            backtrack(nums, used, current, ans);

            // Backtrack
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}