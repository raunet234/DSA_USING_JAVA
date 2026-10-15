import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        findCombinations(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    public void findCombinations(int[] candidates, int target, int index,
                                 List<Integer> current, List<List<Integer>> result) {

        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0 || index == candidates.length) {
            return;
        }

        // Pick the current number
        current.add(candidates[index]);
        findCombinations(candidates, target - candidates[index], index, current, result);

        // Backtrack: remove the picked number
        current.remove(current.size() - 1);

        // Skip the current number
        findCombinations(candidates, target, index + 1, current, result);
    }
}