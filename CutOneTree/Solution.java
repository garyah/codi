import java.util.ArrayList;

class Input {
    public int field1;
    public boolean field2;
    public String field3;
    public Input(int field1, boolean field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}

class Output {
    public int field1;
    public boolean field2;
    public String field3;
    public Output(int field1, boolean field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}

class TestCase<TInput, TOutput> {
    public TInput input;
    public TOutput expected;
    public TestCase(TInput input, TOutput expected) {
        this.input = input;
        this.expected = expected;
    }
}

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();

        ArrayList<TestCase<int[], Integer>> testCases = new ArrayList<TestCase<int[], Integer>>();

        // the test cases
        testCases.add(new TestCase<int[], Integer>(new int[]{3,4,5,3,7}, 3));
        testCases.add(new TestCase<int[], Integer>(new int[]{1,2,3,4}, -1));
        testCases.add(new TestCase<int[], Integer>(new int[]{1,3,1,2}, 0));
        testCases.add(new TestCase<int[], Integer>(new int[]{999,1000,998,999}, 0));
        testCases.add(new TestCase<int[], Integer>(new int[]{3,4,4,3,7}, 2));
        testCases.add(new TestCase<int[], Integer>(new int[]{3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7}, -1));

        for (TestCase<int[], Integer> testCase : testCases) {
            Integer actual = solution.solution(testCase.input);
            System.out.println(testCase.input.length);
            System.out.print("input = " + testCase.input + ", actual = " + actual + ", expected = " + testCase.expected);
            System.out.println((actual == testCase.expected) ? ": test case passed" : ": test case failed");
        }

        int[] test = new int[200];
        for (int i = 0; i < test.length; i++) {
            // if (i % 2 == 1) test[i] = 5;
            // else test[i] = 6;

            // test[i] = (int)(Math.random() * 100);

            if (Math.random() > 0.5) test[i] = 5;
            else test[i] = 6;

            System.out.print(test[i] + ((i < test.length - 1) ? ", " : ""));
        }
        System.out.println();
        System.out.println(solution.solution(test));
    }

    public int solution(int[] A) {
        return numWaysToFix(A);
    }

    public int numWaysToFix(int[] A) {
        // initialize state
        int N = A.length;

        // assume that initially, trees are aesthetically pleasing
        boolean isPleasing = true;

        // number of ways to fix garden as follows:
        // 3 = 3 ways to fix by removing one tree, (heights increased or decreased for just 3 trees)
        // 2 = 2 ways to fix by removing one tree, (heights maintained for just 2 trees)
        // 0 = 0 ways to fix by removing one tree, (garden is already pleasing)
        // -1 = garden cannot be fixed
        // assume that initially, garden does not need fixing
        int numWaysToFix = 0;

        // -1 value here means last height starts unknown
        int lastHeight = -1;

        // delta polarity defines delta heights:
        // -1 = height decreasing
        // 0 = height not changing
        // 1 = height increasing
        // -2 value here means that last delta polarity starts unknown
        int lastDeltaPolarity = -2;

        // loop through all the tree heights

        for (int i = 0; i < N; i++) {
            int height = A[i];

            if (lastHeight == -1) {
                // first tree, can remember its height and skip rest
                lastHeight = height;
                continue;
            }
            int deltaPolarity = (height > lastHeight) ? 1 : ((height < lastHeight) ? -1 : 0);
            lastHeight = height;

            if (deltaPolarity == 0) {
                // not pleasing case, heights are the same
                // if already not pleasing, garden cannot be fixed
                if (isPleasing == false) return -1;
                isPleasing = false;
                numWaysToFix = 2;
            }
            if (lastDeltaPolarity == -2) {
                // second tree, can remember delta polarity and skip rest
                lastDeltaPolarity = deltaPolarity;
                continue;
            }
            if (lastDeltaPolarity == deltaPolarity) {
                // not pleasing case, heights have been increasing or decreasing more than once
                // if already not pleasing, garden cannot be fixed
                if (isPleasing == false) return -1;
                isPleasing = false;
                numWaysToFix = 3;
            }

            // reset state for next tree
            lastDeltaPolarity = deltaPolarity;
        }

        // return results
        return numWaysToFix;
    }
}
