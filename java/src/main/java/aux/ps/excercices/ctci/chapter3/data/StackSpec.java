package aux.ps.excercices.ctci.chapter3.data;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

// TODO copy pasted from the test package
public interface StackSpec {

    Stack create();

    @Test
    default void isEmpty() {
        Stack s = create();
        assertTrue(s.isEmpty());
        s.push(1);
        assertFalse(s.isEmpty());
    }

    @Test
    default void do2pushAnd2Pop() {
        Stack s = create();
        s.push(1);
        s.push(2);

        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
    }

    @Test
    default void changePushAndPop() {
        int times = 5;
        Stack s = create();

        IntStream.range(0, times)
                .forEach(i -> {
                    s.push(i);
                    assertEquals(i, s.pop());
                });
    }

    @Test
    default void peek() {
        Stack s = create();
        s.push(1);
        s.push(2);
        s.push(3);

        assertEquals(3, s.peek());
        assertEquals(3, s.peek());
        s.pop();
        assertEquals(2, s.peek());
    }


}
