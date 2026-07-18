class Solution {

    public int numIslands(char[][] grid) {// methode
        int count = 0;
        for(int i= 0; i<grid.length; i++){
            for(int j= 0; j<grid[0].length;j++){
                if(grid[i][j]== '1'){
                    count++;
                    dfs(grid, i , j);//this is dfs function calling dfs function below
                }
            }
        }
        return count;//finally return the no. of island
    }
    public void dfs(char[][] grid, int row,int col){ //this is dfs function who Explore the entire island starting from this cell.
        //out of bound
        if(row<0 || row>= grid.length || col<0 || col>=grid[0].length)
        return;
        //water or already visited
        if(grid[row][col] == '0')
        return;

        //mark as visited
        grid[row][col] = '0';

        //visit all 4 directions
        dfs(grid, row-1,col);
        dfs(grid, row+1, col);
        dfs(grid, row, col-1);
        dfs(grid, row, col+1);
    }
}