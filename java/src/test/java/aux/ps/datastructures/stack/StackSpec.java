package aux.ps.datastructures.stack;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface StackSpec {

    <T> Stack<T> create();

    @Test
    default void isEmpty() {
        Stack<Integer> s = create();
        assertEquals(true, s.isEmpty());
        s.push(1);
        assertEquals(false, s.isEmpty());
    }

    @Test
    default void do2pushAnd2Pop() {
        Stack<String> s = create();
        s.push("a");
        s.push("b");

        assertEquals("b", s.pop());
        assertEquals("a", s.pop());
    }

    @Test
    default void changePushAndPop() {
        int times = 5;
        Stack<String> s = create();

        IntStream.range(0, times)
                .forEach(i -> {
                    String str = String.valueOf(i);
                    s.push(str);
                    assertEquals(str, s.pop());
                });
    }

    @Test
    default void peek() {
        Stack<String> s = create();
        s.push("a");
        s.push("b");
        s.push("c");

        assertEquals("c", s.peek());
        assertEquals("c", s.peek());
        s.pop();
        assertEquals("b", s.peek());
    }


}
