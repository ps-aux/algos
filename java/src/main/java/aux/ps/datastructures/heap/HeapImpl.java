package aux.ps.datastructures.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class HeapImpl<K extends Comparable<K>, V> implements Heap<K, V> {

    private int initialSize = 64 + 1;

    private Node<K, V>[] array = new Node[initialSize];

    // We start at position 1 for easier math
    private int last = 0;
    private int root = 1;
    private final Comparator<K> comp;

    public HeapImpl(Comparator<K> comp) {
        this.comp = comp;
    }

    private void growArrayIfNeeded() {
        if (array.length == last) {
            Node[] newArray = new Node[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }

            array = newArray;
        }
    }

    private void percolate(int i) {
        if (i == root)
            return;
        int p = parent(i);
        if (gt(i, p)) {
            swap(i, p);
            percolate(p);
        }
    }

    private boolean gt(int aIdx, int bIdx) {
        Node<K, V> a = array[aIdx];
        Node<K, V> b = array[bIdx];
        if (b == null)
            return true;
        if (a == null)
            return false;
        return comp.compare(a.key, b.key) > 0;
    }

    private void swap(int a, int b) {
        Node<K, V> tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;

    }

    private void sink(int i) {
        if (isLeaf(i))
            return;
        int l = left(i);
        int r = right(i);

        // If any of children is greater
        if (gt(l, i) || gt(r, i)) {
            int successor = pickBigger(l, r);
            // Demote
            swap(i, successor);
            // Successor is now former demoted parent
            sink(successor);
        }
    }

    private int pickBigger(int aIdx, int bIdx) {
        return gt(aIdx, bIdx) ? aIdx : bIdx;
    }


    private int left(int idx) {
        return idx * 2;
    }

    private int parent(int idx) {
        if (idx == root)
            throw new IllegalStateException("Root element has no parent");
        return idx / 2;
    }

    private int right(int idx) {
        return idx * 2 + 1;
    }

    private boolean isLeaf(int i) {
        int l = i * 2;
        return l > array.length - 1 || array[i] == null;
    }

    @Override
    public void insert(K k, V v) {
        growArrayIfNeeded();
        array[++last] = new Node<>(k, v);
        percolate(last);
    }

    @Override
    public void change(K k, V v) {
        int i = find(k, root);

        if (i == -1)
            throw new NoSuchElementException("With key " + k);

        Node<K, V> n = array[i];
        n.val = v;

    }


    private int find(K k, int i) {
        if (i > array.length - 1)
            return -1;

        Node<K, V> n = array[i];
        if (n == null)
            return -1;
        // Key is greater so it can't be in this tree
        if (comp.compare(k, n.key) > 0) {
            return -1;
        }

        if (comp.compare(k, n.key) == 0)
            return i;

        int l = left(i);
        int r = right(i);

        // Try luck first in left branch
        int lAttempt = find(k, l);
        if (lAttempt != -1)
            return lAttempt;
        // ... and then in right branch
        return find(k, r);
    }

    @Override
    public V max() {
        return array[root].val;
    }

    @Override
    public K maxKey() {
        return array[root].key;
    }

    @Override
    public V delMax() {
        V v = max();
        replaceParent(root);
        last--;
        return v;
    }

    private void replaceParent(int i) {
        if (isLeaf(i))
            return;

        int successor = pickBigger(left(i), right(i));

        array[i] = array[successor];

        replaceParent(successor);
    }

    @Override
    public int size() {
        return last;
    }

    @Override
    public void merge(Heap<K, V> h) {
        while (h.size() > 0) {
            K k = h.maxKey();
            insert(k, h.delMax());
        }
    }


    @Override
    public V find(K k) {
        int i = find(k, root);
        if (i == -1)
            throw new NoSuchElementException("With key " + k);
        return array[i].val;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    private static class Node<K extends Comparable<K>, V> {

        K key;
        V val;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return "{" + key + "=" + val + '}';
        }
    }
}
