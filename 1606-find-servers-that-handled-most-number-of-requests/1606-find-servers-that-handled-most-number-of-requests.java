
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        TreeSet<Integer> available = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];

        for (int i = 0; i < arrival.length; i++) {

            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                long[] server = busy.poll();
                available.add((int) server[1]);
            }

            if (available.isEmpty()) {
                continue;
            }

            int preferred = i % k;

            Integer server = available.ceiling(preferred);

            if (server == null) {
                server = available.first();
            }

            available.remove(server);
            count[server]++;

            long finishTime = (long) arrival[i] + load[i];

            busy.offer(new long[]{finishTime, server});
        }

        int maxRequests = 0;

        for (int c : count) {
            maxRequests = Math.max(maxRequests, c);
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == maxRequests) {
                result.add(i);
            }
        }

        return result;
    }
}
