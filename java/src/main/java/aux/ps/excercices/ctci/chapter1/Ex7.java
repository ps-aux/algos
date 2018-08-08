package aux.ps.excercices.ctci.chapter1;

import aux.ps.excercices.ctci.data.Matrix;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;


class MatrixArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        int[][] a0 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] a1 = {
                {7, 4, 1},
                {8, 5, 2},
                {9, 6, 3}
        };


        int[][] b0 = {
                {1, 2},
                {3, 4},
        };

        int[][] b1 = {
                {3, 1},
                {4, 2},
        };

        int[][] c0 = {
                {1},
        };

        int[][] c1 = {
                {1},
        };

        return Stream.of(
                Arguments.of(a0, a1),
                Arguments.of(b0, b1),
                Arguments.of(c0, c1));
    }


    static void rotateLayer(int start, int size, Matrix src, Matrix dst) {

        var end = start + size - 1;
        // <0, size>
        Supplier<IntStream> loop = () -> IntStream.range(0, size);

        // Just optimalization
        if (size == 1) {
            var v = src.get(start, start);
            dst.insert(start, start, v);
            return;
        }

        // top -> left
        loop.get().forEach(i -> {
            var v = src.get(start + i, end);
            dst.insert(end, end - i, v);
        });

        // left -> bottom
        loop.get().forEach(i -> {
            var v = src.get(end, start + i);
            dst.insert(start + i, start, v);
        });

        // bottom -> left
        loop.get().forEach(i -> {
            var v = src.get(start + i, start);
            dst.insert(start, end - i, v);
        });


        // let -> top
        loop.get().forEach(i -> {
            var v = src.get(start, start + i);
            dst.insert(start + i, end, v);
        });

    }


    public static void main(String[] args) {


        var n = 5;
        var m = Matrix.withSequenceInts(n);
        var dst = Matrix.zeroed(n);
        System.out.println(m);

        System.out.println("-------");
        var layerCount = Math.round(n / 2.0);

        for (int i = 0; i < layerCount; i++) {
            rotateLayer(i + 1, n - 2 * i, m, dst);
        }

        System.out.println(dst);

    }

}

interface RotateMatrixSpec {

    int[][] rotate(int[][] img);

    @ParameterizedTest(name = "{0} rotated by 90 degrees is t{1}")
    @ArgumentsSource(MatrixArgsProvider.class)
    default void test(int[][] a, int[][] b) {
        assertThat(rotate(a)).isEqualTo(b);
    }
}


class RotateMatrixSpecImpl implements RotateMatrixSpec {


    @Override
    public int[][] rotate(int[][] img) {
        return img;
    }
}
