package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


interface SuccessorSpec {

    Node findNext(Node bst);

    @ParameterizedTest(name = "find next")
    @ArgumentsSource(Data.class)
    default void test(Node n1, Node n2) {
        assertThat(findNext(n1)).isEqualTo(n2);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            var t1 = new Node(10);
            Node t1Res = null;

            var t2Root = new Node(10);
            t2Root.add(5);
            t2Root.add(1);
            t2Root.add(7);

            var t2 = t2Root.findNode(1);
            var t2Res = t2Root.findNode(5);

            var t3 = new Node(10);
            t3.add(15);
            var t3Res = t3.right;

            return Stream.of(
                    of(t1, t1Res),
                    of(t2, t2Res),
                    of(t3, t3Res)
            );
        }
    }
}

class SuccessorImpl implements SuccessorSpec {

    boolean isLeft(Node n) {
        return n.parent.left == n;
    }

    @Override
    public Node findNext(Node n) {
        var r = n.right;

        // Find leftmost node in the right subtree.
        if (r != null) {
            Node n2 = r;
            while (n2.left != null) {
                n2 = n2.left;
            }
            return n2;
        }

        // No right node. No Parent. This is the root node.
        if (n.parent == null)
            return null;

        // Is left node. Parent node is next.
        if (isLeft(n))
            return n.parent;

        // It is right node. Find the first parent which is left node.
        Node n2 = n;
        while (!isLeft(n2) || n.parent != null) {
            n2 = n2.parent;
        }

        if (n.parent == null)
            // It was the last node - rightmost node.
            return null;

        return n.parent;
    }
}
