class Solution {
    public boolean[] pathExistenceQueries(
            int n,
            int[] nums,
            int maxDiff,
            int[][] queries) {

        // group[i] tells us which connected component
        // node i belongs to.
        int[] group = new int[n];

        int groupId = 0;

        for (int i = 1; i < n; i++) {

            // If there is a big gap, start a new group
            if (nums[i] - nums[i - 1] > maxDiff) {
                groupId++;
            }

            group[i] = groupId;
        }

        boolean[] answer = new boolean[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int u = queries[i][0];
            int v = queries[i][1];

            answer[i] = group[u] == group[v];
        }

        return answer;
    }
}