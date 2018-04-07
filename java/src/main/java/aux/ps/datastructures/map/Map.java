package aux.ps.datastructures.map;

public interface Map<K, V> {

    void put(K k, V v);

    V get(K k);

    void remove(K k);

    int size();

}
