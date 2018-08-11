package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.Assertions.assertThat;


interface PartitionSpec {

    Node partition(Node list, int pivot);

    default void assertIsPartitionAccordingTo(Node list, int pivot) {
        Node n = list;
        boolean leftEnded = false;
        while (n != null) {
            if (n.data >= pivot)
                leftEnded = true;

            if (leftEnded)
                assertThat(n.data).isGreaterThanOrEqualTo(pivot);

            n = n.next;
        }

    }

    @ParameterizedTest(name = "{0} is partitioned according to {1}")
    @ArgumentsSource(Data.class)
    default void test(Node list, int pivot) {
        var res = partition(list, pivot);
        assertIsPartitionAccordingTo(res, pivot);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {


            return Stream.of(
                    Arguments.of(
                            of(1, 5, 3, 6, 2, 4),
                            3),
                    Arguments.of(
                            of(1, 1, 1, 3, 1, 1, 1),
                            3),
                    Arguments.of(
                            of(3, 5),
                            3),
                    Arguments.of(
                            of(1, 3, 2),
                            5),
                    Arguments.of(
                            of(1),
                            5)
            );
        }
    }

}


class PartitionSpecImpl implements PartitionSpec {


    @Override
    public Node partition(Node n, int p) {
        Node smallerStart = null;
        Node smallerEnd = null;
        Node greaterStart = null;
        Node greaterEnd = null;

        while (n != null) {
            var data = n.data;
            Node newN = new Node(data);
            if (data < p) {
                if (smallerStart == null) {
                    smallerStart = newN;
                    smallerEnd = newN;
                } else {
                    smallerEnd.next = newN;
                    smallerEnd = newN;
                }
            } else {
                if (greaterStart == null) {
                    greaterStart = newN;
                    greaterEnd = newN;
                } else {
                    greaterEnd.next = newN;
                    greaterEnd = newN;
                }

            }
            n = n.next;
        }

        if (smallerEnd != null) {
            // No values on the left side
            smallerEnd.next = greaterStart;
            return smallerStart;
        } else {
            return greaterStart;
        }
    }
}

class PartitionSpecImpl2 implements PartitionSpec {


    @Override
    public Node partition(Node n, int p) {
        Node smaller = null;
        Node previous = null;
        while (n != null) {
            var data = n.data;
            if (data < p) {
                Node newNode = new Node(data);
                if (smaller == null)
                    smaller = newNode;
                else
                    smaller.next = newNode;

                // Delete this node
                if (previous != null) {
                    // Is first. Cannot be deleted as the other nodes.
                    previous.next = n.next;
                } else {
                    if (n.next != null) {
                        n.next = n.next.next;
                        n.data = n.next.data;
                    } else {
                        // Is first and last at the same time
                        n = null;
                        break;
                    }
                }
            }
            previous = n;
            n = n.next;
        }

        if (smaller != null) {
            // No values on the left side
            smaller.next = n;
            return smaller;
        } else {
            return n;
        }
    }
}
