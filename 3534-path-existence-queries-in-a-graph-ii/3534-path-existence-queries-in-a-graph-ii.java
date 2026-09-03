class Solution {

    public int[] pathExistenceQueries(
            int n,
            int[] nums,
            int maxDiff,
            int[][] queries) {

        // Store {value, original index}
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by value
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        // sorted values
        int[] values = new int[n];

        // original index -> sorted position
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = arr[i][0];
            pos[arr[i][1]] = i;
        }

        // next[i] = furthest position reachable from i in one edge
        int[] next = new int[n];

        int r = 0;

        for (int i = 0; i < n; i++) {

            if (r < i) {
                r = i;
            }

            while (r + 1 < n &&
                   values[r + 1] - values[i] <= maxDiff) {
                r++;
            }

            next[i] = r;
        }

        /*
         * Find connected components.
         *
         * If next[i] == i, we cannot move forward.
         * We use component IDs to determine whether
         * two nodes are connected.
         */
        int[] component = new int[n];

        int comp = 0;

        for (int i = 0; i < n; i++) {

            if (i > 0 && next[i - 1] < i) {
                comp++;
            }

            component[i] = comp;
        }

        // Binary lifting
        int LOG = 18; // because n <= 100000

        int[][] jump = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            jump[0][i] = next[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                jump[k][i] = jump[k - 1][jump[k - 1][i]];
            }
        }

        int[] answer = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int u = queries[q][0];
            int v = queries[q][1];

            int left = pos[u];
            int right = pos[v];

            // Same node
            if (left == right) {
                answer[q] = 0;
                continue;
            }

            // Make left smaller
            if (left > right) {
                int temp = left;
                left = right;
                right = temp;
            }

            // Different components
            if (component[left] != component[right]) {
                answer[q] = -1;
                continue;
            }

            // Find minimum number of jumps
            int steps = 0;
            int current = left;

            for (int k = LOG - 1; k >= 0; k--) {

                if (jump[k][current] < right) {
                    current = jump[k][current];
                    steps += (1 << k);
                }
            }

            // One final jump reaches right
            answer[q] = steps + 1;
        }

        return answer;
    }
}