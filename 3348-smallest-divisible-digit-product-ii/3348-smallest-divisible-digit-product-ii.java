class Solution {
    public String smallestNumber(String num, long t) {
        long tt = t;
        for (int p : new int[]{2,3,5,7}) while (tt % p == 0) tt /= p;
        if (tt != 1) return "-1";

        int n = num.length();
        char[] a = num.toCharArray();

        // Check if num itself works
        long need = t;
        boolean zero = false;
        for (int i = 0; i < n; i++) {
            if (a[i] == '0') { zero = true; break; }
            need /= gcd(need, a[i]-'0');
        }
        if (!zero && need == 1) return new String(a);

        // prefix reductions (only valid up to first zero)
        long[] pref = new long[n+1];
        pref[0] = t;
        int firstZero = n;
        for (int i = 0; i < n; i++) {
            if (a[i]=='0'){ firstZero=i; break; }
            pref[i+1] = pref[i] / gcd(pref[i], a[i]-'0');
        }

        // Try increasing at position i, keep prefix, fill suffix
        for (int i = n-1; i >= 0; i--) {
            if (i > firstZero) continue;
            long rem = pref[i];
            for (int d = (a[i]-'0')+1; d <= 9; d++) {
                long r = rem / gcd(rem, d);
                String suf = fill(r, n-1-i);
                if (suf != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(a, 0, i);
                    sb.append((char)('0'+d));
                    sb.append(suf);
                    return sb.toString();
                }
            }
        }

        // Longer numbers
        // Minimum length needed = at least ceil to fit factors; try increasing lengths
        for (int len = n+1; ; len++) {
            String s = fill(t, len);
            if (s != null) return s;
            if (len > n + 60) break; // safety; factors of t bounded
        }
        return "-1";
    }

    // smallest lexicographic string of exactly len digits (1-9), product divisible by rem
    private String fill(long rem, int len) {
        if (len < 0) return null;
        long[] f = factorize(rem);
        if (f == null) return null;
        long n2=f[0], n3=f[1], n5=f[2], n7=f[3];
        // minimum positions required: each 7 needs its own slot, each 5 its own slot,
        // 2s: 9 gives 0, 8 gives three 2s -> min slots for 2s = ceil(n2/3), for 3s = ceil(n3/2)
        // but a single slot can't serve both a 2-heavy and 3-heavy at max simultaneously except 6(2*3)
        long slots5 = n5, slots7 = n7;
        // remaining slots (after 5s,7s) must cover 2s and 3s
        int free = len - (int)Math.min(len+1L, slots5+slots7);
        if (slots5 + slots7 > len) return null;
        free = len - (int)(slots5 + slots7);
        // min slots to cover n2,n3 using best digits (8=2^3, 9=3^2, 6=2*3)
        if (!feasible(n2, n3, free)) return null;

        char[] res = new char[len];
        for (int i = 0; i < len; i++) {
            int rest = len - i - 1;
            for (int d = 1; d <= 9; d++) {
                long[] use = digitFactors(d);
                long a2 = Math.max(0, n2 - use[0]);
                long a3 = Math.max(0, n3 - use[1]);
                long a5 = Math.max(0, n5 - use[2]);
                long a7 = Math.max(0, n7 - use[3]);
                // this digit only helps if it actually reduces something needed (else fine too)
                if (canFill(a2, a3, a5, a7, rest)) {
                    res[i] = (char)('0'+d);
                    n2=a2; n3=a3; n5=a5; n7=a7;
                    break;
                }
            }
        }
        return new String(res);
    }

    private long[] digitFactors(int d) {
        long[] r = new long[4];
        int[] ps={2,3,5,7};
        for (int k=0;k<4;k++){ while(d%ps[k]==0){d/=ps[k];r[k]++;} }
        return r;
    }

    // can we satisfy needed 2,3,5,7 counts in exactly `slots` digits (1-9)?
    private boolean canFill(long n2, long n3, long n5, long n7, int slots) {
        if (slots < 0) return false;
        if (n5 + n7 > slots) return false;
        int free = slots - (int)(n5 + n7);
        return feasible(n2, n3, free);
    }

    // cover n2 twos and n3 threes in `slots` digits from {2,3,4,6,8,9,1}
    private boolean feasible(long n2, long n3, int slots) {
        if (slots < 0) return false;
        // greedy min slots: use 8 (2^3), 9 (3^2), 6 (2*3)
        // lower bound on slots = ceil over max packing. Compute minimal slots.
        long min = 0;
        // pair leftover 2 and 3 into 6 later; first use 8s and 9s
        long eights = n2 / 3; long r2 = n2 % 3;
        long nines = n3 / 2; long r3 = n3 % 2;
        min = eights + nines;
        // remaining r2 (0-2) twos, r3 (0-1) threes
        if (r2 > 0 && r3 > 0) { min += 1; r2 = Math.max(0, r2-1); r3=0; } // 6 covers one 2 and one 3; extra 2 handled next
        min += (r2>0?1:0) + (r3>0?1:0);
        return min <= slots;
    }

    private long[] factorize(long x) {
        long[] r=new long[4]; int[] ps={2,3,5,7};
        for(int k=0;k<4;k++){while(x%ps[k]==0){x/=ps[k];r[k]++;}}
        return x==1?r:null;
    }
    private long gcd(long a,long b){while(b!=0){long t=a%b;a=b;b=t;}return a;}
}