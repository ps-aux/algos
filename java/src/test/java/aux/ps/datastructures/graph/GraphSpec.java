package aux.ps.datastructures.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

public interface GraphSpec {

    Graph create(long... ids);

    @Test
    default void addVertex() {
        Graph g = create();
        g.add(new Vertex(123));

        assertEquals(1, g.getVertices().size());
        assertEquals(123, g.getVertices().iterator().next().getId());
        assertEquals(123, g.getVertex(123).getId());

    }

    @Test
    default void addIsIdempotent() {
        Graph g = create();
        g.add(new Vertex(123));
        g.add(new Vertex(123));

        assertEquals(1, g.getVertices().size());
    }

    @Test
    default void connect() {
        Graph g = create(1, 2, 3);

        g.connect(1, 2, 3);

        assertTrue(g.isEdge(1, 2));
        assertTrue(g.isEdge(2, 1));

        assertTrue(g.isEdge(1, 3));
        assertTrue(g.isEdge(3, 1));

        assertFalse(g.isEdge(2, 3));

        assertEquals(2, g.getVertex(1).getNeighbourCount());
        assertEquals(1, g.getVertex(2).getNeighbourCount());
        assertEquals(1, g.getVertex(3).getNeighbourCount());
    }

    @Test
    default void connectIsIdempotent() {
        Graph g = create(1, 2);

        g.connect(1, 2);
        g.connect(1, 2);

        assertEquals(1, g.getVertex(1).getNeighbourCount());
        assertEquals(1, g.getVertex(2).getNeighbourCount());

    }


    @Test
    default void unconnectedVerticesDontHavePath() {
        Graph g = create(1, 2, 3);

        assertFalse(g.hasPath(1, 2));
        assertFalse(g.hasPath(2, 3));
        assertFalse(g.hasPath(3, 2));
        assertFalse(g.hasPath(1, 3));
    }

    @Test
    default void connectedVerticesHavePath() {
        Graph g = create(1, 2, 3, 4, 5, 6);
        assertFalse(g.hasPath(1, 2));

        g.connect(1, 2);
        g.connect(2, 3);
        g.connect(1, 4);
        g.connect(5, 6);

        assertTrue(g.hasPath(1, 2));
        assertTrue(g.hasPath(1, 3));
        assertTrue(g.hasPath(4, 3));

        // Redundant tests
        assertTrue(g.hasPath(1, 4));
        assertTrue(g.hasPath(2, 4));

        assertFalse(g.hasPath(1, 5));
        assertTrue(g.hasPath(5, 6));
    }

    @Test
    default void bfsConnected() {
        Graph g = create(1, 2, 3, 4, 5, 6);
        assertFalse(g.dfsHasPath(1, 2));

        g.connect(1, 2);
        g.connect(2, 3);
        g.connect(1, 4);
        g.connect(5, 6);

        assertTrue(g.dfsHasPath(1, 2));
        assertTrue(g.dfsHasPath(1, 3));
        assertTrue(g.dfsHasPath(4, 3));

        // Redundant tests
        assertTrue(g.dfsHasPath(1, 4));
        assertTrue(g.dfsHasPath(2, 4));

        assertFalse(g.dfsHasPath(1, 5));
        assertTrue(g.dfsHasPath(5, 6));
    }

    @Test
    default void notConnectedToNonExistent() {
        Graph g = create(1, 2);
        g.connect(1, 2);

        // Test for cycles
        g.connect(1, 2);
        assertTimeoutPreemptively(ofSeconds(5),
                () -> assertFalse(g.hasPath(1, 100)));
    }

    @Test
    default void shortestPath() {
        Graph g = create(1, 2, 3, 4, 5, 6);

        /*       2 -- 6
        *       /   / |
        *      1   4  |
        *       \ /   |
        *        3 -- 5
        */

        g.connect(1, 2);
        g.connect(2, 6);

        g.connect(1, 3);
        g.connect(3, 5);
        g.connect(5, 6);


        g.connect(3, 4);
        g.connect(4, 6);

        List<Long> path = g.shortestPath(1, 6);
        Iterator<Long> it = path.iterator();

        Long[] shortest = {1L, 2L, 6L};

        Arrays.stream(shortest)
                .forEach(s -> {
                    assertEquals(s, it.next());
                });
    }

    @Test
    default void shortestWeighedPath() {
        Graph g = create(1, 2, 3, 4, 5, 6);

        /*    2 --(2)-- 6
        *    /          |
        *  (10)        (1)
        *  /            |
        * 1 ---(10)---- 5
        *  \            |
        *  (1)         (2)
        *    \          |
        *     3 --(1)-- 4
        * Shortest : 1 -> 3 -> 4 -> 6
        */


        g.connectWeighted(1, 2, 10);
        g.connectWeighted(2, 6, 2);

        g.connectWeighted(1, 5, 10);
        g.connectWeighted(5, 6, 1);


        g.connectWeighted(1, 3, 1);
        g.connectWeighted(3, 4, 1);
        g.connectWeighted(4, 5, 1);
        g.connectWeighted(5, 6, 1);

        List<Long> path = g.shortestWeightedPath(1, 6);
        Iterator<Long> it = path.iterator();

        Long[] shortest = {1L, 3L, 4L, 5L, 6L};

        Arrays.stream(shortest)
                .forEach(s -> {
                    assertEquals(s, it.next());
                });
    }

}
