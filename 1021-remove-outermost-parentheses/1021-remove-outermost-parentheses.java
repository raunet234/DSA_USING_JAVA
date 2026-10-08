class Solution {
    public String removeOuterParentheses(String s) {

        StringBuilder answer = new StringBuilder();

        int depth = 0;

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c == '(') {

                // Add '(' only if it is NOT the outermost one
                if (depth > 0) {
                    answer.append(c);
                }

                depth++;

            } else {

                depth--;

                // Add ')' only if it is NOT the outermost one
                if (depth > 0) {
                    answer.append(c);
                }
            }
        }

        return answer.toString();
    }
}