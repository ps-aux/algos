package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.BinaryTree;
import aux.ps.excercices.ctci.chapter4.data.BinaryTree.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


interface FirstCommonAncestorSpec {


    Node firstCommonAncestor(Node tree, int a, int b);

    @ParameterizedTest(name = "projects are build in correct order")
    @ArgumentsSource(Data.class)
    default void test(Node tree, int a, int b, int expected) {
        assertThat(firstCommonAncestor(tree, a, b).val).isEqualTo(expected);
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
    @Override
    public Node firstCommonAncestor(Node tree, int a, int b) {
        return tree;
    }
}


