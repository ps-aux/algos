package aux.ps.datastructures.trie;

import aux.ps.datastructures.map.HashMap;
import aux.ps.datastructures.map.Map;

import java.util.Objects;

public class Trie<V> implements Map<String, V> {

    private int size;
    private Map<Character, Node> children = new HashMap<>();

    @Override

    public void put(String s, V v) {
        if (s.isEmpty())
            throw new IllegalArgumentException("Empty string");
        Objects.requireNonNull(s);

        // The root node is virtual
        insertToChild(s, v, children);
    }

    private Node insert(String s, V val, Node n) {
        char ch = s.charAt(0);
        if (n == null) {
            n = new Node(ch, null);
        }
        if (s.length() == 1) {
            if (n.val == null)
                size++;
            n.val = val;
        } else
            insertToChild(s.substring(1), val, n.children);

        return n;
    }

    private void insertToChild(String s, V val, Map<Character, Node> children) {
        char ch = s.charAt(0);
        Node child = children.get(ch);
        Node done = insert(s, val, child);
        children.put(ch, done);
    }


    @Override
    public V get(String s) {
        if (s.isEmpty())
            throw new IllegalArgumentException("Empty string");
        Node n = find(s, children.get(s.charAt(0)));
        if (n == null)
            return null;

        return n.val;
    }

    @Override
    public void remove(String s) {
        Node n = find(s, children.get(s.charAt(0)));
        if (n != null)
            n.val = null;
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    private Node find(String s, Node n) {
        if (n == null)
            return null;

        assert s.charAt(0) == n.key;

        if (s.length() == 1)
            return n;

        return find(s.substring(1),
                n.children.get(s.charAt(1)));
    }

    private class Node {
        char key;
        V val;
        Map<Character, Node> children = new HashMap<>();

        public Node(char key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
