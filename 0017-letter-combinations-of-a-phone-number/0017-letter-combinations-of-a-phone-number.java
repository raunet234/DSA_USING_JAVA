class Solution {

    public List<String> letterCombinations(String digits) {

        // Store all possible letters for each digit
        String[] phone = {
            "", "", "abc", "def",
            "ghi", "jkl", "mno",
            "pqrs", "tuv", "wxyz"
        };

        // Store the final combinations
        List<String> result = new ArrayList<>();

        // Start building combinations
        backtrack(digits, 0, "", result, phone);

        return result;
    }


    public void backtrack(
        String digits,
        int index,
        String current,
        List<String> result,
        String[] phone
    ) {

        // If we used all digits,
        // we have one complete combination
        if (index == digits.length()) {
            result.add(current);
            return;
        }

        // Get the letters for the current digit
        String letters = phone[digits.charAt(index) - '0'];


        // Try every letter
        for (char letter : letters.toCharArray()) {

            // Add the letter
            // and move to the next digit
            backtrack(
                digits,
                index + 1,
                current + letter,
                result,
                phone
            );
        }
    }
}