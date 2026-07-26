class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int right = 0;
        int left = 1 ;

        for(int i = 0;i<piles.length;i++){
            right = Math.max(right,piles[i]);
        }
        while(left <= right){
            int mid = left + (right - left)/2;
            long hours = 0;
            for( int i=0;i<piles.length;i++){
                hours = hours + (piles[i] + mid -1)/mid;
            }
            if(hours<=h){
                right = mid -1;
            }else{
                left = mid + 1;
            }
            
        }
        return left;
    }
}