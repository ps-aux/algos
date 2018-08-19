package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Graph;
import aux.ps.excercices.ctci.chapter4.data.Graph.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter4.data.GraphBuilder.graphOfSize;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


interface HasPath {

    boolean hasPath(Graph g, int from, int to);


    @ParameterizedTest(name = "{0} has root to {1}: {2}")
    @ArgumentsSource(Data.class)
    default void test(Graph g, int from, int to, boolean expected) {
        var res = hasPath(g, from, to);
        assertThat(res).isEqualTo(expected);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            /*
             *   1 -- 2 -- 3
             *   |         |
             *   4 -- 5 -- 6
             */
            Graph g1 = graphOfSize(6)
                    .connect(1, 2, 4)
                    .connect(2, 3)
                    .connect(4, 5)
                    .connect(6, 3, 5)
                    .build();
            var g1Args = Stream.of(
                    of(g1, 1, 6, true),
                    of(g1, 6, 1, true),
                    of(g1, 2, 5, true)
            );

            /*
             *   1 -- 2    3
             *   |         |
             *   4         6
             */
            Graph g2 = graphOfSize(6)
                    .connect(1, 2, 4)
                    .connect(3, 6)
                    .build();

            var g2Args = Stream.of(
                    of(g2, 1, 4, true),
                    of(g2, 3, 6, true),
                    of(g2, 1, 6, false)
            );

            return Stream.of(g1Args, g2Args).flatMap(x -> x);
        }
    }

}


class BfsImpl implements HasPath {

    @Override
    public boolean hasPath(Graph g, int from, int to) {
        Deque<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        queue.add(g.node(from));

        while (!queue.isEmpty()) {
            Node n = queue.removeFirst();
            if (n.isNeighbourOf(to))
                return true;

            visited.add(n);

            var unvisited = n.nodes.stream()
                    .filter(x -> !visited.contains(x))
                    .collect(toList());

            queue.addAll(unvisited);
        }

        return false;
    }
}

class StackDfsImpl implements HasPath {

    @Override
    public boolean hasPath(Graph g, int from, int to) {
        Deque<Node> stack = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        stack.push(g.node(from));

        while (!stack.isEmpty()) {
            Node n = stack.pop();
            if (n.isNeighbourOf(to))
                return true;

            visited.add(n);

            var unvisited = n.nodes.stream()
                    .filter(x -> !visited.contains(x))
                    .collect(toList());

            unvisited.forEach(stack::push);
        }

        return false;
    }
}

class RecursiveDfsImpl implements HasPath {


    private boolean hasPathTo(Node n, int to, Set<Node> visited) {
        if (n.isNeighbourOf(to))
            return true;
        for (Node neigh : n.nodes) {
            if (visited.contains(neigh))
                continue;
            visited.add(neigh);
            if (hasPathTo(neigh, to, visited))
                return true;
        }

        return false;
    }

    @Override
    public boolean hasPath(Graph g, int from, int to) {
        Set<Node> visited = new HashSet<>();
        return hasPathTo(g.node(from), to, visited);
    }
}
