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


interface CheckSubtreeSpec {


    boolean isSubtree(Node t, Node of);

    @ParameterizedTest(name = "tree is a subtree of another: {2}")
    @ArgumentsSource(Data.class)
    default void test(Node t, Node of, boolean expected) {
        assertThat(isSubtree(t, of)).isEqualTo(expected);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            Node t1Of = new Node(8);
            t1Of.add(4);
            t1Of.add(5);

            t1Of.add(20);
            t1Of.add(14);
            t1Of.add(22);

            Node t1T = new Node(20);
            t1T.add(14);
            t1T.add(22);

            boolean t1Res = true;

            Node t2Of = t1Of;

            Node t2T = new Node(23);
            t2T.add(14);

            boolean t2Res = false;


            return Stream.of(
//                    of(t1T, t1Of, t1Res)
                    of(t2T, t2Of, t2Res)
            );
        }
    }
}

// Assumes no duplicates
class CheckSubtreeImpl implements CheckSubtreeSpec {

    Node find(Node n, int val) {
        if (n == null)
            return null;
        if (n.val == val)
            return n;

        var left = find(n.left, val);
        if (left != null)
            return left;

        return find(n.right, val);
    }

    boolean areSame(Node a, Node b) {
        if (a == null && b == null)
            return true;

        if (a == null)
            return false;
        if (b == null)
            return false;

        if (a.val != b.val)
            return false;

        return areSame(a.left, b.left) && areSame(a.right, b.right);
    }

    @Override
    public boolean isSubtree(Node t, Node of) {
        Node n = find(of, t.val);
        if (n == null)
            return false;

        return areSame(n, t);
    }
}
