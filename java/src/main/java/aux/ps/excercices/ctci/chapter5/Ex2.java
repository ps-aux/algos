package aux.ps.excercices.ctci.chapter5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;


interface BinaryToStringSpec {

    String toString(float n);


    @ParameterizedTest(name = "n={0}, m={1}, i={2}, j={3}")
    @ArgumentsSource(Data.class)
    default void test(int n, int m, int i, int j, int res) {
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of());
        }

    }

}

