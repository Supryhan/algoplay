package com.supryhan.problemslab.abstractdatatypes;

public class MainAdt {
    public static void main(String[] args) {
        StackAdt<Integer> stack = new ArrayStackAdt<>();

        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
    }
}
