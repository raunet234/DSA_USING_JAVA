class Solution {
    public int[] sortedSquares(int[] nums) {
        
        int n = nums.length;
        int left = 0;
        int right = n-1;
        int wp = n-1; 
        
        int[] result= new int[n];
        while(left<=right){
            int leftSqr = nums[left] * nums[left];
            int rightSqr = nums[right] * nums[right];

            if(leftSqr>rightSqr){
                result[wp] = leftSqr;
                left++;
            } 
            else{
                result[wp]= rightSqr;
                right--;
            }
            wp--; 
        }
        return result;
    }
}