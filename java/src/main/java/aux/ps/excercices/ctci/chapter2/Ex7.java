package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


interface HasIntersectSpec {

    Node intersect(Node a, Node b);


    @ParameterizedTest(name = "{0} intersects with {1} in {2}")
    @ArgumentsSource(Data.class)
    default void test(Node a, Node b, Node expected) {
        var res = intersect(a, b);

        assertThat(intersect(a, b)).isSameAs(expected);
    }

    class Data implements ArgumentsProvider {

        static Node combine(Node... nodes) {
            for (int i = 0; i < nodes.length - 1; i++) {
                nodes[i].next = nodes[i + 1];
            }
            return nodes[0];
        }

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            /**
             * a:    1 - 2 - 3
             *      /
             * b:  0
             */
            Node a0 = of(1, 2, 3);
            Node a1 = combine(new Node(0), a0);
            Node aRes = a0;

            /**
             * a: 1-2
             *       \
             * b:     3
             */
            Node b0 = of(1, 2, 3);
            Node b1 = b0.next.next;
            Node bRes = b1;

            /**
             * a: 1-2
             *       \
             *        5-6-7
             *       /
             * b: 3-4
             */
            Node cCommon = of(5, 6, 7);
            Node c0 = combine(of(1, 2), cCommon);
            Node c1 = combine(of(3, 4), cCommon);
            Node cRes = cCommon;

            /**
             * a: 1-2
             *       \
             *        5-6-7
             *       /
             * b:   3
             */
            Node eCommon = of(5, 6, 7);
            Node e0 = combine(of(1, 2), eCommon);
            Node e1 = combine(of(3), eCommon);
            Node eRes = eCommon;

            /**
             * a: 1-2-3
             * b: 1-2-3
             */
            Node d0 = of(1, 2, 3);
            Node d1 = of(1, 2, 3);
            Node dRes = null;

            Node f0 = of(1, 2, 3);
            Node f1 = null;
            Node fRes = null;

            Node g0 = null;
            Node g1 = null;
            Node gRes = null;


            return Stream.of(
                    Arguments.of(a0, a1, aRes),
                    Arguments.of(b0, b1, bRes),
                    Arguments.of(c0, c1, cRes),
                    Arguments.of(e0, e1, eRes),
                    Arguments.of(d0, d1, dRes),
                    Arguments.of(f0, f1, fRes),
                    Arguments.of(g0, g1, gRes)
            );
        }
    }

}

class HasIntersectLinearImpl implements HasIntersectSpec {

    int size(Node n) {
        int i = 0;
        while (true) {
            if (n == null)
                return i;
            n = n.next;
            i++;
        }
    }

    /**
     * Linked lists with intersect share at least tail or more nodes before the tail
     * O(n + m) time
     * O(1) space
     */
    @Override
    public Node intersect(Node a, Node b) {
        int sizeA = size(a);
        int sizeB = size(b);

        // Can be optimized with also checking the tails on the first pass
        // If tails are not the same then they have no intersect

        int diff = sizeA - sizeB;

        boolean isALonger = diff > 0;

        int remainingDiff = Math.abs(diff);
        while (remainingDiff > 0) {
            if (isALonger)
                a = a.next;
            else
                b = b.next;
            remainingDiff--;
        }

        if (a == b)
            return a;

        Node pA = a;
        Node pB = b;
        while (pA != null && pB != null) {
            if (pA == pB)
                return pA;

            pA = pA.next;
            pB = pB.next;
        }
        return null;
    }

}
