package problems;


import java.util.*;

interface Distance {
    int findClosestDistance(Integer[] numbers);
}

class NiceInterviewImpl implements Distance {

    public static void main(String[] args) {
        Distance problem = new NiceInterviewImpl();
        System.out.println(problem.findClosestDistance(new Integer[]{3, 16, 25, 13, 9, 54}));
        System.out.println(problem.findClosestDistance(new Integer[]{9, 35, 27, 4, 45, 39, 15}));
        System.out.println(problem.findClosestDistance(new Integer[]{3, 6, 19, 12, 4, 27, 6, 21}));
        System.out.println(problem.findClosestDistance(new Integer[]{}));
    }

    @Override
    public int findClosestDistance(Integer[] numbers) {
        if (numbers.length < 1) return -1;
        Arrays.sort(numbers);
        Integer[] newArray = new Integer[numbers.length - 1];

        int start = numbers[0];

        for(int i = 1; i < numbers.length; i++) {
            int end = numbers[i];
            int diff = end - start;
            newArray[i - 1] = diff;
            start = end;
        }
        Arrays.sort(newArray);
        return newArray[0];
    }
}
