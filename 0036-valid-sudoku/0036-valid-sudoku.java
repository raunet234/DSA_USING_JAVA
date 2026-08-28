class Solution {
    public boolean isValidSudoku(char[][] board) {

        HashSet<String> set = new HashSet<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                char num = board[row][col];

                // Ignore empty cells
                if (num == '.') {
                    continue;
                }

                // Check row
                String rowKey = num + " in row " + row;

                // Check column
                String colKey = num + " in col " + col;

                // Check 3 x 3 box
                String boxKey = num + " in box " 
                        + (row / 3) + "-" + (col / 3);

                if (!set.add(rowKey) ||
                    !set.add(colKey) ||
                    !set.add(boxKey)) {
                    return false;
                }
            }
        }

        return true;
    }
}