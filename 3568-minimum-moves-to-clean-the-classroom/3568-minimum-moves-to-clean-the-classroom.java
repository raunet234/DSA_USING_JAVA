class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0;
        int startC = 0;
        int litterCount = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    startR = r;
                    startC = c;
                }

                if (ch == 'L') {
                    litterCount++;
                }
            }
        }

        int[][] litterIndex = new int[m][n];

        for (int r = 0; r < m; r++) {
            Arrays.fill(litterIndex[r], -1);
        }

        int index = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                if (classroom[r].charAt(c) == 'L') {
                    litterIndex[r][c] = index++;
                }
            }
        }

        int totalMasks = 1 << litterCount;

        int[][][][] dist =
            new int[m][n][totalMasks][energy + 1];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                for (int mask = 0; mask < totalMasks; mask++) {
                    Arrays.fill(dist[r][c][mask], -1);
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();

        // row, col, collectedMask, remainingEnergy
        queue.offer(new int[]{
            startR, startC, 0, energy
        });

        dist[startR][startC][0][energy] = 0;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!queue.isEmpty()) {

            int[] state = queue.poll();

            int r = state[0];
            int c = state[1];
            int mask = state[2];
            int currEnergy = state[3];

            int moves = dist[r][c][mask][currEnergy];

            // All litter collected
            if (mask == totalMasks - 1) {
                return moves;
            }

            // No energy and not standing on R
            if (currEnergy == 0 &&
                classroom[r].charAt(c) != 'R') {
                continue;
            }

            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= m ||
                    nc < 0 || nc >= n) {
                    continue;
                }

                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                int newEnergy = currEnergy - 1;

                if (newEnergy < 0) {
                    continue;
                }

                // Reset energy
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                int newMask = mask;

                // Collect litter
                if (classroom[nr].charAt(nc) == 'L') {
                    int litter = litterIndex[nr][nc];
                    newMask |= (1 << litter);
                }

                if (dist[nr][nc][newMask][newEnergy] != -1) {
                    continue;
                }

                dist[nr][nc][newMask][newEnergy] = moves + 1;

                queue.offer(new int[]{
                    nr, nc, newMask, newEnergy
                });
            }
        }

        return -1;
    }
}