package aux.ps.datastructures.queue;

import aux.ps.datastructures.list.LinkedList;

public class LinkedListQueue<T> implements Queue<T> {

    private LinkedList<T> list = LinkedList.of();


    @Override
    public void enqueque(T t) {
        list.add(t);
    }

    @Override
    public T dequeue() {
        T t = list.get(0);
        list.remove(0);
        return t;
    }

    @Override
    public T peek() {
        return list.get(0);
    }

    @Override
    public String toString() {
        return "LinkedListQueue{" + list + '}';
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
