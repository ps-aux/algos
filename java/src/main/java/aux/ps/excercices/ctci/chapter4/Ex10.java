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


interface BstSequencesSpec {


    int[][] bstSequences(Node bst);

    @ParameterizedTest(name = "projects are build in correct order")
    @ArgumentsSource(Data.class)
    default void test(Node a, Node b, Node expected) {

    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            Node t1 = new Node(8);
            t1.add(4);
            t1.add(5);

            t1.add(20);
            t1.add(14);
            t1.add(22);

            int[][] t1Res = {
                    {8, 4, 5, 20, 14, 22},
                    {8, 4, 5, 20, 22, 14},
                    {8, 20, 22, 14, 4, 5}
            };


            return Stream.of(
                    of(t1, t1Res)
            );
        }
    }
}

class BstSequencesImpl implements  BstSequencesSpec {

    @Override
    public int[][] bstSequences(Node bst) {
        return new int[0][];
    }
}