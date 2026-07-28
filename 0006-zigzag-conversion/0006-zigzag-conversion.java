class Solution {
    public String convert(String s, int numRows) {

        // If only one row or string is too short
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        // Create a StringBuilder for each row
        StringBuilder[] rows = new StringBuilder[numRows];

        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRow = 0;
        boolean goingDown = true;

        // Traverse each character
        for (char c : s.toCharArray()) {

            rows[currentRow].append(c);

            // Change direction at the top or bottom
            if (currentRow == 0) {
                goingDown = true;
            } else if (currentRow == numRows - 1) {
                goingDown = false;
            }

            // Move to the next row
            if (goingDown) {
                currentRow++;
            } else {
                currentRow--;
            }
        }

        // Combine all rows
        StringBuilder result = new StringBuilder();

        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }
}