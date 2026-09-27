import java.util.*;

class Solution {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {

        int m = grid.size();
        int n = grid.get(0).size();

        int[][] best = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(best[i], -1);
        }

        // {remainingHealth, row, col}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> b[0] - a[0]
        );

        int startHealth = health - grid.get(0).get(0);

        if (startHealth <= 0) {
            return false;
        }

        best[0][0] = startHealth;
        pq.offer(new int[]{startHealth, 0, 0});

        int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        while (!pq.isEmpty()) {

            int[] current = pq.poll();

            int currentHealth = current[0];
            int row = current[1];
            int col = current[2];

            if (row == m - 1 && col == n - 1) {
                return true;
            }

            // We already reached this cell with better health
            if (currentHealth < best[row][col]) {
                continue;
            }

            for (int i = 0; i < 4; i++) {

                int newRow = row + directions[i][0];
                int newCol = col + directions[i][1];

                if (newRow < 0 || newRow >= m ||
                    newCol < 0 || newCol >= n) {
                    continue;
                }

                int newHealth = currentHealth
                        - grid.get(newRow).get(newCol);

                if (newHealth <= 0) {
                    continue;
                }

                if (newHealth > best[newRow][newCol]) {

                    best[newRow][newCol] = newHealth;

                    pq.offer(new int[]{
                        newHealth,
                        newRow,
                        newCol
                    });
                }
            }
        }

        return false;
    }
}