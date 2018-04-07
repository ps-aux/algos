package aux.ps.datastructures.queue;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface PriorityQueueSpec {

    PriorityQueue<Integer, String> create();

    @Test
    default void isEmpty() {
        PriorityQueue<Integer, String> s = create();
        assertEquals(true, s.isEmpty());
        s.enqueque(1, "any");
        assertEquals(false, s.isEmpty());
    }

    @Test
    default void enqueueAndDequeue() {
        PriorityQueue<Integer, String> s = create();
        s.enqueque(10, "a");
        s.enqueque(100, "b");
        s.enqueque(1, "c");

        assertEquals("b", s.dequeue());
        assertEquals("a", s.dequeue());
        assertEquals("c", s.dequeue());
    }

    @Test
    default void peek() {
        PriorityQueue<Integer, String> s = create();
        s.enqueque(1, "a");
        s.enqueque(2, "b");

        assertEquals("b", s.peek());
    }


}
