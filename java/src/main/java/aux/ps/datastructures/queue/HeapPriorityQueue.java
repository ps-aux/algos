package aux.ps.datastructures.queue;

import aux.ps.datastructures.heap.Heap;
import aux.ps.datastructures.heap.HeapImpl;

import java.util.Comparator;

public class HeapPriorityQueue<K extends Comparable<K>, V>
        implements PriorityQueue<K, V> {

    private final Heap<K, V> h;

    public HeapPriorityQueue(Comparator<K> comp) {
        h = new HeapImpl<>(comp);
    }


    @Override
    public void enqueque(K k, V v) {
        h.insert(k, v);
    }

    @Override
    public V dequeue() {
        return h.delMax();
    }

    @Override
    public V peek() {
        return h.max();
    }

    @Override
    public boolean isEmpty() {
        return h.size() == 0;
    }
}
