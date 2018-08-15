package aux.ps.excercices.ctci.chapter3;

import aux.ps.excercices.ctci.chapter3.data.Stack;
import aux.ps.excercices.ctci.chapter3.data.StackSpec;
import aux.ps.excercices.ctci.chapter3.data.WrappingStack;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


interface MinStackSpec extends StackSpec {

    @Override
    MinStack create();

    @Test
    default void testMin() {
        MinStack sut = create();

        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        sut.push(0);

        assertThat(sut.min()).isEqualTo(0);
        sut.pop();
        assertThat(sut.min()).isEqualTo(1);
        sut.pop();
        sut.pop();
        assertThat(sut.min()).isEqualTo(2);

    }

}


interface MinStack extends Stack {

    int min();

}

class LinkedListMinStackTest implements MinStackSpec {

    @Override
    public MinStack create() {
        return new LinkedListMinStack();
    }
}


class LinkedListMinStack extends WrappingStack implements MinStack {

    private MinNode min;

    @Override
    public void push(int i) {
        if (size() == 0) {
            min = new MinNode(i);
        } else {
            if (i <= min.val) {
                var prev = min;
                min = new MinNode(i);
                min.next = prev;
            }

        }
        super.push(i);
    }

    @Override
    public int pop() {
        var i = super.pop();
        if (i == min.val) {
            min = min.next;
        }
        return i;
    }

    public int min() {
        return min.val;
    }

    private static class MinNode {


        MinNode(int val) {
            this.val = val;
        }

        int val;
        MinNode next;

    }
}


class StackMinStackTest implements MinStackSpec {

    @Override
    public MinStack create() {
        return new StackMinStack();
    }
}

class StackMinStack extends WrappingStack implements MinStack {

    private Stack mins = new WrappingStack();

    @Override
    public void push(int i) {
        if (size() == 0 || i <= mins.peek())
            mins.push(i);

        super.push(i);
    }

    @Override
    public int pop() {
        var i = super.pop();
        if (i == mins.peek())
            mins.pop();
        return i;
    }

    public int min() {
        return mins.peek();
    }

}

