package aux.ps.datastructures.map;

import aux.ps.datastructures.list.LinkedList;

import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V> {

    private final int arrSize = 16;

    private LinkedList<Node>[] array = new LinkedList[arrSize];

    private int size;

    @Override
    public void put(K k, V v) {
        LinkedList<Node> l = list(k);
        if (l == null) {
            l = LinkedList.of(new Node(k, v));
            array[hashCode(k)] = l;
            size++;
            return;
        }


        Node existing = findNode(k, l);
        if (existing != null) {
            existing.val = v;
            return;
        }

        Node n = new Node(k, v);
        l.add(n);
        size++;
    }

    private LinkedList<Node> list(K k) {
        int code = hashCode(k);
        return array[code];
    }

    private int indexOf(K k, LinkedList<Node> l) {
        if (l == null)
            return -1;
        Iterator<Node> it = l.iterator();
        int c = -1;
        while (it.hasNext()) {
            Node next = it.next();
            c++;
            if (next.key.equals(k)) {
                return c;
            }
        }

        return -1;
    }

    private Node findNode(K k, LinkedList<Node> l) {
        int index = indexOf(k, l);
        if (index == -1)
            return null;
        return l.get(index);
    }

    private int hashCode(K k) {
        return Math.abs(k.hashCode()) % arrSize;
    }

    @Override
    public V get(K k) {
        Node n = findNode(k, list(k));
        if (n == null)
            return null;

        return n.val;
    }

    @Override
    public void remove(K k) {
        LinkedList<Node> l = list(k);
        int i = indexOf(k, l);
        if (i == -1)
            return;

        l.remove(i);
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    private class Node {

        K key;
        V val;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
