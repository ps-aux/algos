package aux.ps.excercices.ctci.chapter3;


import aux.ps.excercices.ctci.chapter3.data.Stack;
import aux.ps.excercices.ctci.chapter3.data.WrappingStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter3.data.Utils.restack;
import static org.assertj.core.api.Java6Assertions.assertThat;

class SortStackSpec {

    public Stack sut() {
        return new SortStack();
    }

    @Test
    void test() {
        var s = sut();

        Stream.of(4, 1, 5, 2, 3)
                .forEach(s::push);

        assertThat(s.peek()).isEqualTo(1);
        s.pop();
        s.pop();
        assertThat(s.pop()).isEqualTo(3);
        assertThat(s.size()).isEqualTo(2);


    }
}


class SortStack implements Stack {

    private Deque<Integer> stack = new LinkedList<>();

    @Override
    public void push(int e) {
        System.out.println("push " + e);
        if (stack.isEmpty() || stack.peek() > e) {
            stack.push(e);
            return;
        }

        Deque<Integer> tmp = new LinkedList<>();

        System.out.println(stack);
        tmp.push(stack.pop());
        while (!stack.isEmpty() && stack.peek() < e) {
            tmp.push(stack.pop());
        }

        stack.push(e);
        restack(tmp, stack);
        System.out.println("after " + stack);
    }


    @Override
    public int pop() {
        return stack.pop();
    }

    @Override
    public int peek() {
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }
}


interface StackSorterkSpec {

    StackSorter sut();

    @Test
    default void test() {
        Stack s = new WrappingStack();

        Stream.of(5, 1, 2, 4, 3)
                .forEach(s::push);

        sut().sort(s);
        List<Integer> l = new ArrayList<>();

        while (!s.isEmpty())
            l.add(s.pop());

        assertThat(l).isSorted();

    }


}

interface StackSorter {

    void sort(Stack stack);

}

class DoubleStackStackSorterTest implements StackSorterkSpec {

    @Override
    public StackSorter sut() {
        return new SingleStackStackSorter();
    }
}

class SingleStackStackSorterTest implements StackSorterkSpec {

    @Override
    public StackSorter sut() {
        return new DoubleStackStackSorter();
    }
}

class DoubleStackStackSorter implements StackSorter {

    public void sort(Stack stack) {
        int size = stack.size();
        Stack tmp = new WrappingStack();
        Stack tmp2 = new WrappingStack();

        restack(stack, tmp);

        Stack from = tmp;
        Stack to = tmp2;
        while (stack.size() != size) {
            int min = from.peek();

            while (!from.isEmpty()) {
                var el = from.pop();
                if (el < min)
                    min = el;
                to.push(el);
            }

            if (from == tmp) {
                from = tmp2;
                to = tmp;
            } else {
                from = tmp;
                to = tmp2;
            }

            stack.push(min);
        }


    }

}

class SingleStackStackSorter implements StackSorter {

    @Override
    public void sort(Stack stack) {
        int size = stack.size();
        Stack tmp = new WrappingStack();

        restack(stack, tmp);

        while (stack.size() < size) {
            int next = tmp.pop();
            if (stack.isEmpty()) {
                stack.push(next);
                return;
            }
            while (stack.peek() > next) {
                tmp.push(stack.pop());
            }
            stack.push(next);
        }
    }

}

