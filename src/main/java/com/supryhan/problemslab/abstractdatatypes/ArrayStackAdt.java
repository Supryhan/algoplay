package com.supryhan.problemslab.abstractdatatypes;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public final class ArrayStackAdt<T> implements StackAdt<T> {

    private final Deque<T> elements = new ArrayDeque<>();

    @Override
    public void push(T value) {
        elements.push(value);
    }

    @Override
    public T pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        return elements.pop();
    }

    @Override
    public T peek() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        return elements.peek();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
