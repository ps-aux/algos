package aux.ps.datastructures.queue;

public interface PriorityQueue<K extends Comparable<K>, V> {

    void enqueque(K k, V v);

    V dequeue();

    V peek();

    boolean isEmpty();
}
