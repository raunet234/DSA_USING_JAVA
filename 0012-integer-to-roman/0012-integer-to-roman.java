class Solution {

    public String intToRoman(int num) {

        // Roman values from biggest to smallest
        // We also include special cases like
        // 900, 400, 90, 40, 9 and 4
        int[] values = {
            1000, 900, 500, 400,
            100, 90, 50, 40,
            10, 9, 5, 4, 1
        };


        // Roman symbols matching the values above
        String[] symbols = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV", "I"
        };


        // This will store our final Roman number
        StringBuilder result = new StringBuilder();


        // Go through all Roman values
        // from biggest to smallest
        for (int i = 0; i < values.length; i++) {

            // Keep using this Roman value
            // as long as it fits into num
            while (num >= values[i]) {

                // Add the Roman symbol
                result.append(symbols[i]);

                // Subtract its value from num
                num -= values[i];
            }
        }


        // Convert StringBuilder to String
        return result.toString();
    }
}