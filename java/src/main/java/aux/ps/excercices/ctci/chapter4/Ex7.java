package aux.ps.excercices.ctci.chapter4;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.params.provider.Arguments.of;


interface BuildOderSpec {

    List<Integer> buildOrder(int[] projects, List<Pair<Integer, Integer>> dependencies);


    default void assertNoCycle(List<Integer> order, List<Pair<Integer, Integer>> dependencies) {
        var unfulfilledDeps = dependencies;

        for (int i : order) {
            // Check if has dependencies
            if (unfulfilledDeps.stream().anyMatch(p -> p.getSecond() == i))
                throw new IllegalStateException(i + "has unfulfilled dependencies");

            // Remove from dependencies
            unfulfilledDeps = unfulfilledDeps.stream()
                    .filter(p -> p.getFirst() == i).collect(toList());
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


class Node {

    public int id;
    public List<Node> in = new ArrayList<>();
    public List<Node> to = new ArrayList<>();

    public Node(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", in=" + in.size() +
                ", to=" + to.size() +
                '}';
    }
}

class BuildOderImpl implements BuildOderSpec {

    @Override
    public List<Integer> buildOrder(int[] projects, List<Pair<Integer, Integer>> dependencies) {
        List<Node> nodes = Arrays.stream(projects)
                .mapToObj(Node::new).collect(toList());

        Map<Integer, Node> map = nodes.stream()
                .collect(toMap(n -> n.id, identity()));

        dependencies.stream().forEach(d -> {
            Node from = map.get(d.getFirst());
            Node to = map.get(d.getSecond());

            from.to.add(to);
            to.in.add(from);

        });

        List<Node> order = new ArrayList<>();
        while (!nodes.isEmpty()) {
            List<Node> removed = nodes.stream()
                    .filter(n -> n.in.isEmpty())
                    .collect(toList());

            // Remove reference from target nodes
            removed.forEach(n ->
                    n.to.forEach(to ->
                            to.in.remove(n)));

            order.addAll(removed);
            nodes.removeAll(removed);
        }

        return order.stream().map(n -> n.id).collect(toList());
    }
}


