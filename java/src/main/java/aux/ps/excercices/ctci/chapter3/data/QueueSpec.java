package aux.ps.excercices.ctci.chapter3.data;

import aux.ps.datastructures.queue.Queue;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        Queue<Integer> s = create();
        s.enqueque(1);
        s.enqueque(2);

        assertThat(1).isEqualTo(s.dequeue());
        assertThat(2).isEqualTo(s.dequeue());
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
        Queue<Integer> s = create();
        s.enqueque(1);
        s.enqueque(2);
        s.enqueque(3);

        assertThat(1).isEqualTo(s.peek());
        assertThat(1).isEqualTo(s.peek());
        s.dequeue();
        assertThat(2).isEqualTo(s.peek());
    }


}
