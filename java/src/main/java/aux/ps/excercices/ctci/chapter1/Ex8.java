package aux.ps.excercices.ctci.chapter1;

import aux.ps.excercices.ctci.data.Matrix;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.HashSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;


interface ZeroMatrixSpec {

    Matrix zero(Matrix m);

    @ParameterizedTest(name = "{0} zeroed is t{1}")
    @ArgumentsSource(ArgsProvider.class)
    default void test(Matrix a, Matrix b) {
        assertThat(zero(a)).isEqualTo(b);
    }
}

class ArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        var a0 = Matrix.of(new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 0, 1}});

        var a1 = Matrix.of(new int[][]{
                {1, 0, 1},
                {1, 0, 1},
                {0, 0, 0}
        });

        var c0 = Matrix.of(new int[][]{
                {1, 1, 1, 0},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1}});

        var c1 = Matrix.of(new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}});


        var b0 = Matrix.of(new int[][]{
                {1, 2},
                {3, 0},
        });

        var b1 = Matrix.of(new int[][]{
                {1, 0},
                {0, 0},
        });

        return Stream.of(
                Arguments.of(a0, a1),
                Arguments.of(b0, b1),
                Arguments.of(c0, c1)
        );
    }
}

class ZeroMatrixSpecImpl implements ZeroMatrixSpec {


    @Override
    public Matrix zero(Matrix m) {

        var column = new HashSet<Integer>();
        var rows = new HashSet<Integer>();

        m.points()
                .filter(p -> p.val == 0)
                .forEach(p -> {
                    column.add(p.x);
                    rows.add(p.y);
                });


        column.forEach(x -> {
            for (int y = 1; y < m.getSize() + 1; y++) {
                m.insert(x, y, 0);
            }
        });

        rows.forEach(y -> {
            for (int x = 1; x < m.getSize() + 1; x++) {
                m.insert(x, y, 0);
            }
        });


        return m;

    }
}
