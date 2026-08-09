package com.supryhan.problemslab.abstractdatatypes;

interface StackAdt<T> {

    void push(T value);

    T pop();

    T peek();

    boolean isEmpty();
}
