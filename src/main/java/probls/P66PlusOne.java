package probls;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class P66PlusOne {
    public static void main(String[] args) {
        P66PlusOne problem = new P66PlusOne();
        System.out.println("1: " + Arrays.toString(problem.plusOne(new int[]{1, 2, 3, 4, 5})));
        System.out.println("2: " + Arrays.toString(problem.plusOne(new int[]{9})));
    }

    public int[] plusOne(int[] digits) {
        int length = digits.length;
        boolean flag = true;

        for(int i = length - 1; i >= 0; i--) {
            if(digits[i] < 9) {
                digits[i]++;
                flag = false;
                break;
            }
            digits[i] = 0;
        }
        if(flag) {
            int[] c = new int[length + 1];
            arraycopy(digits, 0, c, 1, length);
            c[0] = 1;
            return c;
        }
        return digits;
    }
}
