class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        // Store reserved seats for each row
        Map<Integer, Set<Integer>> map = new HashMap<>();

        // Add every reserved seat to its row
        for (int[] seat : reservedSeats) {
            map.computeIfAbsent(seat[0], k -> new HashSet<>())
               .add(seat[1]);
        }

        // Every row can normally fit 2 groups
        // So start with 2 groups for every row
        int answer = (n - map.size()) * 2;

        // Only rows having reserved seats need checking
        for (Set<Integer> reserved : map.values()) {

            // Check the three possible blocks
            boolean left = true;   // 2,3,4,5
            boolean middle = true; // 4,5,6,7
            boolean right = true;  // 6,7,8,9

            // Check left block
            for (int seat = 2; seat <= 5; seat++) {
                if (reserved.contains(seat)) {
                    left = false;
                    break;
                }
            }

            // Check middle block
            for (int seat = 4; seat <= 7; seat++) {
                if (reserved.contains(seat)) {
                    middle = false;
                    break;
                }
            }

            // Check right block
            for (int seat = 6; seat <= 9; seat++) {
                if (reserved.contains(seat)) {
                    right = false;
                    break;
                }
            }

            // Both left and right are free
            // We can place 2 groups
            if (left && right) {
                answer += 2;
            }

            // At least one block is free
            // We can place 1 group
            else if (left || middle || right) {
                answer += 1;
            }

            // No block is free
            // We add nothing
        }

        return answer;
    }
}