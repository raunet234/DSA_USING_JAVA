class Solution {
    public int maximumProduct(int[] nums) {
        int maxPro = 0;
        int pro = 0;
        int n = nums.length;
        Arrays.sort(nums);
        
        maxPro = nums[n-1]*nums[n-2]*nums[n-3];
        pro = nums[0]*nums[1]*nums[n-1];
        maxPro = Math.max(maxPro,pro);
        return maxPro;
    }

}