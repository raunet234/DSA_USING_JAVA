class Solution {

    public int[] arrayRankTransform(int[] arr) {

        // Make a copy of the original array
        int[] sorted = arr.clone();


        // Sort the copied array
        Arrays.sort(sorted);


        // This map will store:
        // number -> rank
        Map<Integer, Integer> numToRank = new HashMap<>();


        // Smallest number gets rank 1
        int rank = 1;


        // Go through the sorted array
        for (int num : sorted) {

            // If this number does NOT have a rank yet
            if (!numToRank.containsKey(num)) {

                // Give this number the current rank
                numToRank.put(num, rank);

                // Move to the next rank
                rank++;
            }
        }


        // Go through the original array
        for (int i = 0; i < arr.length; i++) {

            // Replace the number with its rank
            arr[i] = numToRank.get(arr[i]);
        }


        // Return the transformed array
        return arr;
    }
}