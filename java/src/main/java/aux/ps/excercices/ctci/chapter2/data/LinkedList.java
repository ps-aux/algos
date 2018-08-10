package aux.ps.excercices.ctci.chapter2.data;

import org.junit.jupiter.api.Test;

import static aux.ps.excercices.ctci.chapter2.data.LinkedList.empty;
import static aux.ps.excercices.ctci.chapter2.data.LinkedList.of;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class LinkedList<T> {

    // API for algorithms exercises
    public T value;
    public LinkedList<T> next;
    private int size;


    private LinkedList(T t) {
        value = t;
        size = 1;
    }

    private boolean isLast() {
        return next == null;
    }

    private LinkedList<T> findLast() {
        var l = this;
        while (!l.isLast())
            l = l.next;

        return l;
    }

    void add(T t) {
        if (size == 0) {
            value = t;
            size = 1;
            return;
        }
        findLast().next = of(t);
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public T get(int index) {
        if (index == 0)
            return value;

        if (isLast())
            throw new IndexOutOfBoundsException();

        return next.get(index - 1);

    }

    public void remove(int index) {
        if (index == 0) {
            if (next == null) {
                size = 0;
                return;
            }

            value = next.value;
            next = next.next;
            size--;
            return;
        }

        next.remove(index - 1);
        size--;
    }

    public static <T> LinkedList<T> of(T... t) {

        LinkedList<T> l = empty();

        for (int i = 0; i < t.length; i++) {
            l.add(t[i]);
        }

        return l;
    }

    public static <T> LinkedList<T> empty() {
        var l = new LinkedList<T>(null);
        l.size = 0;
        return l;
    }

    @Override
    public String toString() {
        return isEmpty() ? "(empty list)" : "(" + value + ", " + next + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedList<?> that = (LinkedList<?>) o;

        if (size != that.size)
            return false;

        var thisNext = this;
        var thatNext = that;
        for (int i = 1; i < size; i++) {
            if (thisNext.value != thatNext.value)
                return false;

            thisNext = thisNext.next;
            thatNext = thatNext.next;
        }

        return true;
    }

    // TODO misin hashcode
}

class LinkedListSpec {

    LinkedList sut() {
        return LinkedList.of(1, 2, 3);
    }

    @Test
    void get() {
        var sut = sut();
        assertThat(sut.get(0)).isEqualTo(1);
        assertThat(sut.get(2)).isEqualTo(3);
    }

    @Test
    void size() {
        var sut = sut();
        assertThat(sut.size()).isEqualTo(3);
    }

    @Test
    void removeFromBeginning() {
        var sut = sut();
        sut.remove(0);
        assertThat(sut.size()).isEqualTo(2);
        assertThat(sut.get(0)).isEqualTo(2);
    }

    @Test
    void remove() {
        var sut = sut();
        sut.remove(1);
        assertThat(sut.size()).isEqualTo(2);
        assertThat(sut.get(1)).isEqualTo(3);
    }

    @Test
    void equals() {
        var sut = sut();

        assertThat(sut).isEqualTo(of(1, 2, 3));
        assertThat(empty()).isEqualTo(empty());
        assertThat(sut).isNotEqualTo(of(1, 2));
        assertThat(sut).isNotEqualTo(of(1, 2, 3, 4));
    }

}


