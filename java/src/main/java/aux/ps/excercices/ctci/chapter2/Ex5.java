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


interface SumListspec {

    Node sumLists(Node a, Node b);


    @ParameterizedTest(name = "sum of {0} and {1} is {2}")
    @ArgumentsSource(Data.class)
    default void test(Node a, Node b, Node sum) {
        var res = sumLists(a, b);
        assertThat(res).isEqualTo(sum);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {


            return Stream.of(
                    Arguments.of(
                            of(1, 2, 3),
                            of(1, 2, 3),
                            of(2, 4, 6)),
                    Arguments.of(
                            of(9, 1),
                            of(1),
                            of(0, 2)),
                    Arguments.of(
                            of(9, 7, 8),
                            of(6, 8, 5),
                            of(5, 6, 4, 1)),
                    Arguments.of(
                            of(1),
                            of(1, 2, 3),
                            of(2, 2, 3)),
                    Arguments.of(
                            of(1),
                            of(1),
                            of(2))
            );
        }
    }

}

class SumLists implements SumListspec {

    int radix = 10;

    Node sum(Node a, Node b, Node sumStart, Node sumEnd) {
        if (a == null && b == null) {

            if (sumStart == null) // Summing two nulls
                return null;

            // Handle carry over
            if (sumEnd.data > (radix - 1)) {
                sumEnd.data = sumEnd.data - radix;
                sumEnd.next = new Node(1);
            }
            return sumStart;
        }

        int aVal;
        Node aNext;
        int bVal;
        Node bNext;

        if (a == null) {
            aVal = 0;
            aNext = null;
        } else {
            aVal = a.data;
            aNext = a.next;
        }

        if (b == null) {
            bVal = 0;
            bNext = null;
        } else {
            bVal = b.data;
            bNext = b.next;
        }

        int res = aVal + bVal;

        // Handle carry over
        if (sumEnd != null && sumEnd.data > (radix - 1)) {
            res = res + 1;
            sumEnd.data = sumEnd.data - radix;
        }


        Node n = new Node(res);
        if (sumEnd == null) {
            assert sumStart == null;
            sumStart = n;
        } else {
            sumEnd.next = n;

        }
        return sum(aNext, bNext, sumStart, n);
    }

    @Override
    public Node sumLists(Node a, Node b) {
        return sum(a, b, null, null);
    }
}

interface SumListsForwardpec {

    Node sumLists(Node a, Node b);


    @ParameterizedTest(name = "sum of {0} and {1} is {2}")
    @ArgumentsSource(Data.class)
    default void test(Node a, Node b, Node sum) {
        var res = sumLists(a, b);
        assertThat(res).isEqualTo(sum);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {


            return Stream.of(
                    Arguments.of(
                            of(1, 2, 3),
                            of(1, 2, 3),
                            of(2, 4, 6)),
                    Arguments.of(
                            of(1, 9),
                            of(1),
                            of(2, 0)),
                    Arguments.of(
                            of(1),
                            of(1, 2, 3),
                            of(1, 2, 4)),
                    Arguments.of(
                            of(9, 7, 8),
                            of(6, 8, 5),
                            of(1, 6, 6, 3)),
                    Arguments.of(
                            of(1),
                            of(1),
                            of(2))
            );
        }
    }

}


/**
 * TODO revisit and implement the solution in the book
 */
class SumListsForward implements SumListsForwardpec {

    int radix = 10;

    int size(Node list) {
        var i = 0;
        Node n = list;
        while (true) {
            if (n == null)
                return i;
            n = n.next;
            i++;
        }
    }

    Node zeroedOfSize(int size) {
        if (size == 0)
            return null;
        Node first = new Node(0);
        Node n = first;

        for (int i = 1; i < size; i++) {
            Node newN = new Node(0);
            n.next = newN;
            n = newN;
        }

        return first;
    }


    boolean hasCarryOver(int val) {
        return val > radix - 1;
    }


    Node lastNode(Node list) {
        Node n = list;
        while (true) {
            if (n.next == null)
                return n;

            n = n.next;
        }

    }


    // TODO refactor the code
    Node sum(Node a, Node b, Node sumEnd) {
        // a and b have same length
        if (a == null)
            return sumEnd;

        var digitSum = a.data + b.data;

        Node start = null;
        Node end;
        if (sumEnd == null) {
            // First node
            if (hasCarryOver(digitSum)) {
                start = new Node(1);
                start.next = new Node(digitSum - radix);
                end = start.next;
            } else {
                start = new Node(digitSum);
                end = start;
            }
            sumEnd = end;
        } else {
            // Not first
            if (hasCarryOver(digitSum)) {
                sumEnd.data = sumEnd.data + 1;
                if (hasCarryOver(sumEnd.data)) {
                    sumEnd.data = 1;
                    sumEnd.next = new Node(sumEnd.data - radix);
                    sumEnd = sumEnd.next;
                }
                sumEnd.next = new Node(digitSum - radix);
            } else {
                sumEnd.next = new Node(digitSum);
            }
            sumEnd = sumEnd.next;
        }

        sum(a.next, b.next, sumEnd);


        return start;
    }

    @Override
    public Node sumLists(Node a, Node b) {
        int sizeA = size(a);
        int sizeB = size(b);

        int diff = sizeA - sizeB;
        if (diff > 0) {
            var zeroes = zeroedOfSize(Math.abs(diff));
            lastNode(zeroes).next = b;
            b = zeroes;
        } else if (diff < 0) {
            var zeroes = zeroedOfSize(Math.abs(diff));
            lastNode(zeroes).next = a;
            a = zeroes;
        }

        var r = sum(a, b, null);

        return r;

    }
}

