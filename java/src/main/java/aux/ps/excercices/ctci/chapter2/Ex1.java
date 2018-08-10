package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.LinkedList;
import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.Assertions.assertThat;


interface RemoveDupsSpec {
    void removeDups(Node n);


    @ParameterizedTest(name = "{0} without duplicates is {1}")
    @ArgumentsSource(Data.class)
    default void test(Node a, Node b) {
        removeDups(a);
        assertThat(a).isEqualTo(b);

    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(
                            of(2, 2, 1, 4, 4, 3),
                            of(2, 1, 4, 3)),
                    Arguments.of(
                            of(1, 2),
                            of(1, 2)),
                    Arguments.of(
                            of(1, 1),
                            of(1)));
        }
    }

}


class RemoveDupsImpl implements RemoveDupsSpec {

    @Override
    public void removeDups(Node n) {
        Set<Integer> vals = new HashSet<>();

        Node previous = null;
        var current = n;

        while (current != null) {
            var val = current.data;

            if (!vals.add(val)) {
                // Cannot happen in first iteration
                previous.next = current;
            } else {
                previous = current;
            }
            current = current.next;


        }

    }
}

class RemoveDupsConstantSpaceImpl implements RemoveDupsSpec {

    @Override
    public void removeDups(Node n) {

        Node p1 = n;

        while (p1 != null) {
            Node p2 = p1.next;
            while (p2 != null) {
                if (p1.data == p2.data) {
                    p1.next = p2.next;
                }
                p2 = p2.next;
            }
            p1 = p1.next;
        }

    }
}
