package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.BinaryTree;
import aux.ps.excercices.ctci.chapter4.data.BinaryTree.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


interface FirstCommonAncestorSpec {


    Node firstCommonAncestor(Node tree, int a, int b);

    @ParameterizedTest(name = "first common ancestor found")
    @ArgumentsSource(Data.class)
    default void test(Node tree, int a, int b, int expected) {
        assertThat(firstCommonAncestor(tree, a, b).val).isEqualTo(expected);
    }

    @ParameterizedTest(name = "on not existing value return snull")
    @ArgumentsSource(Data.class)
    default void testNonExistent() {
        var t = BinaryTree.build(new int[]{
                1,
                4, 5,
                8, 9, 10, 12

        });

        assertThat(firstCommonAncestor(t, 999, 8)).isNull();
        assertThat(firstCommonAncestor(t, 10, 999)).isNull();
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            var t1 = BinaryTree.build(new int[]{
                    1,
                    4, 5,
                    8, 9, 10, 12

            });
            var t1A = 8;
            var t1B = 9;
            var t1Res = 4;

            var t2 = t1;
            var t2A = 8;
            var t2B = 12;
            var t2Res = 1;


            return Stream.of(
                    of(t1, t1A, t1B, t1Res),
                    of(t2, t2A, t2B, t2Res)
            );
        }
    }
}

class FcaImpl implements FirstCommonAncestorSpec {

    private boolean pathTo(Node n, int a, Deque<Node> path) {
        if (n == null)
            return false;
        if (a == n.val) {
            path.addFirst(n);
            return true;
        }

        var onLeft = pathTo(n.left, a, path);
        if (onLeft) {
            path.addFirst(n);
            return true;
        }
        var onRight = pathTo(n.right, a, path);
        if (onRight) {
            path.addFirst(n);
            return true;
        }

        return false;
    }

    @Override
    public Node firstCommonAncestor(Node tree, int a, int b) {
        var pathA = new LinkedList<Node>();
        var pathB = new LinkedList<Node>();

        if (!pathTo(tree, a, pathA) || !pathTo(tree, b, pathB))
            return null;


        Node last = null;
        while (pathA.getFirst() == pathB.getFirst()) {
            last = pathA.removeFirst();
            pathB.removeFirst();
        }


        System.out.println(last);
        return last;
    }
}


