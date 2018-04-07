package aux.ps.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {

    private T val;

    private LinkedList<T> next;

    private LinkedList(T t) {
        this.val = t;
    }

    public void add(T t) {
        if (t == null)
            throw new NullPointerException();
        if (isEmpty()) {
            val = t;
        } else if (next == null) {
            this.next = new LinkedList<>(t);
        } else {
            next.add(t);
        }
    }

    @Override
    public void insert(int index, T t) {
        checkOutOfBounds(index);
        if (index == 0) {
            LinkedList<T> newNode = new LinkedList<>(val);
            val = t;
            newNode.next = next;
            next = newNode;

        } else {
            LinkedList<T> newNode = new LinkedList<>(t);
            LinkedList<T> before = getNode(index - 1);
            LinkedList<T> node = before.next;

            before.next = newNode;
            newNode.next = node;

        }


    }

    public T get(int index) {
        checkOutOfBounds(index);
        LinkedList<T> n = getNode(index);

        return n.val;
    }

    public void remove(int index) {
        checkOutOfBounds(index);
        if (index == 0) {
            if (next == null)
                val = null;
            else {
                // Remove the next node
                val = next.val;
                next = next.next;
            }
        } else {
            LinkedList<T> before = getNode(index - 1);
            LinkedList<T> n = before.next;

            before.next = n.next;
        }
    }

    private LinkedList<T> getNode(int index) {
        if (index == 0)
            return this;
        if (next == null)
            throw new IndexOutOfBoundsException();

        return next.getNode(index - 1);
    }

    public int size() {
        if (val == null)
            return 0;
        if (next == null)
            return 1;
        return 1 + next.size();
    }

    public Iterator<T> iterator() {

        return new Iterator<T>() {

            LinkedList<T> next = LinkedList.this;

            @Override
            public boolean hasNext() {
                return next != null && next.val != null;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                LinkedList<T> res = next;
                next = res.next;
                return res.val;
            }
        };
    }

    @Override
    public String toString() {
        // O (n) toString() :)
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedList{");
        Iterator<T> it = iterator();
        while (it.hasNext())
            sb.append(it.next()).append(" ");

        sb.append("}");
        return sb.toString();
    }

    public static <T> LinkedList<T> of(T... ts) {
        LinkedList<T> t = new LinkedList<>(null);
        for (int i = 0; i < ts.length; i++) {
            t.add(ts[i]);
        }

        return t;
    }
}
