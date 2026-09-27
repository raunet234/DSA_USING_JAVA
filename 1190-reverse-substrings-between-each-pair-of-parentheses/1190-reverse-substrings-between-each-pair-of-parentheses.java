import java.util.*;

class Solution {
    public String reverseParentheses(String s) {

        Stack<StringBuilder> stack = new Stack<>();

        StringBuilder current = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c == '(') {

                stack.push(current);
                current = new StringBuilder();

            } else if (c == ')') {

                current.reverse();

                StringBuilder previous = stack.pop();

                previous.append(current);

                current = previous;

            } else {

                current.append(c);
            }
        }

        return current.toString();
    }
}