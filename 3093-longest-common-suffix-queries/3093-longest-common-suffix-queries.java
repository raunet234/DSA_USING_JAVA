class Solution {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int index = -1;
    }

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {

        TrieNode root = new TrieNode();

        for (int i = 0; i < wordsContainer.length; i++) {
            insert(root, wordsContainer[i], i, wordsContainer);
        }

        int[] answer = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; i++) {
            answer[i] = search(root, wordsQuery[i]);
        }

        return answer;
    }

    private void insert(
        TrieNode root,
        String word,
        int index,
        String[] wordsContainer
    ) {
        TrieNode node = root;

        updateIndex(node, index, wordsContainer);

        for (int i = word.length() - 1; i >= 0; i--) {

            int c = word.charAt(i) - 'a';

            if (node.children[c] == null) {
                node.children[c] = new TrieNode();
            }

            node = node.children[c];

            updateIndex(node, index, wordsContainer);
        }
    }

    private void updateIndex(
        TrieNode node,
        int index,
        String[] wordsContainer
    ) {
        if (node.index == -1) {
            node.index = index;
            return;
        }

        String current = wordsContainer[node.index];
        String candidate = wordsContainer[index];

        if (candidate.length() < current.length()) {
            node.index = index;
        } else if (
            candidate.length() == current.length()
            && index < node.index
        ) {
            node.index = index;
        }
    }

    private int search(TrieNode root, String word) {

        TrieNode node = root;

        int answer = root.index;

        for (int i = word.length() - 1; i >= 0; i--) {

            int c = word.charAt(i) - 'a';

            if (node.children[c] == null) {
                break;
            }

            node = node.children[c];

            answer = node.index;
        }

        return answer;
    }
}