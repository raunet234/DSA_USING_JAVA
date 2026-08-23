class Solution {
    public double findMaxAverage(int[] nums, int k) {
      
      int sum = 0;
      //first window sum
      for(int i = 0 ;i<k; i++){
        sum = sum + nums[i];
      }

      int maxSum = sum;
      for(int i = k;i<nums.length;i++){
        //slide the window now
        sum = sum - nums[i-k];
        sum = sum + nums[i];
        maxSum = Math.max(maxSum,sum);
      }
      return (double) maxSum/k;
    }
}