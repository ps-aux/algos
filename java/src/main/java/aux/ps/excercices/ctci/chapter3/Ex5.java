package aux.ps.excercices.ctci.chapter3;


import aux.ps.datastructures.queue.Queue;
import aux.ps.excercices.ctci.chapter3.data.QueueSpec;


class StackQueueSpec implements QueueSpec {


    @Override
    public <T> Queue<T> create() {
        return new StackQueue<>();
    }
}


class StackQueue<T> implements Queue<T> {

    @Override
    public void enqueque(T t) {

    }

    @Override
    public T dequeue() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

