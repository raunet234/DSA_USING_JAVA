import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : invocations) {
            graph.get(edge[0]).add(edge[1]);
        }

        boolean[] suspicious = new boolean[n];

        Queue<Integer> q = new LinkedList<>();
        q.offer(k);
        suspicious[k] = true;

        while (!q.isEmpty()) {
            int curr = q.poll();

            for (int next : graph.get(curr)) {
                if (!suspicious[next]) {
                    suspicious[next] = true;
                    q.offer(next);
                }
            }
        }

        // Check if any outside method calls a suspicious method
        for (int[] edge : invocations) {
            int from = edge[0];
            int to = edge[1];

            if (!suspicious[from] && suspicious[to]) {
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        // Remove suspicious methods
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }
}