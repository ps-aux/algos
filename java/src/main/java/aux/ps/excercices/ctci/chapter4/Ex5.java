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


interface ValidateBstSpec {

    boolean isValidBst(Node bst);

    @ParameterizedTest(name = "{0} has minimal length")
    @ArgumentsSource(Data.class)
    default void test(Node bst, boolean expected) {
        assertThat(isValidBst(bst)).isEqualTo(expected);
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
            var t3Res = true;


            var t4 = new Node(4);
            t4.left = new Node(4);
            var t4Res = false;

            var t5 = new Node(2);
            t5.left = new Node(3);
            t5.right = new Node(4);
            var t5Res = false;

            var t6 = new Node(5);
            var t6Left = new Node(2);
            t6.left = t6Left;
            t6Left.left = new Node(3);
            var t6Res = false;


            return Stream.of(
                    of(t1, t1Res),
                    of(t2, t2Res),
                    of(t3, t3Res),
                    of(t4, t4Res),
                    of(t5, t5Res),
                    of(t6, t6Res)
            );
        }
    }
}

class ValidateBstImpl implements ValidateBstSpec {

    @Override
    public boolean isValidBst(Node bst) {
        if (bst == null)
            return true;
        if (bst.left != null && bst.left.val >= bst.val)
            return false;
        if (bst.right != null && bst.right.val <= bst.val)
            return false;

        return isValidBst(bst.right) && isValidBst(bst.left);
    }
}
