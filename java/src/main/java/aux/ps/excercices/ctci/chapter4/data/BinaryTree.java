package aux.ps.excercices.ctci.chapter4.data;

import static java.util.stream.Collectors.joining;

/**
 * TODO shameless copy of Bst code
 */
public class BinaryTree {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }


        private static String spaceOfSize(int l) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < l; i++) {
                sb.append(" ");
            }

            return sb.toString();
        }

        private int childHeight(Node n) {
            if (n == null)
                return 0;
            return n.height();
        }

        public int height() {
            var r = 1 + Math.max(childHeight(left), childHeight(right));
            return r;
        }

        public String makeString() {

            var col = collectVals();
            var h = height();

            StringBuilder sb = new StringBuilder();

            int valW = 2;
            int i = 0;
            var c = col;
            while (c != null) {
                int lvl = h - i;

                var offsetLen = (int) (Math.pow(2, lvl - 1) - 1);
                var spaceLen = 2 * offsetLen + 1;

                var offset = spaceOfSize(offsetLen * valW);
                var space = spaceOfSize(spaceLen * valW);

                sb.append(offset);
                var row = c.valStream().map(v -> v == null ? "  " : " " + v)
                        .collect(joining(space));
                sb.append(row);

                sb.append(offset);
                sb.append('\n');
                c = c.next;
                i++;
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            return makeString();
        }


        public Collector collectVals() {
            var h = height();
            var col = Collector.ofSize(h);
            collect(col);

            return col;
        }

        private void collect(Collector col) {
            col.add(val);
            var nextCol = col.next;

            // This is leaf node
            if (nextCol == null)
                return;

            if (left == null) {
                nextCol.addPlaceholder();
            } else {
                left.collect(nextCol);
            }
            if (right == null) {
                nextCol.addPlaceholder();
            } else {
                right.collect(nextCol);
            }
        }
    }

    public static Node build(int[] data) {
        var nodes = new Node[data.length];
        var root = new Node(data[0]);
        nodes[0] = root;

        for (int i = 1; i < data.length; i = i + 2) {
            int pIdx = (i - 1) / 2;
            Node p = nodes[pIdx];

            Node l = new Node(data[i]);
            Node r = new Node(data[i + 1]);

            p.left = l;
            p.right = r;

            nodes[i] = l;
            nodes[i + 1] = r;
        }

        return root;
    }

    public static void main(String[] args) {
        var t = build(new int[]{
                1,
                4, 5,
                8, 9, 10, 12

        });

        System.out.println(t);

    }
}
