package aux.ps.excercices.ctci.chapter5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;


interface InsertionSpec {

    int insert(int n, int m, int i, int j);


    @ParameterizedTest(name = "n={0}, m={1}, i={2}, j={3}")
    @ArgumentsSource(Data.class)
    default void test(int n, int m, int i, int j, int res) {
//        assertThat(insert(n, m, i, j)).isEqualTo(res);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            var n1 = Integer.valueOf("10000000000", 2);
            var m1 = Integer.valueOf("10011", 2);
            var i1 = 2;
            var j1 = 6;
            var res1 = Integer.valueOf("10001001100", 2);

            return Stream.of(
                    Arguments.of(n1, m1, i1, j1, res1));
        }
    }

}

class InsertionsImpl implements InsertionSpec {

    @Override
    public int insert(int n, int m, int i, int j) {
        var x = (-1 << j);
        var y = ~(-1 << i);

        var mask = x ^ y;

        return (n & mask) | (m << i);
    }

    public static void main(String[] args) {
        new InsertionsImpl().insert(0, 0, 2, 6);
    }
}


