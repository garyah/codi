package SmallestMissingInteger;

// you can also use imports, for example:
import java.util.Arrays;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int solution(int[] A) {
        // write your code in Java SE 8
        int r = 1;
        Arrays.sort(A);
        for (int n : A) {
            if (n == r) r++;
        }
        return r;
    }
}
