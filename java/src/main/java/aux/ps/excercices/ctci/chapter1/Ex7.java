package aux.ps.excercices.ctci.chapter1;

import aux.ps.excercices.ctci.data.Coords;
import aux.ps.excercices.ctci.data.Matrix;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.data.Coords.c;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Java6Assertions.assertThat;


class MatrixArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Matrix a0 = Matrix.of(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Matrix a1 = Matrix.of(new int[][]{
                {7, 4, 1},
                {8, 5, 2},
                {9, 6, 3}
        });


        Matrix b0 = Matrix.of(new int[][]{
                {1, 2},
                {3, 4},
        });

        Matrix b1 = Matrix.of(new int[][]{
                {3, 1},
                {4, 2},
        });

        Matrix c0 = Matrix.of(new int[][]{
                {1},
        });

        Matrix c1 = Matrix.of(new int[][]{
                {1},
        });

        return Stream.of(
                Arguments.of(a0, a1),
                Arguments.of(b0, b1),
                Arguments.of(c0, c1));
    }
}

interface RotateMatrixSpec {

    Matrix rotate(Matrix img);

    @ParameterizedTest(name = "{0} rotated by 90 degrees is t{1}")
    @ArgumentsSource(MatrixArgsProvider.class)
    default void test(Matrix a, Matrix b) {
        assertThat(rotate(a)).isEqualTo(b);
    }
}


class Rotation {
    public final Coords from;
    public final Coords to;

    Rotation(Coords from, Coords to) {
        this.from = from;
        this.to = to;
    }

    Rotation add(Coords c) {
        return new Rotation(
                from.add(c),
                to.add(c)
        );
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}


class InplaceRotationImpl implements RotateMatrixSpec {

    static Coords rotateTop(Coords c, int n) {
        int x = n;
        int y = n + 1 - c.x;

        return c(x, y);
    }

    static Coords rotateRight(Coords c, int n) {
        int x = c.y;
        int y = 1;

        return c(x, y);
    }

    static Coords rotateBottom(Coords c, int n) {
        int x = 1;
        int y = n + 1 - c.x;

        return new Coords(x, y);
    }

    static Coords rotateLeft(Coords c, int n) {
        int x = c.y;
        int y = n;

        return new Coords(x, y);
    }

    static Stream<Rotation> rotationsFromLeft(Coords c, int n) {
        List<BiFunction<Coords, Integer, Coords>> transforms = Arrays.asList(
                InplaceRotationImpl::rotateLeft,
                InplaceRotationImpl::rotateTop,
                InplaceRotationImpl::rotateRight,
                InplaceRotationImpl::rotateBottom);


        List<Rotation> rotations = new ArrayList<>();
        for (var t : transforms) {
            Coords rotated = t.apply(c, n);
            rotations.add(new Rotation(c, rotated));
            c = rotated;

        }

        return rotations.stream();
    }


    static void swap(List<Rotation> rotations, Matrix m) {
        if (rotations.size() != 4)
            throw new IllegalArgumentException();


        var first = rotations.get(0);
        int replaced;
        var inserted = m.get(first.from);

        for (Rotation r : rotations) {
            replaced = m.get(r.to);
            m.insert(r.to, inserted);
            inserted = replaced;
        }
    }

    static void rotateLayer(Coords start, int n, Matrix src) {

        if (n == 1) {
            var v = src.get(start);
            src.insert(start, v);
            return;
        }

        Coords offset = start
                .add(-1, -1); // Prevent +1

        for (int i = 1; i < n; i++) {
            Coords c = c(1, i);

            var rs = rotationsFromLeft(c, n)
                    .map(r -> r.add(offset));
            swap(rs.collect(toList()), src);
        }
    }

    @Override
    public Matrix rotate(Matrix img) {
        var n = img.getSize();
        var layerCount = Math.round(n / 2.0);

        for (int i = 0; i < layerCount; i++)
            rotateLayer(c(i + 1, i + 1), n - 2 * i, img);

        return img;
    }
}


// TODO finish
class SimpleInPlaceRotationImpl implements RotateMatrixSpec {

    @Override
    public Matrix rotate(Matrix img) {
        int n = img.getSize();
        int[][] m = img.getData();

        // FINISH
        for (int i = 0; i < n / 2; i++) {
            int start = i;
            int end = n - start - 1;

            for (int j = start; j < end; j++) {
                int top = m[start][j];

                m[start][j] = m[start][start];

            }
        }

        return img;

    }
}