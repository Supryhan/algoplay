package problems;

/**
 * A binary gap within a positive integer N is any maximal sequence of consecutive zeros that
 * is surrounded by ones at both ends in the binary representation of N.
 * For example, number 9 has binary representation 1001 and contains a binary gap of length 2.
 * The number 529 has binary representation 1000010001 and contains two binary gaps: one of
 * length 4 and one of length 3.
 * The number 20 has binary representation 10100 and contains one
 * binary gap of length 1. The number 15 has binary representation 1111 and has no binary gaps.
 * The number 32 has binary representation 100000 and has no binary gaps.
 *
 * Write a function:
 * class Solution { public int solution(int N); }
 * that, given a positive integer N, returns the length of its longest binary gap. The function
 * should return 0 if N doesn't contain a binary gap.
 * For example, given N = 1041 the function should return 5, because N has binary representation
 * 10000010001 and so its longest binary gap is of length 5.
 * Given N = 32 the function should return 0, because N has binary representation '100000' and
 * thus no binary gaps.
 * Write an efficient algorithm for the following assumptions:
 * N is an integer within the range [1..2,147,483,647].
 */
public class BinaryGap {

    public static void main(String[] args) {
        BinaryGap b = new BinaryGap();
        System.out.println(b.solution(Integer.MAX_VALUE));
    }

    public int solution(int N) {
        System.out.println(N);
        int result = 0;
        boolean[] massif = new boolean[31];
        for (int i = massif.length - 1; i >= 0 ; i--) {
            int n = N % 2;
            N = N / 2;
            if ( n == 1) massif[i] = true;
            System.out.print(" " + n + ",");
        }
        System.out.println();
        for(int i = 0; i < massif.length; i++) System.out.print(" " + massif[i] + ",");
        System.out.println();
        int j = 0;
        while (!massif[j] && j < 30) {
            massif[j] = true;
            if (massif[j + 1]) {
                break;
            }
            j++;
        }
        j = 30;
        while (!massif[j] && j > 0) {
            massif[j] = true;
            if (massif[j - 1]) {
                break;
            }
            j--;
        }

        int curr = 0;
        boolean seq = false;
        for(int i = 0; i < 31; i++) {
            if(!massif[i]){
                if (seq) curr++;
                else {
                    seq = true;
                    curr = 1;
                }
            } else {
                seq = false;
                if (curr > result) result = curr;
                curr = 0;
            }

        }

        return result;
    }
}
