package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;


interface FirstCommonAncesterSpec {


    Node firstCommonAncestor(Node a, Node b);

    @ParameterizedTest(name = "projects are build in correct order")
    @ArgumentsSource(Data.class)
    default void test(Node a, Node b, Node expected) {

    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            int[] t1 = {1, 2, 3, 4, 5, 6};
            List<Pair<Integer, Integer>> t1Deps = List.of(
                    new Pair<>(1, 4),
                    new Pair<>(6, 2),
                    new Pair<>(2, 4),
                    new Pair<>(6, 1),
                    new Pair<>(4, 3)
            );

            return Stream.of(
                    of(t1, t1Deps, true)
            );
        }
    }
}

class FcaImpl implements FirstCommonAncesterSpec {

    @Override
    public Node firstCommonAncestor(Node a, Node b) {
        return null;
    }
}


