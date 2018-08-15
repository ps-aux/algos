package aux.ps.excercices.ctci.chapter3.data;

import java.util.Deque;
import java.util.LinkedList;

public class WrappingStack implements Stack {

    private final Deque<Integer> deque = new LinkedList<>();

    @Override
    public void push(int i) {
        deque.push(i);
    }

    @Override
    public int pop() {
        return deque.pop();
    }

    @Override
    public int peek() {
        return deque.peek();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public int size() {
        return deque.size();
    }
}


class WrappingStackTest implements StackSpec {

    @Override
    public Stack create() {
        return new WrappingStack();
    }
}
