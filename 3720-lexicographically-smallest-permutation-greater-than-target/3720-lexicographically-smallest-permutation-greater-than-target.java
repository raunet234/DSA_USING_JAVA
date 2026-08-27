class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < target.length(); i++) {
            int t = target.charAt(i) - 'a';

            // We cannot match target anymore.
            // Find the smallest character greater than target[i].
            if (freq[t] == 0) {
                for (int c = t + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        StringBuilder ans = new StringBuilder(prefix);
                        ans.append((char) ('a' + c));
                        freq[c]--;

                        appendSmallest(ans, freq);
                        return ans.toString();
                    }
                }

                // We cannot make this position greater,
                // so we need to change an earlier position.
                return backtrack(prefix, freq, target, i - 1);
            }

            // Keep this position equal to target.
            prefix.append(target.charAt(i));
            freq[t]--;
        }

        // s and target are exactly the same permutation.
        return backtrack(prefix, freq, target, target.length() - 1);
    }

    private String backtrack(
            StringBuilder prefix,
            int[] freq,
            String target,
            int index) {

        for (int i = index; i >= 0; i--) {

            // Restore characters after position i.
            if (i < prefix.length()) {
                freq[prefix.charAt(i) - 'a']++;
            }

            int t = target.charAt(i) - 'a';

            // Find the smallest character greater than target[i].
            for (int c = t + 1; c < 26; c++) {
                if (freq[c] > 0) {

                    StringBuilder ans =
                            new StringBuilder(prefix.substring(0, i));

                    ans.append((char) ('a' + c));
                    freq[c]--;

                    appendSmallest(ans, freq);

                    return ans.toString();
                }
            }
        }

        return "";
    }

    private void appendSmallest(StringBuilder ans, int[] freq) {
        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans.append((char) ('a' + c));
                freq[c]--;
            }
        }
    }
}