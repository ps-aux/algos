package aux.ps.excercices.ctci.chapter4;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;


interface BuildOderSpec {

    int[] buildOrder(int[] projects, List<Pair<Integer, Integer>> dependencies);


    default void assertNoCycle(int[] order, List<Pair<Integer, Integer>> dependencies) {
        var unfulfilledDeps = dependencies.stream();

        for (int i : order) {
            // Check if has dependencies
            if (unfulfilledDeps.anyMatch(p -> p.getSecond() == i))
                throw new IllegalStateException(i + "has unfulfilled dependencies");

            // Remove from dependencies
            unfulfilledDeps = unfulfilledDeps.filter(p -> p.getFirst() == i);
        }
    }

    @ParameterizedTest(name = "projects are build in correct order")
    @ArgumentsSource(Data.class)
    default void test(int[] projects,
                      List<Pair<Integer, Integer>> dependencies, boolean expected) {

        assertNoCycle(buildOrder(projects, dependencies), dependencies);
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


class BuildOderImpl implements BuildOderSpec {

    @Override
    public int[] buildOrder(int[] projects, List<Pair<Integer, Integer>> dependencies) {
        return new int[0];
    }
}


