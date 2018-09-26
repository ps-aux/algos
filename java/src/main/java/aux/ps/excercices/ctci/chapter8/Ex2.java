package aux.ps.excercices.ctci.chapter8;

import aux.ps.excercices.ctci.data.Coords;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.data.Coords.c;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;


interface RobotInAGridSpec {

    List<Coords> path(int c, int r, Collection<Coords> coords);

    default boolean isValidPath(List<Coords> path,
                                int c, int r) {
        var first = path.get(0);
        var last = path.get(path.size() - 1);
        // Is valid start
        if (first.x != 1 && first.y != r)
            return false;
        // Is valid end
        if (last.x != c && last.y != 1)
            return false;


        for (int i = 1; i < path.size(); i++) {
            var prev = path.get(i - 1);
            var curr = path.get(i);

            if (curr.y == 0 || curr.y == r + 1 || curr.x == 0 || curr.x == c + 1)
                return false;

            var leftMove = curr.y == prev.y - 1 && curr.x == prev.x;
            var rightMove = curr.y == prev.y && curr.x == prev.x + 1;

            if (leftMove || rightMove) {
            } else
                return false;
        }

        return true;
    }


    @ParameterizedTest(name = "{0} is {1}")
    @ArgumentsSource(ValidData.class)
    default void outputIsValid(int c, int r, List<Coords> blocks) {
        var res = path(c, r, blocks);
        assertThat(isValidPath(res, c, r)).isTrue();
    }

    @ParameterizedTest(name = "{0} is {1}")
    @ArgumentsSource(ExpectedData.class)
    default void outputIsAsExpected(int c, int r, List<Coords> blocks, List<Coords> expected) {
        var res = path(c, r, blocks);

        assertThat(isValidPath(res, c, r)).isTrue();
    }


    @Test
    default void exceptionOnNoPath() {
        var c = 4;
        var r = 4;

        var block = IntStream.range(1, 5)
                .mapToObj(x -> c(x, 2))
                .collect(toList());
        /**
         * o o o o
         * x x x x
         * o o o o
         * o o o o
         */

        assertThatThrownBy(() -> path(c, r, block))
                .isInstanceOf(IllegalStateException.class);


    }

    @RepeatedTest(10)
    default void randomInput() {
        Random ran = new Random(System.nanoTime());
        int n = ran.nextInt(1000) + 1;
        int c = n;
        int r = n;

        int iterations = ran.nextInt(n * n);
        List<Coords> blocks = IntStream.range(0, iterations)
                .mapToObj(x ->
                        c(ran.nextInt(n) + 1,
                                ran.nextInt(n) + 1))
                .collect(toList());

        try {
            var p = path(c, r, blocks);
            assertThat(isValidPath(p, c, r));
        } catch (IllegalStateException e) {
            // nothing
        }


    }

    class ValidData implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    of(3, 3, List.of(c(1, 2))),
                    of(10, 10, List.of(
                            c(1, 2),
                            c(3, 6),
                            c(5, 5),
                            c(7, 8)
                    ))
            );
        }
    }

    class ExpectedData implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    /**
                     * o o o o
                     * x x x o
                     * o o o o
                     * o o o o
                     */
                    of(4, 4,
                            List.of(c(1, 2), c(2, 2), c(3, 2)),
                            List.of(c(1, 4), c(2, 4), c(3, 4), c(4, 4),
                                    c(4, 3), c(4, 2), c(4, 1)
                            )
                    ));
        }
    }

}


class RobotInAGridImpl implements RobotInAGridSpec {

    @Override
    public List<Coords> path(int c, int r, Collection<Coords> blocks) {
        var t = new Track(c, r, blocks);

        while (!t.isInFinish()) {
            var moves = t.possibleMoves();
            if (moves.isEmpty()) {
                t.addDeadEnd(t.currentPosition());
                t.backtrack();
            } else {
                t.move(moves.get(0));
            }
        }

        return t.getPath();
    }

    private static class Track {

        int c;
        int r;
        Set<Coords> blocks;
        LinkedList<Coords> path = new LinkedList<>();
        Set<Coords> deadEnds = new HashSet<>();

        Track(int c, int r, Collection<Coords> blocks) {
            path.add(c(1, c));
            this.r = r;
            this.c = c;
            this.blocks = new HashSet<>(blocks);
        }

        void addDeadEnd(Coords c) {
            deadEnds.add(c);
        }

        boolean isInFinish() {
            return currentPosition().equals(c(c, 1));
        }

        void move(Coords c) {
            if (!possibleMoves().contains(c))
                throw new IllegalArgumentException();
            path.add(c);
        }

        void backtrack() {
            if (path.size() < 3)
                throw new IllegalStateException("Backtracked to or from the starting point");
            path.removeLast();
        }

        Coords currentPosition() {
            return path.getLast();
        }

        List<Coords> getPath() {
            return path;
        }

        List<Coords> possibleMoves() {
            List<Coords> moves = new ArrayList<>();
            var position = currentPosition();
            var down = position.add(0, -1);
            var right = position.add(1, 0);

            if (down.y != 0 && !blocks.contains(down)
                    && !deadEnds.contains(down))
                moves.add(down);

            if (right.x != c + 1 && !blocks.contains(right)
                    && !deadEnds.contains(right))
                moves.add(right);

            return moves;
        }


    }


}

