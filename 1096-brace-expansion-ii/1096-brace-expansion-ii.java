import java.util.*;

class Solution {

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression, 0, expression.length() - 1);

        List<String> answer = new ArrayList<>(result);
        Collections.sort(answer);

        return answer;
    }

    private Set<String> parse(String s, int left, int right) {

        Set<String> result = new HashSet<>();

        int balance = 0;
        int start = left;

        for (int i = left; i <= right; i++) {

            char c = s.charAt(i);

            if (c == '{') {
                balance++;
            } else if (c == '}') {
                balance--;
            }

            // Top-level comma means UNION
            if (c == ',' && balance == 0) {

                Set<String> part = parse(s, start, i - 1);
                result.addAll(part);

                start = i + 1;
            }
        }

        // Handle remaining part after the last comma
        if (start <= right) {
            Set<String> part = parsePart(s, start, right);
            result.addAll(part);
        }

        return result;
    }

    private Set<String> parsePart(String s, int left, int right) {

        Set<String> result = new HashSet<>();
        result.add("");

        int i = left;

        while (i <= right) {

            Set<String> current;

            if (s.charAt(i) == '{') {

                int balance = 1;
                int j = i + 1;

                while (balance > 0) {

                    if (s.charAt(j) == '{') {
                        balance++;
                    } else if (s.charAt(j) == '}') {
                        balance--;
                    }

                    j++;
                }

                // Parse inside { }
                current = parse(s, i + 1, j - 2);

                i = j;

            } else {

                current = new HashSet<>();
                current.add(String.valueOf(s.charAt(i)));

                i++;
            }

            // Cartesian product = concatenation
            Set<String> next = new HashSet<>();

            for (String a : result) {
                for (String b : current) {
                    next.add(a + b);
                }
            }

            result = next;
        }

        return result;
    }
}