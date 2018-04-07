package aux.ps.datastructures.bst;

public interface SearchTree<K extends Comparable<K>, V> {

    V find(K k);

    void insert(K k, V v);

    void delete(K k);

    int size();

}
