package aux.ps.datastructures.graph;

public class GraphImplTest implements GraphSpec {

    public Graph create(long... ids) {
        return GraphImpl.of(ids);
    }

}
