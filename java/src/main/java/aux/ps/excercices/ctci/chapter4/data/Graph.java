package aux.ps.excercices.ctci.chapter4.data;


import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Graph {

    private List<Node> nodes;

    Graph(List<Node> nodes) {
        this.nodes = nodes;
    }

    public static class Node {
        public final int id;
        public final List<Node> nodes;

        public Node(int id) {
            this.id = id;
            nodes = new ArrayList<>();
        }

        private void addNode(Node n) {
            nodes.add(n);
        }

        public boolean isNeighbourOf(int id) {
            return nodes.stream().anyMatch(n -> n.id == id);
        }


        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", nodes=" +
                    nodes.stream().map(n -> n.id)
                            .map(i -> "" + i)
                            .collect(joining(",")) +
                    '}';
        }
    }


    public Node node(int id) {
        return this.nodes.stream()
                .filter(n -> n.id == id)
                .findFirst().get();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" +
                nodes.stream()
                        .map(Node::toString)
                        .collect(joining("\n"))
                +
                '}';
    }

    public static Graph create(int size,
                               List<Pair<Integer, Integer>> edges) {

        List<Node> nodes = IntStream.range(1, size + 1)
                .mapToObj(Node::new)
                .collect(toList());

        edges.forEach(t -> {
            Node n1 = nodes.get(t.getFirst() - 1);
            Node n2 = nodes.get(t.getSecond() - 1);
            n1.addNode(n2);
            n2.addNode(n1);
        });

        return new Graph(nodes);
    }


}
