package aux.ps.excercices.ctci.data;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Matrix {

    private int[][] data;
    private int size;

    @Override
    public String toString() {
        return Arrays.stream(data)
                .map(row ->
                        Arrays.stream(row)
                                .mapToObj(v -> String.format("%2d", v))
                                .collect(joining(" ")))
                .collect(joining("\n"));

    }


    private int y(int y) {
        return size - y;
    }

    public int getSize() {
        return size;
    }

    private int x(int x) {
        return x - 1;
    }

    public void insert(int x, int y, int v) {
        data[y(y)][x(x)] = v;
    }

    public void insert(Coords c, int v) {
        insert(c.x, c.y, v);
    }


    public int get(int x, int y) {
        return data[y(y)][x(x)];
    }

    public int get(Coords c) {
        return get(c.x, c.y);
    }

    public int[][] getData() {
        return data;
    }

    public Stream<Point> points() {
        return IntStream.range(1, size + 1)
                .boxed()
                .flatMap(y ->
                        IntStream.range(1, size + 1)
                                .mapToObj(x -> Point.of(x, y, data[y(y)][x(x)])));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;

        return size == matrix.size &&
                Arrays.deepEquals(data, matrix.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    public static class Point {
        public final int x;
        public final int y;

        public final int val;

        private Point(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        static Point of(int x, int y, int val) {
            return new Point(x, y, val);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", val=" + val +
                    '}';
        }
    }

    private static Matrix ofSize(int n, boolean initWithZeroes) {
        int[][] arr = new int[n][];

        for (int i = 0; i < n; i++) {
            arr[i] = IntStream.range(i * n + 1, (i + 1) * n + 1)
                    .map(v -> initWithZeroes ? 0 : v).toArray();
        }

        var m = new Matrix();
        m.data = arr;
        m.size = n;

        return m;
    }

    public static Matrix zeroed(int size) {
        return ofSize(size, true);
    }

    public static Matrix withSequenceInts(int size) {
        return ofSize(size, false);
    }

    public static Matrix of(int[][] data) {
        Matrix m = new Matrix();
        m.data = data;
        m.size = data.length;

        for (var row : data)
            if (row.length != m.size)
                throw new IllegalArgumentException();
        return m;
    }


}
