class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = 1000000;

        // best[i] = minimum length of a valid subarray
        // completely inside indices [0 ... i]
        int[] best = new int[n];
        Arrays.fill(best, INF);

        int left = 0;
        int sum = 0;
        int answer = INF;
        int minLength = INF;

        for (int right = 0; right < n; right++) {

            sum += arr[right];

            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // If we found a subarray [left ... right]
            if (sum == target) {

                int length = right - left + 1;

                // Combine it with the best subarray
                // that ends before 'left'
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(
                        answer,
                        length + best[left - 1]
                    );
                }

                minLength = Math.min(minLength, length);
            }

            // Carry forward the best subarray seen so far
            best[right] = minLength;
        }

        return answer == INF ? -1 : answer;
    }
}