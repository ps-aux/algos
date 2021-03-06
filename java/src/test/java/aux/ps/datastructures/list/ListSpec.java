package aux.ps.datastructures.list;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ListSpec {


    <T> List<T> create(T... ts);

    @Test
    default void getElementAtExistingIndex() {
        List<Integer> l = create(1, 2, 3);

        assertEquals(valueOf(1), l.get(0));
        assertEquals(valueOf(2), l.get(1));
        assertEquals(valueOf(3), l.get(2));
    }

    @Test
    default void exceptionThrownForOutOfBounds() {
        List<Integer> l = create(1);
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(2));
    }

    @Test
    default void exceptionThrownForOutOfBoundsInEmpty() {
        List<Integer> l = create();
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(1));
    }

    @Test
    default void addToEmpty() {
        List<Integer> l = create();
        l.add(1);
        assertEquals(valueOf(1), l.get(0));
        assertEquals(1, l.size());
    }

    @Test
    default void insertAt0th() {
        List<Integer> l = create(1, 2);
        l.insert(0, 100);

        assertEquals(3, l.size());
        assertEquals(valueOf(100), l.get(0));
        assertEquals(valueOf(1), l.get(1));
        assertEquals(valueOf(2), l.get(2));
    }

    @Test
    default void insertAtLast() {
        List<Integer> l = create(1, 2);
        l.insert(1, 100);

        assertEquals(3, l.size());
        assertEquals(valueOf(1), l.get(0));
        assertEquals(valueOf(100), l.get(1));
        assertEquals(valueOf(2), l.get(2));
    }

    @Test
    default void insert() {
        List<Integer> l = create(1, 2, 3, 4);
        l.insert(2, 100);
        assertEquals(5, l.size());
        assertEquals(valueOf(1), l.get(0));
        assertEquals(valueOf(2), l.get(1));
        assertEquals(valueOf(100), l.get(2));
        assertEquals(valueOf(3), l.get(3));
        assertEquals(valueOf(4), l.get(4));
    }


    @Test
    default void length() {
        List<Integer> l = create(1, 2, 3);
        assertEquals(3, l.size());
    }

    @Test
    default void manyCanBeAdded() {
        int many = 1000;
        List<Integer> l = create();
        IntStream.range(0, many)
                .forEach(l::add);

        assertEquals(many, l.size());
        assertEquals(valueOf(0), l.get(0));
        assertEquals(valueOf(many - 1), l.get(many - 1));
    }

    @Test
    default void singletonLength() {
        List<Integer> singleton = create(1);
        assertEquals(1, singleton.size());
    }

    @Test
    default void emptyLength() {
        List<Integer> empty = create();
        assertEquals(0, empty.size());
    }

    @Test
    default void remove() {
        List<Integer> l = create(1, 2, 3);
        l.remove(1);
        assertEquals(valueOf(1), l.get(0));
        assertEquals(valueOf(3), l.get(1));
        assertEquals(2, l.size());
    }

    @Test
    default void remove0th() {
        List<Integer> l = create(1, 2, 3);
        l.remove(0);

        assertEquals(valueOf(2), l.get(0));
        assertEquals(valueOf(3), l.get(1));
    }

    @Test
    default void iterator() {
        Iterator<Integer> l = create(1, 2, 3).iterator();

        assertEquals(valueOf(1), l.next());
        assertEquals(true, l.hasNext());
        assertEquals(valueOf(2), l.next());
        assertEquals(true, l.hasNext());
        assertEquals(valueOf(3), l.next());
        assertEquals(false, l.hasNext());
        assertThrows(NoSuchElementException.class, l::next);
    }


}
