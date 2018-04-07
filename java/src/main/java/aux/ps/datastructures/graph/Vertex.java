package aux.ps.datastructures.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Vertex {

    private Map<Long, Integer> neighbours = new HashMap<>();

    private final long id;

    public Vertex(long id) {
        this.id = id;
    }

    public void connectTo(long id, int weight) {
        neighbours.put(id, weight);

    }

    public long getId() {
        return id;
    }


    public Collection<Long> getNeighbours() {
        return neighbours.keySet();
    }

    public Map<Long, Integer> getWeightedNeighbours() {
        return Collections.unmodifiableMap(neighbours);
    }

    boolean hasNeighbour(long id) {
        return neighbours.containsKey(id);
    }

    public int getNeighbourCount() {
        return neighbours.size();
    }
}
