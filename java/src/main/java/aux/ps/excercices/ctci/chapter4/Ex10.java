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

    @ParameterizedTest(name = "tree is a subtree of another")
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
                    of(t1T, t1Of, t1Res),
                    of(t2T, t2Of, t2Res)
            );
        }
    }
}

class CheckSubtreeImpl implements CheckSubtreeSpec {

    @Override
    public boolean isSubtree(Node t, Node of) {
        return false;
    }
}
