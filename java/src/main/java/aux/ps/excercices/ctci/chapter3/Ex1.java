package aux.ps.excercices.ctci.chapter3;

import aux.ps.excercices.ctci.chapter3.data.Stack;
import aux.ps.excercices.ctci.chapter3.data.StackSpec;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;


// Just reusing existing testing code in a funny way
interface StackTester extends StackSpec {

    default void test() {
        isEmpty();
        do2pushAnd2Pop();
        changePushAndPop();
        peek();
    }

}

class ThreeInOneSpec {

    Stream<StackTester> testers(int size, int count) {
        Stack[] stacks = ThreeInOneStepped.create(size, count);

        return Arrays.stream(stacks)
                .map(s -> () -> s);
    }

    @Test
    void genericStackTests() {
        testers(12, 3)
                .forEach(StackTester::test);
    }

    @Test
    void test() {

        int size = 12;
        int count = 3;
        Stack[] stacks = ThreeInOneStepped.create(size, count);

        stacks[0].push(0);
        stacks[0].push(1);

        stacks[1].push(10);
        stacks[1].push(11);

        stacks[2].push(100);
        stacks[2].push(101);

        assertThat(stacks[0].pop()).isEqualTo(1);
        assertThat(stacks[0].size()).isEqualTo(1);

        assertThat(stacks[1].pop()).isEqualTo(11);
        assertThat(stacks[1].size()).isEqualTo(1);

        assertThat(stacks[2].pop()).isEqualTo(101);
        assertThat(stacks[2].size()).isEqualTo(1);


        assertThat(stacks[2].pop()).isEqualTo(100);
        assertThat(stacks[2].isEmpty()).isTrue();

        assertThat(stacks[1].pop()).isEqualTo(10);
        assertThat(stacks[1].isEmpty()).isTrue();

        assertThat(stacks[0].pop()).isEqualTo(0);
        assertThat(stacks[0].isEmpty()).isTrue();
    }

}


// TODO dynamic stacks
// TODO allocating portion of stack (e.g. for 3 stacks 1/2, 2/3 and 3/3)
class ThreeInOneStepped {

    static Stack[] create(int bufferSize, int count) {
        int array[] = new int[bufferSize];
        Stack[] stacks = new Stack[count];

        if (bufferSize % count != 0)
            throw new IllegalArgumentException("size not dividable by count");

        for (int i = 0; i < count; i++) {
            stacks[i] = new ArrayStack(array,
                    i, bufferSize - 1 - count + i,
                    count);
        }

        return stacks;
    }

    private static class ArrayStack implements Stack {

        private final int[] array;
        private final int start;
        private final int end;
        private final int step;
        private int current;
        private int size;


        private ArrayStack(int[] array, int start, int end, int step) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.step = step;
            current = start - step;
        }

        private int nextIndex() {
            int next = current + step;
            if (next > end)
                throw new StackOverflowError();
            return next;
        }

        @Override
        public void push(int i) {
            int next = nextIndex();
            System.out.println("push " + i + " to " + next);
            array[next] = i;
            current = next;
            size++;
        }

        @Override
        public int pop() {
            if (isEmpty())
                throw new IllegalStateException("Stack is empty");
            int el = array[current];
            System.out.println("pop " + el + " from " + current);
            current = current - step;
            size--;
            return el;
        }

        @Override
        public int peek() {
            return array[current];
        }

        @Override
        public boolean isEmpty() {
            return current < start;
        }

        @Override
        public int size() {
            return size;
        }
    }

}


