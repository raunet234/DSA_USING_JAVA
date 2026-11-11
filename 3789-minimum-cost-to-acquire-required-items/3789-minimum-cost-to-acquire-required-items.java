
class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth,
                            int need1, int need2) {

        // Strategy 1: Buy separate items
        long costA = (long) need1 * cost1
                   + (long) need2 * cost2;

        // Strategy 2: Buy type 3 for the smaller requirement
        long costB;

        if (need1 <= need2) {
            costB = (long) need1 * costBoth
                  + (long) (need2 - need1) * cost2;
        } else {
            costB = (long) need2 * costBoth
                  + (long) (need1 - need2) * cost1;
        }

        // Strategy 3: Buy only type 3 items
        long costC = (long) Math.max(need1, need2) * costBoth;

        return Math.min(costA, Math.min(costB, costC));
    }
}
