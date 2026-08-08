class Solution {

    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        int[] suf = new int[n + 1];

        Arrays.fill(suf, m);

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && word1.charAt(i) == word2.charAt(j))
                j--;
            suf[i] = j + 1;
        }

        int[] ans = new int[m];

        boolean used = false;

        int i = 0;
        j = 0;

        while (j < m) {

            while (i < n && word1.charAt(i) != word2.charAt(j)) {

                if (!used && suf[i + 1] <= j + 1) {
                    used = true;
                    ans[j] = i;
                    i++;
                    j++;
                    break;
                }

                i++;
            }

            if (j == m)
                break;

            if (i == n)
                return new int[0];

            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                i++;
                j++;
            }
        }

        return ans;
    }
}