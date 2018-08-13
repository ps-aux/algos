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

    boolean hasIntersect(Node list);


    @ParameterizedTest(name = "{0} has intersect {1}")
    @ArgumentsSource(Data.class)
    default void test(Node list, boolean expected) {
        var res = hasIntersect(list);

        if (expected) {
            assertThat(res).isTrue();
        } else {
            assertThat(res).isFalse();
        }
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

            Node n1 = new Node(1);
            Node n2 = new Node(2);
            Node n3 = new Node(3);
            Node n4 = new Node(5);
            Node n5 = new Node(6);
            Node n6 = new Node(7);


            return Stream.of(
                    Arguments.of(
                            combine(n1, n2, n3),
                            combine(n1, of(1, 2)),
                            true),
                    Arguments.of(
                            combine(n1, n2, n3),
                            combine(of(1, 2), n1),
                            true),
                    Arguments.of(
                            combine(n1, n2, n3),
                            combine(n1),
                            true),
                    Arguments.of(
                            combine(n1, n2, n3),
                            combine(n3),
                            true),
                    Arguments.of(
                            combine(n1, n2, n3),
                            combine(n4, n5, n6),
                            false),
                    Arguments.of(
                            combine(n1, n2, n3),
                            combine(new Node(1), n5, n6),
                            false)
            );
        }
    }

}

class HasIntersectImpl implements HasIntersectSpec {


    @Override
    public boolean hasIntersect(Node list) {
        return false;
    }
}