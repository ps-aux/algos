package aux.ps.datastructures.stack;

import aux.ps.datastructures.list.LinkedList;

public class LinkedListStack<T> implements Stack<T> {

    private LinkedList<T> list;

    public LinkedListStack() {
        this.list = LinkedList.of();
    }

    @Override
    public void push(T t) {
        this.list.add(t);
    }

    private int lastIndex() {
        return list.size() - 1;
    }

    @Override
    public T pop() {
        int last = lastIndex();
        T t = list.get(last);
        list.remove(last);
        return t;
    }

    @Override
    public T peek() {
        int last = lastIndex();
        return list.get(last);
    }


    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }
}
