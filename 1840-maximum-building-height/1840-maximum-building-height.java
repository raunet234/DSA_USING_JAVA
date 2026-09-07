class Solution {
    public int maxBuilding(int n, int[][] restrictions) {

        // Add building 1 with height 0
        int[][] arr = new int[restrictions.length + 1][2];

        for (int i = 0; i < restrictions.length; i++) {
            arr[i][0] = restrictions[i][0];
            arr[i][1] = restrictions[i][1];
        }

        arr[restrictions.length][0] = 1;
        arr[restrictions.length][1] = 0;

        // Sort by building number
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        // Forward pass
        for (int i = 1; i < arr.length; i++) {
            int distance = arr[i][0] - arr[i - 1][0];
            arr[i][1] = Math.min(
                arr[i][1],
                arr[i - 1][1] + distance
            );
        }

        // Backward pass
        for (int i = arr.length - 2; i >= 0; i--) {
            int distance = arr[i + 1][0] - arr[i][0];
            arr[i][1] = Math.min(
                arr[i][1],
                arr[i + 1][1] + distance
            );
        }

        int answer = 0;

        // Maximum height between restrictions
        for (int i = 0; i < arr.length - 1; i++) {
            int distance = arr[i + 1][0] - arr[i][0];
            int h1 = arr[i][1];
            int h2 = arr[i + 1][1];

            int peak = (h1 + h2 + distance) / 2;

            answer = Math.max(answer, peak);
        }

        // After the last restriction
        int lastId = arr[arr.length - 1][0];
        int lastHeight = arr[arr.length - 1][1];

        answer = Math.max(answer, lastHeight + (n - lastId));

        return answer;
    }
}