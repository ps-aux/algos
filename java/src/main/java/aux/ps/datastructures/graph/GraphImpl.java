package aux.ps.datastructures.graph;


import aux.ps.datastructures.queue.LinkedListQueue;
import aux.ps.datastructures.queue.Queue;
import aux.ps.datastructures.stack.LinkedListStack;
import aux.ps.datastructures.stack.Stack;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class GraphImpl implements Graph {

    private final Map<Long, Vertex> vertices = new HashMap<>();

    @Override
    public Vertex getVertex(long id) {
        return vertices.get(id);
    }

    @Override
    public void add(Vertex v) {
        vertices.put(v.getId(), v);
    }

    @Override
    public void connect(long source, long... targets) {
        if (!vertices.containsKey(source))
            throw new NoSuchElementException("Vertex " + source);

        Arrays.stream(targets)
                .forEach(t -> {
                    connectWeighted(source, t, 1);
                });

    }

    @Override
    public void connectWeighted(long source, long target, int weight) {
        Vertex s = vertices.get(source);
        Vertex t = vertices.get(target);

        if (s == null)
            throw new NoSuchElementException("Vertex " + source);

        if (t == null)
            throw new NoSuchElementException("Vertex " + target);

        s.connectTo(target, weight);
        t.connectTo(source, weight);
    }


    @Override
    public boolean isEdge(long v1, long v2) {
        Vertex v = vertices.get(v1);
        if (v == null)
            throw new NoSuchElementException("Vertex " + v1);

        return v.hasNeighbour(v2);
    }

    @Override
    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    @Override
    public boolean hasPath(long from, long to) {
        return shortestPath(from, to) != null;
    }

    @Override
    public boolean dfsHasPath(long from, long to) {
        return dfsHasPathInternal(from, to, new HashSet<>());
    }

    @Override
    public List<Long> shortestWeightedPath(long from, long to) {
        if (!vertices.containsKey(from))
            throw new NoSuchElementException("Vertex " + from);
        if (!vertices.containsKey(to))
            throw new NoSuchElementException("Vertex " + to);

        return new DijkstraPathFinder(from, to, vertices).find();
    }

    private boolean stackDfs(long from, long to) {
        Stack<Long> stack = new LinkedListStack<>();
        Set<Long> visited = new HashSet<>();
        Predicate<Long> isVisited = visited::contains;

        stack.push(from);
        while (!stack.isEmpty()) {
            long next = stack.pop();
            if (next == to)
                return true;
            visited.add(next);

            vertices.get(next)
                    .getNeighbours().stream()
                    .filter(isVisited.negate())
                    .forEach(stack::push);
        }

        return false;

    }

    private boolean dfsHasPathInternal(long from,
                                       long to,
                                       Set<Long> visited) {
        if (from == to)
            return true;
        Predicate<Long> isVisited = visited::contains;

        visited.add(from);
        Collection<Long> unvisited = vertices.get(from)
                .getNeighbours().stream()
                .filter(isVisited.negate())
                .collect(Collectors.toList());


        for (Long n : unvisited) {
            if (dfsHasPathInternal(n, to, visited))
                return true;
        }

        return false;
    }


    @Override
    public List<Long> shortestPath(long from, long to) {
        Queue<Long> toVisit = new LinkedListQueue<>();
        toVisit.enqueque(from);

        Set<Long> visited = new HashSet<>();
        Map<Long, Long> visitedFrom = new HashMap<>();

        boolean found = false;
        while (!toVisit.isEmpty()) {
            long next = toVisit.dequeue();
            if (next == to) {
                found = true;
                break;
            }
            visited.add(next);
            Collection<Long> neighbours = vertices.get(next).getNeighbours();
            neighbours.forEach(id -> {
                if (!visited.contains(id)) {
                    visitedFrom.put(id, next);
                    toVisit.enqueque(id);
                }
            });
        }
        if (!found)
            return null;


        return constructPath(visitedFrom, to);
    }

    private List<Long> constructPath(Map<Long, Long> visitedFrom, long to) {
        Stack<Long> s = new LinkedListStack<>();

        Long last = to;
        while (true) {
            s.push(last);
            last = visitedFrom.get(last);
            if (last == null) {
                // This was the initial vertex
                break;
            }

        }

        List<Long> path = new ArrayList<>();
        while (!s.isEmpty())
            path.add(s.pop());

        return path;
    }

    public static Graph of(long... ids) {
        Graph g = new GraphImpl();
        Arrays.stream(ids)
                .mapToObj(Vertex::new)
                .forEach(g::add);

        return g;
    }

}
