package aux.ps.datastructures.heap;

public interface Heap<K extends Comparable<K>, V> {

    void insert(K k, V v);

    void change(K k, V v);

    V max();

    K maxKey();

    V delMax();

    int size();

    void merge(Heap<K, V> h);


    V find(K k);
}
