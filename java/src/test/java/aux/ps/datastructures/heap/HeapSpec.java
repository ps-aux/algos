package aux.ps.datastructures.heap;

import org.junit.jupiter.api.Test;

import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface HeapSpec {

    Heap<Integer, String> create();

    @Test
    default void insert() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");

        assertEquals("b", h.max());
        assertEquals(2, h.size());
    }

    @Test
    default void max() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");
        h.insert(100, "c");

        assertEquals("c", h.max());
    }

    @Test
    default void maxKey() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");

        assertEquals(valueOf(2), h.maxKey());
    }

    @Test
    default void delMax() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");
        h.insert(100, "c");

        h.delMax();
        assertEquals("b", h.max());
        assertEquals(2, h.size());
    }

    @Test
    default void size() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");

        assertEquals(2, h.size());
    }

    @Test
    default void changeMax() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");
        h.change(2, "c");

        assertEquals("c", h.max());
    }

    @Test
    default void changeNonMax() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");
        h.change(1, "c");

        assertEquals(2, h.size());
        h.delMax();
        assertEquals("c", h.max());
    }

    @Test
    default void find() {
        Heap<Integer, String> h = create();

        h.insert(1, "a");
        h.insert(2, "b");
        h.insert(3, "c");

        assertEquals("c", h.find(3));

    }

    @Test
    default void merge() {
        Heap<Integer, String> h = create();
        h.insert(1, "a1");
        h.insert(2, "b1");

        Heap<Integer, String> h2 = create();

        h2.insert(1, "a2");
        h2.insert(2, "b2");
        h2.insert(3, "c2");
        h2.insert(4, "d2");


        h.merge(h2);


        assertEquals(6, h.size());
        assertEquals("d2", h.max());
        h.delMax();
        assertEquals("c2", h.max());
        h.delMax();

        // Del bs
        h.delMax();
        h.delMax();
        assertTrue(h.max().startsWith("a"));

    }

}
