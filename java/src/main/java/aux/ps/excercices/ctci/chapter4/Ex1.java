package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Graph;
import aux.ps.excercices.ctci.chapter4.data.GraphBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static aux.ps.excercices.ctci.chapter4.data.GraphBuilder.graphOfSize;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


interface HasRootSpec {

    boolean hasRoot(Graph g, int from, int to);


    @ParameterizedTest(name = "{0} has root to {1}: {2}")
    @ArgumentsSource(Data.class)
    default void test(Graph g, int from, int to, boolean expected) {
        var res = hasRoot(g, from, to);
        assertThat(expected).isEqualTo(res);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            /*
             *   1 -- 2 -- 3
             *   |         |
             *   4 -- 5 -- 6
             */
            Graph g1 = graphOfSize(6)
                    .connect(1, 2, 4)
                    .connect(2, 3)
                    .connect(4, 5)
                    .connect(6, 3, 5)
                    .build();

            return Stream.of(
                    Arguments.of(g1, 1, 6, true),
                    Arguments.of(g1, 6, 1, true),
                    Arguments.of(g1, 2, 5, true)
            );
        }
    }

}


class HasRootToImpl implements HasRootSpec {

    @Override
    public boolean hasRoot(Graph g, int from, int to) {
        System.out.println(g);
        return false;
    }
}

