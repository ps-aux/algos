package aux.ps.datastructures.list;

import java.util.Iterator;

public interface List<T> {

    void add(T t);

    void insert(int index, T t);

    void remove(int index);

    T get(int index);

    int size();

    Iterator<T> iterator();

    default void checkOutOfBounds(int index) {
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " is out of bounds");
    }

    default boolean isEmpty() {
        return size() == 0;
    }
}
