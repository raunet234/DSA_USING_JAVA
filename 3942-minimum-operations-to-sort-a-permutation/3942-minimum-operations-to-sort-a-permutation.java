class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zeroIndex = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zeroIndex = i;
                break;
            }
        }

        int rotate = zeroIndex;
        boolean normal = true;

        for (int i = 0; i < n; i++) {
            if (nums[(i + rotate) % n] != i) {
                normal = false;
                break;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (normal) {
            ans = Math.min(rotate, n - rotate + 2);
        }

        int reverseRotate = (zeroIndex + 1) % n;
        boolean reversed = true;

        for (int i = 0; i < n; i++) {
            int index = (reverseRotate + n - 1 - i) % n;

            if (index < 0) {
                index += n;
            }

            if (nums[index] != i) {
                reversed = false;
                break;
            }
        }

        if (reversed) {
            ans = Math.min(ans,
                    Math.min(reverseRotate + 1, n - reverseRotate + 1));
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}