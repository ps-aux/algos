package aux.ps.excercices.ctci.chapter3;


import aux.ps.datastructures.queue.Queue;
import aux.ps.excercices.ctci.chapter3.data.QueueSpec;

import java.util.Deque;
import java.util.LinkedList;

import static aux.ps.excercices.ctci.chapter3.data.Utils.restack;

class StackQueueSpec implements QueueSpec {


    @Override
    public <T> Queue<T> create() {
        return new StackQueue<>();
    }
}

class LazyStackQueueSpec implements QueueSpec {


    @Override
    public <T> Queue<T> create() {
        return new LazyStackQueue<>();
    }
}


/**
 * pop/peek O(1)
 * push O(n)
 */
class StackQueue<T> implements Queue<T> {

    private Deque<T> in = new LinkedList<>();
    private Deque<T> out = new LinkedList<>();

    @Override
    public void enqueque(T t) {
        if (out.isEmpty()) {
            out.push(t);
            return;
        }

        while (!out.isEmpty()) {
            in.push(out.pop());
        }
        in.push(t);

        while (!in.isEmpty()) {
            out.push(in.pop());
        }
    }

    @Override
    public T dequeue() {
        return out.pop();
    }

    @Override
    public T peek() {
        return out.peek();
    }

    @Override
    public boolean isEmpty() {
        return out.isEmpty();
    }
}


/**
 * peek/pop: best O(1), worst O(n), TODO amortized ??
 * push: O(1)
 */
class LazyStackQueue<T> implements Queue<T> {

    private Deque<T> in = new LinkedList<>();
    private Deque<T> out = new LinkedList<>();

    @Override
    public void enqueque(T t) {
        in.push(t);
    }

    private void restackIfNeed() {
        if (out.isEmpty()) {
            restack(in, out);
        }
    }

    @Override
    public T dequeue() {
        restackIfNeed();
        return out.pop();
    }

    @Override
    public T peek() {
        restackIfNeed();
        return out.peek();
    }

    @Override
    public boolean isEmpty() {
        return in.isEmpty();
    }
}

