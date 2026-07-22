import java.util.*;

/**
 * LC 3501. Maximize Active Section with Trade II (Hard)
 *
 * KEY INSIGHT
 * A trade = pick a '1'-block surrounded by two '0'-blocks (pattern 0...0 1...1 0...0).
 * Converting the 1s to 0s merges everything into one big 0-block, which (thanks to the
 * augmented '1's at both ends) is surrounded by 1s, so it all flips to 1s.
 *   => gain = len(leftZeroBlock) + len(rightZeroBlock)
 *   => answer = totalOnes(s) + max over ADJACENT zero-run pairs inside [l, r]
 *
 * Only the FIRST and LAST zero-runs touching [l, r] can be clipped by the query
 * boundary; every zero-run strictly between them is fully contained. So per query:
 *   - pair (a, a+1) and pair (b-1, b): handle with clipped lengths      -> O(1)
 *   - all fully-interior pairs (i, i+1), a+1 <= i <= b-2: precomputed
 *     sparse table over pairSum[i] = zl[i] + zl[i+1]                    -> O(1)
 *
 * Time:  O((n + q) log n)   Space: O(n log n)
 */
class Solution {
    private int[] zs, ze, zl;      // zero-run start / end / length
    private int[][] sp;            // sparse table over adjacent-pair sums
    private int[] logTable;

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        long totalOnes = 0;
        for (int i = 0; i < n; i++) if (s.charAt(i) == '1') totalOnes++;

        // ---- 1. Decompose s into zero-runs ----
        List<int[]> runs = new ArrayList<>();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == '0') {
                int j = i;
                while (j < n && s.charAt(j) == '0') j++;
                runs.add(new int[]{i, j - 1});
                i = j;
            } else i++;
        }
        int m = runs.size();
        zs = new int[m]; ze = new int[m]; zl = new int[m];
        for (int k = 0; k < m; k++) {
            zs[k] = runs.get(k)[0];
            ze[k] = runs.get(k)[1];
            zl[k] = ze[k] - zs[k] + 1;
        }

        // ---- 2. Sparse table over pairSum[k] = zl[k] + zl[k+1] ----
        int psz = Math.max(m - 1, 0);
        logTable = new int[psz + 2];
        for (int k = 2; k <= psz + 1; k++) logTable[k] = logTable[k / 2] + 1;
        int LOG = logTable[Math.max(psz, 1)] + 1;
        sp = new int[LOG][Math.max(psz, 1)];
        for (int k = 0; k < psz; k++) sp[0][k] = zl[k] + zl[k + 1];
        for (int j = 1; j < LOG; j++)
            for (int k = 0; k + (1 << j) <= psz; k++)
                sp[j][k] = Math.max(sp[j - 1][k], sp[j - 1][k + (1 << (j - 1))]);

        // ---- 3. Answer queries ----
        List<Integer> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            int l = q[0], r = q[1];
            long gain = 0;

            int a = firstGE(ze, l);   // first zero-run intersecting [l, r]
            int b = lastLE(zs, r);    // last  zero-run intersecting [l, r]

            if (a < m && b >= 0 && b >= a + 1) {   // need >= 2 zero-runs in range
                long effA = Math.min(ze[a], r) - Math.max(zs[a], l) + 1; // clipped left run
                long effB = Math.min(ze[b], r) - Math.max(zs[b], l) + 1; // clipped right run

                // pair (a, a+1): right member is full unless a+1 == b
                gain = Math.max(gain, effA + (a + 1 < b ? zl[a + 1] : effB));
                // pair (b-1, b): left member is full unless b-1 == a
                gain = Math.max(gain, (b - 1 > a ? zl[b - 1] : effA) + effB);
                // fully-interior adjacent pairs
                if (a + 1 <= b - 2) gain = Math.max(gain, rangeMax(a + 1, b - 2));
            }
            ans.add((int)(totalOnes + gain));
        }
        return ans;
    }

    private int rangeMax(int lo, int hi) {
        int j = logTable[hi - lo + 1];
        return Math.max(sp[j][lo], sp[j][hi - (1 << j) + 1]);
    }

    private int firstGE(int[] arr, int target) {   // first idx with arr[idx] >= target
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] >= target) hi = mid; else lo = mid + 1;
        }
        return lo;
    }

    private int lastLE(int[] arr, int target) {    // last idx with arr[idx] <= target
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= target) lo = mid + 1; else hi = mid;
        }
        return lo - 1;
    }
}