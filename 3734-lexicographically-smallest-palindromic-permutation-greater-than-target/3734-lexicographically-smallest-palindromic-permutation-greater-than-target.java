class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        // Characters for the first half
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        int halfLength = n / 2;

        // Special case: n == 1
        if (halfLength == 0) {
            char midChar = (char) ('a' + middle);

            return midChar > target.charAt(0)
                    ? String.valueOf(midChar)
                    : "";
        }

        String half = buildHalf(
                halfCount,
                target,
                halfLength
        );

        if (half.equals("")) {
            return "";
        }

        String result = buildPalindrome(half, middle);

        // If this palindrome is already greater, return it.
        if (result.compareTo(target) > 0) {
            return result;
        }

        // Otherwise, get the next permutation of the half.
        String nextHalf = nextPermutation(half);

        if (nextHalf.equals("")) {
            return "";
        }

        result = buildPalindrome(nextHalf, middle);

        return result.compareTo(target) > 0
                ? result
                : "";
    }

    private String buildHalf(
            int[] count,
            String target,
            int length) {

        int[] remaining = count.clone();
        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int t = target.charAt(i) - 'a';

            // Keep the prefix equal to target
            if (remaining[t] > 0) {
                prefix.append(target.charAt(i));
                remaining[t]--;
            } else {

                // Try to make this position greater
                for (int c = t + 1; c < 26; c++) {

                    if (remaining[c] > 0) {

                        StringBuilder ans =
                                new StringBuilder(prefix);

                        ans.append((char) ('a' + c));
                        remaining[c]--;

                        appendSmallest(ans, remaining);

                        return ans.toString();
                    }
                }

                // Cannot become greater here.
                break;
            }
        }

        // Successfully matched the whole half.
        if (prefix.length() == length) {
            return prefix.toString();
        }

        /*
         * We got stuck.
         * Go backwards and increase the latest possible position.
         */
        for (int i = prefix.length() - 1; i >= 0; i--) {

            remaining[prefix.charAt(i) - 'a']++;

            int t = target.charAt(i) - 'a';

            for (int c = t + 1; c < 26; c++) {

                if (remaining[c] > 0) {

                    StringBuilder ans =
                            new StringBuilder(prefix.substring(0, i));

                    ans.append((char) ('a' + c));
                    remaining[c]--;

                    appendSmallest(ans, remaining);

                    return ans.toString();
                }
            }
        }

        return "";
    }

    private void appendSmallest(
            StringBuilder ans,
            int[] count) {

        for (int c = 0; c < 26; c++) {
            while (count[c] > 0) {
                ans.append((char) ('a' + c));
                count[c]--;
            }
        }
    }

    private String buildPalindrome(
            String half,
            int middle) {

        StringBuilder result = new StringBuilder();

        result.append(half);

        if (middle != -1) {
            result.append((char) ('a' + middle));
        }

        result.append(
                new StringBuilder(half).reverse()
        );

        return result.toString();
    }

    private String nextPermutation(String s) {

        char[] arr = s.toCharArray();

        int i = arr.length - 2;

        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }

        if (i < 0) {
            return "";
        }

        int j = arr.length - 1;

        while (arr[j] <= arr[i]) {
            j--;
        }

        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        int left = i + 1;
        int right = arr.length - 1;

        while (left < right) {
            temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }

        return new String(arr);
    }
}