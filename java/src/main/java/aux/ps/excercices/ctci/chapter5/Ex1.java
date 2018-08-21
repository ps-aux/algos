package aux.ps.excercices.ctci.chapter5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


interface FooSpec {

    int foo(int pivot);


    @ParameterizedTest(name = "{0} is {1}")
    @ArgumentsSource(Data.class)
    default void test(int i, int j) {
        var res = foo(0);
        assertThat(res).isEqualTo(j);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {


            return Stream.of(
                    Arguments.of(
                            of(1, 5, 3, 6, 2, 4),
                            3),
                    Arguments.of(
                            of(1, 1, 1, 3, 1, 1, 1),
                            3),
                    Arguments.of(
                            of(3, 5),
                            3),
                    Arguments.of(
                            of(1, 3, 2),
                            5),
                    Arguments.of(
                            of(1),
                            5)
            );
        }
    }

}


class FooSpecImpl implements FooSpec {

    @Override
    public int foo(int pivot) {
        return 0;
    }
}

