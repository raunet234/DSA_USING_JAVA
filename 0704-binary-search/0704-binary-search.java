class Solution {
    public int search(int[] nums, int target) {

   int left= 0;
   int right = nums.length - 1;
   while(left <= right){
    int midle = left + (right - left)/2;
    if(target == nums[midle]) return midle;
    if(target> nums[midle]){
        left = midle + 1;
    }else{
        right = midle - 1;
    }
   }
   return -1;
    }       
}
