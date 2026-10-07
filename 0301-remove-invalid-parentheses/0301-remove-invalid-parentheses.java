import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {

        List<String> answer = new ArrayList<>();

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                if (isValid(current)) {
                    answer.add(current);
                    found = true;
                }

                // If we already found valid strings,
                // don't remove more characters.
                if (found) {
                    continue;
                }

                for (int j = 0; j < current.length(); j++) {

                    // Removing a letter is unnecessary
                    if (current.charAt(j) != '(' &&
                        current.charAt(j) != ')') {
                        continue;
                    }

                    String next =
                        current.substring(0, j) +
                        current.substring(j + 1);

                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }

            if (found) {
                break;
            }
        }

        return answer;
    }

    private boolean isValid(String s) {

        int balance = 0;

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c == '(') {
                balance++;
            } 
            else if (c == ')') {
                balance--;

                // More ')' than '('
                if (balance < 0) {
                    return false;
                }
            }
        }

        return balance == 0;
    }
}