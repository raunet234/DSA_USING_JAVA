// Define the Solution class.
class Solution {

    // Define the method that finds the longest valid substring.
    public int characterReplacement(String s, int k) {

        // Create an array to store the frequency of each uppercase letter A-Z.
        int[] freq = new int[26];

        // 'left' represents the starting index of our sliding window.
        int left = 0;

        // Store the highest frequency of any character inside the window.
        int maxFreq = 0;

        // Store the maximum valid window length found so far.
        int maxLength = 0;

        // Move the right pointer from the beginning to the end of the string.
        for (int right = 0; right < s.length(); right++) {

            // Increase the frequency of the character entering the window.
            freq[s.charAt(right) - 'A']++;

            // Update maxFreq if the current character has become the most frequent.
            maxFreq = Math.max(
                maxFreq,
                freq[s.charAt(right) - 'A']
            );

            // If the number of replacements needed is greater than k,
            // the current window is invalid, so we need to shrink it.
            while ((right - left + 1) - maxFreq > k) {

                // Remove the character at the left side from our frequency count.
                freq[s.charAt(left) - 'A']--;

                // Move the left pointer one position to the right.
                left++;
            }

            // Calculate the current valid window length
            // and update maxLength if this window is larger.
            maxLength = Math.max(
                maxLength,
                right - left + 1
            );
        }

        // Return the longest valid window length.
        return maxLength;
    }
}