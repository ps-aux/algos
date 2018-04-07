package aux.ps.datastructures.bst;

public class SearchTreeImpl<K extends Comparable<K>, V> implements SearchTree<K, V> {

    private Node root;
    private int size = 0;

    @Override
    public V find(K k) {
        Node n = find(k, root);
        if (n == null)
            return null;
        return n.val;
    }

    @Override
    public void insert(K k, V v) {
        if (root == null) {
            root = new Node(k, v);
            size++;
        }
        insert(k, v, root);
    }

    @Override
    public void delete(K k) {
        root = delete(k, root);
    }

    @Override
    public int size() {
        return size;
    }

    private boolean eq(K k1, K k2) {
        return k1.compareTo(k2) == 0;
    }

    private boolean gt(K k1, K k2) {
        return k1.compareTo(k2) > 0;
    }

    private Node find(K k, Node n) {
        if (n == null)
            return null;
        K nKey = n.key;
        if (eq(k, nKey))
            return n;

        if (gt(k, nKey))
            return find(k, n.right);
        else
            return find(k, n.left);
    }

    private Node delete(K k, Node n) {
        if (n == null)
            return null;
        if (eq(n.key, k)) {
            size--;
            return replaceByChild(n);
        } else {
            if (gt(k, n.key))
                n.right = delete(k, n.right);
            else
                n.left = delete(k, n.left);
            return n;
        }
    }

    private Node replaceByChild(Node n) {
        // No childe node
        if (n.right == null && n.left == null)
            return null;

        // Single child node
        if (n.right == null)
            return n.left;
        else if (n.left == null)
            return n.right;

        Node right = n.right;
        right.right = replaceByChild(right);

        return right;
    }


    private void insert(K k, V v, Node n) {
        if (eq(n.key, k)) {
            n.val = v;
            return;
        }
        if (gt(k, n.key)) {
            if (n.right == null) {
                n.right = new Node(k, v);
                size++;
            } else {
                insert(k, v, n.right);
            }
        } else {
            if (n.left == null) {
                n.left = new Node(k, v);
                size++;
            } else {
                insert(k, v, n.left);
            }
        }
    }

    private class Node {
        K key;
        V val;
        Node left;
        Node right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
}
