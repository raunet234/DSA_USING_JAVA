class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        Integer[][] arr = new Integer[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        Arrays.sort(arr, (a, b) -> {
            if (!a[0].equals(b[0])) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        List<Integer>[][] dp = new ArrayList[n + 1][5];
        long[][] score = new long[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = new ArrayList<>();
            }
        }

        for (int i = n - 1; i >= 0; i--) {

            for (int count = 1; count <= 4; count++) {

                // Option 1: skip this interval
                score[i][count] = score[i + 1][count];
                dp[i][count] = new ArrayList<>(dp[i + 1][count]);

                // Option 2: take this interval
                int next = findNext(arr, i + 1, arr[i][1]);

                long takeScore =
                    arr[i][2] + score[next][count - 1];

                List<Integer> takeList = new ArrayList<>();
                takeList.add(arr[i][3]);
                takeList.addAll(dp[next][count - 1]);

                Collections.sort(takeList);

                if (takeScore > score[i][count] ||
                    (takeScore == score[i][count]
                    && isSmaller(takeList, dp[i][count]))) {

                    score[i][count] = takeScore;
                    dp[i][count] = takeList;
                }
            }
        }

        List<Integer> result = dp[0][4];

        int[] answer = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }

        return answer;
    }

    private int findNext(Integer[][] arr, int start, int right) {

        int left = start;
        int high = arr.length;

        while (left < high) {

            int mid = left + (high - left) / 2;

            if (arr[mid][0] > right) {
                high = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean isSmaller(List<Integer> a, List<Integer> b) {

        int size = Math.min(a.size(), b.size());

        for (int i = 0; i < size; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}