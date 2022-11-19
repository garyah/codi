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
        // testCases.add(new TestCase<int[], Integer>(new int[]{3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7,3,4,5,3,7}, 3));

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
        // initialize state
        int N = A.length;

        // check if trees are already aesthetically pleasing
        boolean isPleasing = isPleasing(A, -1);

        // if so, return true
        if (isPleasing) return 0;

        // iterate on all possible one tree removals, count how many are pleasing
        int numPleasing = 0;
        for (int i = 0; i < N; i++) {
            if (isPleasing(A, i)) numPleasing++;
        }

        // return results
        return (numPleasing > 0) ? numPleasing : -1;
    }

    public boolean isPleasing(int[] A, int indexToSkip) {
        // initialize state
        int N = A.length;

        // assume that initially, trees are aesthetically pleasing
        boolean isPleasing = true;

        // -1 value here means the last height starts unknown
        int lastHeight = -1;

        // delta polarity defines delta heights:
        // -1 = height decreasing
        // 0 = height not changing
        // 1 = height increasing
        // -2 value here means that the last delta polarity starts unknown
        int lastDeltaPolarity = -2;

        // loop through all the tree heights

        for (int i = 0; i < N; i++) {
            int height = A[i];
            if (indexToSkip > -1 && i == indexToSkip) {
                // must skip the tree that is removed, if any
                continue;
            }

            if (lastHeight == -1) {
                // first tree, can remember its height and skip rest
                lastHeight = height;
                continue;
            }
            int deltaPolarity = (height > lastHeight) ? 1 : ((height < lastHeight) ? -1 : 0);
            lastHeight = height;

            if (deltaPolarity == 0) {
                // not pleasing case, heights are the same
                isPleasing = false;
            }
            if (lastDeltaPolarity == -2) {
                // second tree, can remember delta polarity and skip rest
                lastDeltaPolarity = deltaPolarity;
                continue;
            }
            if (lastDeltaPolarity == deltaPolarity) {
                // not pleasing case, heights have been increasing or decreasing more than once
                isPleasing = false;
            }

            // reset state for next tree
            lastDeltaPolarity = deltaPolarity;
        }

        // return results
        return isPleasing;
    }
}
