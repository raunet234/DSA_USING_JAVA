class Solution {

    int k;
    long[][] tree;
    int[] product;

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.k = k;

        int n = nums.length;

        tree = new long[4 * n][k];
        product = new int[4 * n];

        build(1, 0, n - 1, nums);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Persistent update
            update(1, 0, n - 1, index, value);

            // Get prefix-product counts in nums[start...n-1]
            long[] result = query(1, 0, n - 1, start, n - 1);

            answer[i] = (int) result[x];
        }

        return answer;
    }

    void build(int node, int left, int right, int[] nums) {

        if (left == right) {

            int rem = nums[left] % k;

            product[node] = rem;
            tree[node][rem] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, nums);
        build(node * 2 + 1, mid + 1, right, nums);

        merge(node);
    }

    void update(int node, int left, int right, int index, int value) {

        if (left == right) {

            for (int i = 0; i < k; i++) {
                tree[node][i] = 0;
            }

            int rem = value % k;

            product[node] = rem;
            tree[node][rem] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        merge(node);
    }

    void merge(int node) {

        int left = node * 2;
        int right = node * 2 + 1;

        for (int i = 0; i < k; i++) {
            tree[node][i] = 0;
        }

        /*
         * Prefixes completely inside LEFT
         */
        for (int r = 0; r < k; r++) {
            tree[node][r] += tree[left][r];
        }

        /*
         * Prefixes that use all of LEFT
         * and then a prefix of RIGHT
         */
        for (int r = 0; r < k; r++) {

            if (tree[right][r] == 0) {
                continue;
            }

            int newRemainder =
                    (int) ((long) product[left] * r % k);

            tree[node][newRemainder] += tree[right][r];
        }

        /*
         * Product of the complete segment
         */
        product[node] =
                (int) ((long) product[left] * product[right] % k);
    }

    long[] query(
            int node,
            int left,
            int right,
            int ql,
            int qr) {

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return query(node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, right, ql, qr);
        }

        long[] leftResult =
                query(node * 2, left, mid, ql, qr);

        long[] rightResult =
                query(node * 2 + 1, mid + 1, right, ql, qr);

        /*
         * Need product of the complete LEFT part
         * that belongs to the query.
         */
        int leftProduct =
                getProduct(node * 2, left, mid, ql, qr);

        long[] result = new long[k];

        // Prefixes completely inside LEFT
        for (int r = 0; r < k; r++) {
            result[r] += leftResult[r];
        }

        // LEFT complete + prefix of RIGHT
        for (int r = 0; r < k; r++) {

            if (rightResult[r] == 0) {
                continue;
            }

            int newRemainder =
                    (int) ((long) leftProduct * r % k);

            result[newRemainder] += rightResult[r];
        }

        return result;
    }

    int getProduct(
            int node,
            int left,
            int right,
            int ql,
            int qr) {

        if (ql <= left && right <= qr) {
            return product[node];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return getProduct(node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return getProduct(node * 2 + 1, mid + 1, right, ql, qr);
        }

        int leftProduct =
                getProduct(node * 2, left, mid, ql, qr);

        int rightProduct =
                getProduct(node * 2 + 1, mid + 1, right, ql, qr);

        return (int) ((long) leftProduct * rightProduct % k);
    }
}