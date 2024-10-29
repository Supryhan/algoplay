package collections;

import java.util.Stack;

public class CollectionStackProbation {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        System.out.println("Elements in stack (adding): " + stack);

        System.out.println("Stack iteration:");
        for (Integer num : stack) {
            System.out.print(num + " "); // prints from 1 to 4
        }

        System.out.println("\nRemoving elements from stack:");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " "); // but pop() works as expected from 4 to 1
        }
    }
}
