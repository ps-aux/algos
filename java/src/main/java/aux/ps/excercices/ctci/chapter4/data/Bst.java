package aux.ps.excercices.ctci.chapter4.data;


import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Collector {

    public final Collector next;
    private Deque<Integer> values;

    public Collector(Collector next) {
        this.next = next;
        values = new LinkedList<>();
    }

    void add(Integer i) {
        values.add(i);
    }

    void addPlaceholder() {
        values.add(null);

        if (next != null) {
            next.addPlaceholder();
            next.addPlaceholder();
        }
    }

    Stream<Integer> valStream() {
        return values.stream();
    }

    static Collector ofSize(int n) {
        if (n == 0)
            return null;
        return new Collector(ofSize(n - 1));
    }

    @Override
    public String toString() {
        return values + "\n" + (next == null ? "" : next.toString());
    }
}

public class Bst {

    public static class Node {

        public final int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }


        public int height() {
            var r = 1 + Math.max(childHeight(left), childHeight(right));
            return r;
        }

        private int childHeight(Node n) {
            if (n == null)
                return 0;
            return n.height();
        }

        public void add(int i) {
            if (i == val)
                return;

            if (i > val) {
                if (right == null) {
                    right = new Node(i);
                } else {
                    right.add(i);
                }
            } else {
                assert i < val;
                if (left == null) {
                    left = new Node(i);
                } else {
                    left.add(i);
                }
            }


        }

        private static String spaceOfSize(int l) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < l; i++) {
                sb.append(" ");
            }

            return sb.toString();
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

    public static void main(String[] args) {
        var n = new Node(10);
        n.add(5);

        n.add(1);
        n.add(7);


        n.add(15);
        n.add(12);
        n.add(17);

        n.add(20);
        n.add(21);
        System.out.println(n);
    }

}

class BstTest {

    @Test
    public void height() {
        var sut = new Node(5);
        assertThat(sut.height()).isEqualTo(1);

        sut.add(3);
        assertThat(sut.height()).isEqualTo(2);
        sut.add(10);
        assertThat(sut.height()).isEqualTo(2);

        sut.add(11);
        assertThat(sut.height()).isEqualTo(3);
        sut.add(9);
        assertThat(sut.height()).isEqualTo(3);
        sut.add(1);
        assertThat(sut.height()).isEqualTo(3);

        sut.add(12);
        assertThat(sut.height()).isEqualTo(4);


    }


}
