package aux.ps.datastructures.queue;

public interface Queue<T> {

    void enqueque(T t);

    T dequeue();

    T peek();

    boolean isEmpty();
}
