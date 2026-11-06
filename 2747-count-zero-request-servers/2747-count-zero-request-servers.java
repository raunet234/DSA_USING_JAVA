
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Sort logs by request time
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Sort query indices by query time
        Integer[] indices = new Integer[queries.length];

        for (int i = 0; i < queries.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, (a, b) ->
            Integer.compare(queries[a], queries[b])
        );

        int[] ans = new int[queries.length];

        // Frequency of requests for each server
        int[] freq = new int[n + 1];

        int left = 0;
        int right = 0;
        int activeServers = 0;

        for (int idx : indices) {

            int q = queries[idx];
            int start = q - x;

            // Add all logs with time <= q
            while (right < logs.length && logs[right][1] <= q) {
                int server = logs[right][0];

                if (freq[server] == 0) {
                    activeServers++;
                }

                freq[server]++;
                right++;
            }

            // Remove logs with time < q - x
            while (left < right && logs[left][1] < start) {
                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    activeServers--;
                }

                left++;
            }

            // Servers with no requests in the interval
            ans[idx] = n - activeServers;
        }

        return ans;
    }
}
