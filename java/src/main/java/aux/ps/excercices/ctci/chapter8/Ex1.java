package aux.ps.excercices.ctci.chapter8;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


interface TripleStep {

    int possibleSteps(int pivot);

    @ParameterizedTest(name = "{0} is {1}")
    @ArgumentsSource(Data.class)
    default void test(int steps, int permutation) {
        assertThat(possibleSteps(steps)).isEqualTo(permutation);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {


            return Stream.of(
                    Arguments.of(1, 1),
                    Arguments.of(2, 2), // [1,1], [2]
                    Arguments.of(3, 4), // [1,1,1], [1, 2], [2, 1], [3]
                    Arguments.of(4, 7) // [1,1,1,1], [1, 2, 1], [2, 1, 1], [3, 1]
                    // [1,1,2], [2, 2]
                    // [1,3]
            );
        }
    }

}

class TripleStepImpl implements TripleStep {

    private Map<Integer, Integer> cache = new HashMap<>();

    @Override
    public int possibleSteps(int steps) {
        if (steps == 0)
            return 1;

        var cached = cache.get(steps);
        if (cached != null)
            return cached;


        int res = 0;
        res += possibleSteps(steps - 1);
        if (steps >= 2)
            res += possibleSteps(steps - 2);
        if (steps >= 3)
            res += possibleSteps(steps - 3);

        cache.put(steps, res);
        return res;
    }
}

