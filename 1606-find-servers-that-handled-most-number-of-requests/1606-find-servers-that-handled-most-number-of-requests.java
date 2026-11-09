
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        // Available server IDs in sorted order
        TreeSet<Integer> available = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        // Busy servers: [finishTime, serverId]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];

        for (int i = 0; i < arrival.length; i++) {

            int currentTime = arrival[i];

            // Release servers that have finished their requests
            while (!busy.isEmpty() &&
                   busy.peek()[0] <= currentTime) {

                long[] server = busy.poll();
                available.add((int) server[1]);
            }

            // If all servers are busy, drop the request
            if (available.isEmpty()) {
                continue;
            }

            // Preferred server
            int preferred = i % k;

            // Find the next available server
            Integer server = available.ceiling(preferred);

            // Wrap around if necessary
            if (server == null) {
                server = available.first();
            }

            // Assign the request
            available.remove(server);

            count[server]++;

            // Calculate when this server becomes free
            long finishTime = (long) currentTime + load[i];

            busy.offer(new long[]{finishTime, server});
        }

        // Find the maximum number of requests handled
        int maxRequests = 0;

        for (int i = 0; i < k; i++) {
            maxRequests = Math.max(maxRequests, count[i]);
        }

        // Collect all busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == maxRequests) {
                result.add(i);
            }
        }

        return result;
    }
}
