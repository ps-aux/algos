package aux.ps.datastructures.graph;

import aux.ps.datastructures.stack.LinkedListStack;
import aux.ps.datastructures.stack.Stack;

import java.util.*;
import java.util.Map.Entry;

public class DijkstraPathFinder {

    private final Long s;
    private final Long t;
    private final Vertex sVertex;
    private final Map<Long, Vertex> vertices;

    private final Set<Long> visited = new HashSet<>();
    private final Map<Long, Long> pathFrom = new HashMap<>();
    private final Map<Long, Integer> distances = new HashMap<>();

    DijkstraPathFinder(Long s, Long t, Map<Long, Vertex> vertices) {
        this.s = s;
        this.sVertex = vertices.get(s);
        this.t = t;
        this.vertices = vertices;
    }

    List<Long> find() {
        vertices.keySet()
                .forEach(id ->
                        distances.put(id, Integer.MAX_VALUE));

        // Initial
        distances.put(s, 0);

        long next;
        while (true) {
            next = next();
            visited.add(next);
            if (next == t)
                return constructPath();

            Vertex v = vertices.get(next);

            for (Entry<Long, Integer> n : v.getWeightedNeighbours().entrySet()) {
                long id = n.getKey();

                if (visited.contains(id))
                    continue;

                // Distance to neighbour via `next` vertex
                int d = distances.get(next) + n.getValue();
                if (d < distances.get(id)) {

                    // New distance is smaller - update
                    distances.put(id, d);
                    pathFrom.put(id, next);
                }

            }

        }
    }


    private List<Long> constructPath() {
        Stack<Long> reversePath = new LinkedListStack<>();
        Long next = t;
        do {
            reversePath.push(next);
            next = pathFrom.get(next);
        } while (next != null);


        List<Long> path = new ArrayList<>();
        while (!reversePath.isEmpty())
            path.add(reversePath.pop());

        return path;
    }

    // Should be O(log) n structure
    // THis is O(n) for now (to lazy to find updetable priority queue in Java)
    private Long next() {
        return distances.entrySet()
                .stream()
                .filter(e -> !visited.contains(e.getKey()))
                .min(Comparator.comparing(Entry::getValue))
                .get().getKey();
    }
}
