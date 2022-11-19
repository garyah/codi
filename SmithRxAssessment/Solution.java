import java.util.ArrayList;

// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class TestCase<Input, Output> {
    public Input input;
    public Output expected;
    public TestCase(Input input, Output expected) {
        this.input = input;
        this.expected = expected;
    }
}

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();

        ArrayList<TestCase<Integer, Integer>> testCases = new ArrayList<TestCase<Integer, Integer>>();

        // the test cases
        testCases.add(new TestCase<Integer, Integer>(0, 0));
        testCases.add(new TestCase<Integer, Integer>(9, 2)); // 1001 (1 gap)
        testCases.add(new TestCase<Integer, Integer>(529, 4)); // 1000010001 (2 gaps, largest is length 4)
        testCases.add(new TestCase<Integer, Integer>(20, 1)); // 10100 (1 gap, zeroes without 1 on end)
        testCases.add(new TestCase<Integer, Integer>(15, 0)); // 1111 (0 gaps, no zeroes)
        testCases.add(new TestCase<Integer, Integer>(32, 0)); // 100000 (0 gaps, zeroes without 1 on end)
        testCases.add(new TestCase<Integer, Integer>(1041, 5)); // 10000010001 (2 gaps, largest is length 5)

        for (TestCase<Integer, Integer> testCase : testCases) {
            Integer actual = solution.solution(testCase.input);
            System.out.print("input = " + testCase.input + ", actual = " + actual + ", expected = " + testCase.expected);
            System.out.println((actual == testCase.expected) ? ": test case passed" : ": test case failed");
        }
    }

    public int solution(int N) {
        // range check
        if (N < 1) return 0;

        // initialize state
        boolean gotFirstOne = false;
        boolean gotFirstZero = false;
        int gapSize = 0;
        int maxGapSize = 0;

        // shift all bits of number right
        for ( ; N != 0; N >>= 1 ) {
            if ((N & 1) == 1) {
                // got 1 bit, advance state
                gotFirstOne = true;
                if (gotFirstZero) {
                    // already saw a 0 bit previously, compute new max gap size, reset current one
                    maxGapSize = Math.max(gapSize, maxGapSize);
                    gapSize = 0;
                }
                gotFirstZero = false;
                continue;
            }
            // got 0 bit
            if (gotFirstOne) {
                // already saw a 1 bit previously, advance state, increase size of current gap
                gotFirstZero = true;
                gapSize++;
            }
        }
        return maxGapSize;
    }
}
