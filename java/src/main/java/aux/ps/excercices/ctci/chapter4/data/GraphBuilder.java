package aux.ps.excercices.ctci.chapter4.data;


import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphBuilder {

    private int size;
    private List<Pair<Integer, Integer>> edges;

    private GraphBuilder(int size) {
        this.size = size;
        edges = new ArrayList<>();
    }

    public static GraphBuilder graphOfSize(int size) {
        return new GraphBuilder(size);
    }

    public GraphBuilder connect(int fst, int... others) {
        Arrays.stream(others)
                .peek(o -> {
                    if (o > size)
                        throw new IllegalArgumentException("Id " + o + " cannot be in graph of size " + size);
                })
                .forEach(o -> edges.add(new Pair<>(fst, o)));
        return this;
    }


    public Graph build() {
        return Graph.create(size, edges);
    }


}
