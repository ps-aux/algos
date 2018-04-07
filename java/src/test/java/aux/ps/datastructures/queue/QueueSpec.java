package aux.ps.datastructures.queue;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface QueueSpec {

    <T> Queue<T> create();

    @Test
    default void isEmpty() {
        Queue<Integer> s = create();
        assertEquals(true, s.isEmpty());
        s.enqueque(1);
        assertEquals(false, s.isEmpty());
    }

    @Test
    default void do2EnqueueAnd2Dequeque() {
        Queue<String> s = create();
        s.enqueque("a");
        s.enqueque("b");

        assertEquals("a", s.dequeue());
        assertEquals("b", s.dequeue());
    }

    @Test
    default void changeEnqueueAndDequeue() {
        int times = 10;
        Queue<String> s = create();

        IntStream.range(0, times)
                .forEach(i -> {
                    String str = String.valueOf(i);
                    s.enqueque(str);
                    assertEquals(str, s.dequeue());
                });
    }

    @Test
    default void peek() {
        Queue<String> s = create();
        s.enqueque("a");
        s.enqueque("b");
        s.enqueque("c");

        assertEquals("a", s.peek());
        assertEquals("a", s.peek());
        s.dequeue();
        assertEquals("b", s.peek());
    }


}
