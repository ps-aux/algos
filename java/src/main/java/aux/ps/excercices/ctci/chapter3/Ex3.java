package aux.ps.excercices.ctci.chapter3;

import aux.ps.excercices.ctci.chapter3.data.Stack;
import aux.ps.excercices.ctci.chapter3.data.StackSpec;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;


class SetOfStacksTest implements StackSpec {

    public SetOfStacks sut(int size) {
        return new SetOfStacks(size);
    }

    @Test
    void push() {
        var sut = sut(2);

        sut.push(1);
        sut.push(2);
        sut.push(3);
        sut.push(4);
        sut.push(5);


        assertThat(sut.pop()).isEqualTo(5);
        assertThat(sut.pop()).isEqualTo(4);
        assertThat(sut.pop()).isEqualTo(3);
        assertThat(sut.pop()).isEqualTo(2);
        assertThat(sut.pop()).isEqualTo(1);
    }

    @Test
    void popAt() {
        var sut = sut(2);

        sut.push(1);
        sut.push(2);
        sut.push(3);
        sut.push(4);
        sut.push(5);


        assertThat(sut.popAt(0)).isEqualTo(2);
        assertThat(sut.popAt(0)).isEqualTo(1);

        assertThat(sut.popAt(1)).isEqualTo(5);
        assertThat(sut.popAt(0)).isEqualTo(4);

        assertThat(sut.size()).isEqualTo(1);

        assertThat(sut.pop()).isEqualTo(3);

    }

    @Override
    public Stack create() {
        return new SetOfStacks(1);
    }
}


class SetOfStacks implements Stack {

    Deque<Deque<Integer>> stacks = new LinkedList<>();

    private final int stackSize;
    int size;


    public SetOfStacks(int size) {
        this.stackSize = size;
    }

    @Override
    public void push(int i) {
        if (stacks.isEmpty())
            stacks.push(new LinkedList<>());

        if (stacks.peek().size() == stackSize) {
            stacks.push(new LinkedList());
        }

        size++;
        stacks.peek().push(i);

    }

    @Override
    public int pop() {
        var el = stacks.peek().pop();

        if (stacks.peek().isEmpty()) {
            stacks.pop();
        }
        size--;
        return el;
    }

    public int popAt(int index) {
        int current = 0;
        var it = stacks.descendingIterator();
        while (current < index) {
            it.next();
            current++;
        }
        size--;

        var s = it.next();
        var el = s.pop();
        if (s.isEmpty()) {
            it.remove();
        }
        return el;
    }

    @Override
    public int peek() {
        return stacks.peek().peek();
    }

    @Override
    public boolean isEmpty() {
        return stacks.isEmpty();
    }

    @Override
    public int size() {
        return size;
    }
}

class SetOfStacksWithRolloverTest extends SetOfStacksTest {

    @Override
    public SetOfStacks sut(int size) {
        return new SetOfStacksWithRollover(size);
    }

    @Test
    void popAt() {
        var sut = sut(2);

        sut.push(1);
        sut.push(2);
        sut.push(3);
        sut.push(4);
        sut.push(5);


        assertThat(sut.popAt(0)).isEqualTo(2);
        assertThat(sut.popAt(0)).isEqualTo(3);

        assertThat(sut.popAt(1)).isEqualTo(5);
        assertThat(sut.popAt(0)).isEqualTo(4);

        assertThat(sut.size()).isEqualTo(1);

        assertThat(sut.pop()).isEqualTo(1);

    }
}

/**
 * when used popAt from the middle then elements are moved by one
 * so all stack except the last one have full length
 */
class SetOfStacksWithRollover extends SetOfStacks {

    public SetOfStacksWithRollover(int size) {
        super(size);
    }


    @Override
    public int popAt(int index) {
        if (index == stacks.size() - 1)
            return pop();
        System.out.println("stacks before " + stacks);

        var it = stacks.descendingIterator();
        int i = 0;
        while (i < index) {
            it.next();
            i++;
        }

        var s = it.next();
        var res = s.pop();

        while (it.hasNext()) {
            var next = it.next();

            System.out.println("next " + next);
            var prev = removeFromBottom(next);
            s.push(prev);
            s = next;
        }

        // Remove the last empty stack if is empty after this
        if (stacks.peek().isEmpty())
            stacks.pop();

        size--;
        System.out.println("stacks " + stacks);
        return res;
    }

    static int removeFromBottom(Deque<Integer> d) {
        System.out.println("--------");
        System.out.println(d);
        Deque<Integer> tmp = new LinkedList<>();

        while (!d.isEmpty()) {
            tmp.push(d.pop());

        }
        int bottom = tmp.pop();

        while (!tmp.isEmpty()) {
            d.push(tmp.pop());
        }

        System.out.println(d);
        return bottom;
    }

}
