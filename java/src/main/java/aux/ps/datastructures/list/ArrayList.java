package aux.ps.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {

    private int initialSize = 4;
    private T[] arr = (T[]) new Object[initialSize];

    private int last = -1;

    private void growIfNeeded() {
        if (size() == arr.length) {
            T[] newArr = (T[]) new Object[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                newArr[i] = arr[i];
            }
            arr = newArr;
        }
    }

    @Override
    public void add(T t) {
        growIfNeeded();
        arr[++last] = t;
    }

    @Override
    public void insert(int index, T t) {
        growIfNeeded();
        for (int i = size() - 1; i >= index; i--) {
            arr[i + 1] = arr[i];
        }
        arr[index] = t;
        last++;
    }

    @Override
    public void remove(int index) {
        checkOutOfBounds(index);
        arr[index] = null;
        for (int i = index + 1; i <= last; i++) {
            arr[i - 1] = arr[i];
        }
        last--;
    }

    @Override
    public T get(int index) {
        checkOutOfBounds(index);
        return arr[index];
    }

    @Override
    public int size() {
        return last + 1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int itLast = -1;

            @Override
            public boolean hasNext() {
                return itLast < last;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return arr[++itLast];
            }
        };
    }

    public static <T> List<T> of(T... ts) {
        List<T> l = new ArrayList<>();

        for (T t : ts) {
            l.add(t);
        }

        return l;
    }
}
