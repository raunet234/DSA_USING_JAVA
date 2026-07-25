class Solution {
    public int maxProduct(int n) {
        int maxDigit = 0;
        int secondMax = 0;
        while(n>0){
            int digit = n%10;
            
           if(digit>=maxDigit){
            secondMax = maxDigit;
            maxDigit = digit;
           }else{
            secondMax = Math.max(secondMax,digit);
           }
            n = n/10;
        }
        return maxDigit*secondMax;
    }
}