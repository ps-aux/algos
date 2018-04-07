package aux.ps.datastructures.graph;

import java.util.Collection;
import java.util.List;

public interface Graph {

    Vertex getVertex(long id);

    void add(Vertex v);

    void connect(long source, long... targets);

    void connectWeighted(long source, long target, int weight);

    boolean isEdge(long v1, long v2);

    Collection<Vertex> getVertices();

    boolean hasPath(long from, long to);

    boolean dfsHasPath(long from, long to);

    List<Long> shortestPath(long from, long to);

    List<Long> shortestWeightedPath(long from, long to);

}
