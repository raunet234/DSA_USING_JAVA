class Solution {
    public List<String> findRepeatedDnaSequences(String s) {

        HashSet<String> seen = new HashSet<>();
        HashSet<String> repeated = new HashSet<>();

        for (int i = 0; i <= s.length() - 10; i++) {

            String dna = s.substring(i, i + 10);

            if (!seen.add(dna)) {
                repeated.add(dna);
            }
        }

        return new ArrayList<>(repeated);
    }
}