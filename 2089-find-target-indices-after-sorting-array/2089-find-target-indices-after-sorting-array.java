class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        
        List<Integer> arr= new ArrayList<>();
        for(int i=0;i<nums.length-1;i++){
            for(int j=0;j<nums.length-1-i;j++){
                if(nums[j]>nums[j+1]){
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                }
            }
        }
        for(int i=0;i<nums.length;i++){
            if(nums[i]==target){
                arr.add(i);
            }
        }
        return arr;   
        
    }
}