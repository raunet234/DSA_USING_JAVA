class Solution {
    public boolean checkValidString(String s) {

        int minOpen = 0;
        int maxOpen = 0;

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c == '(') {
                minOpen++;
                maxOpen++;
            } 
            else if (c == ')') {
                minOpen--;
                maxOpen--;
            } 
            else {
                // '*' can be '(' or ')' or empty
                minOpen--;
                maxOpen++;
            }

            // Even in the best case, we have too many ')'
            if (maxOpen < 0) {
                return false;
            }

            // We cannot have negative minimum
            minOpen = Math.max(minOpen, 0);
        }

        return minOpen == 0;
    }
}