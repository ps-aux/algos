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


interface CheckBalancedSpec {

    boolean isBalanced(Node bst);

    @ParameterizedTest(name = "is balanced")
    @ArgumentsSource(Data.class)
    default void test(Node bsg, boolean expected) {
        assertThat(isBalanced(bsg)).isEqualTo(expected);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            var t1 = new Node(10);
            var t1Res = true;

            var t2 = new Node(10);
            t2.add(5);
            t2.add(1);
            t2.add(7);

            t2.add(15);
            t2.add(13);
            t2.add(17);
            var t2Res = true;

            var t3 = new Node(10);
            t3.add(5);
            t3.add(1);
            t3.add(7);
            var t3Res = false;

            var t4 = new Node(10);
            t2.add(5);
            t2.add(1);
            t2.add(7);

            t2.add(15);
            var t4Res = true;

            return Stream.of(
                    of(t1, t1Res),
                    of(t2, t2Res),
                    of(t3, t3Res),
                    of(t4, t4Res)
            );
        }
    }
}

class CheckBalancedImpl implements CheckBalancedSpec {


    @Override
    public boolean isBalanced(Node bst) {
        return isB(bst) > -1;
    }

    int isB(Node bst) {
        if (bst == null)
            return 0;

        int l = isB(bst.left);
        if (l < 0)
            return l;

        int r = isB(bst.right);
        if (r < 0)
            return r;

        if (Math.abs(l - r) > 1)
            return -1;

        return 1 + Math.max(r, l);
    }
}

