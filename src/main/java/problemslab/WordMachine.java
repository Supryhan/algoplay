package problemslab;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class WordMachine {
    private static final int MIN = 0;
    private static final int MAX = 0x7FFF_FFFF;
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");
    private final Stack<Integer> stack = new Stack<>();
    private static final Map<String, Supplier<Integer>> FUNCTIONS = new HashMap<>();
    {
        FUNCTIONS.put("POP", this::pop);
        FUNCTIONS.put("DUP", this::dup);
        FUNCTIONS.put("+", this::add);
        FUNCTIONS.put("-", this::sub);
    }

    public static void main(String[] args) {
        WordMachine wm = new WordMachine();
        System.out.println(wm.process("1 1 + 2 + DUP +"));
    }

    private int process(String s) {
        try {
            Arrays.stream(s.split(" ")).forEach(this::apply);
            return pop();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    private void apply(String token) {
        if (NUMERIC_PATTERN.matcher(token).matches()) {
            push(Integer.valueOf(token));
        } else {
            Supplier<Integer> function = FUNCTIONS.get(token);
            if (function != null) {
                function.get();
            } else {
                throw new IllegalArgumentException("Unknown operation: " + token);
            }
        }
    }

    private int push(int i) {
        withinRange(i);
        return stack.push(i);
    }

    private int pop() {
        return stack.pop();
    }

    private int dup() {
        hasElements(1);
        return push(stack.peek());
    }

    private int add() {
        hasElements(2);
        return push(stack.pop() + stack.pop());
    }

    private int sub() {
        hasElements(2);
        return push(stack.pop() - stack.pop());
    }

    private int hasElements(int i) {
        if(stack.size() < i){
            throw new IllegalArgumentException("Too few elements are available");
        }
        return i;
    }

    private int withinRange(int i){
        if(i < MIN || i > MAX){
            throw new IllegalArgumentException("Number is out of integer range");
        }
        return i;
    }
}
