class Solution {

    public int numIslands(char[][] grid) {

        // This will count how many islands we find
        int count = 0;

        // Go through every row
        for (int i = 0; i < grid.length; i++) {

            // Go through every column
            for (int j = 0; j < grid[0].length; j++) {

                // If we find land
                if (grid[i][j] == '1') {

                    // We found a new island
                    count++;

                    // Explore the whole island
                    dfs(grid, i, j);
                }
            }
        }

        // Return the total number of islands
        return count;
    }


    // This function explores the entire island
    // starting from the current cell
    public void dfs(char[][] grid, int row, int col) {

        // If we go outside the grid, stop
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length)
            return;


        // If this cell is water ('0')
        // OR we already visited it,
        // stop exploring this direction
        if (grid[row][col] == '0')
            return;


        // Mark this land as visited
        // Change '1' to '0'
        grid[row][col] = '0';


        // Now explore the cell above
        dfs(grid, row - 1, col);

        // Explore the cell below
        dfs(grid, row + 1, col);

        // Explore the cell on the left
        dfs(grid, row, col - 1);

        // Explore the cell on the right
        dfs(grid, row, col + 1);
    }
}