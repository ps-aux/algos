package aux.ps.datastructures.heap;

public class HeapImplTest implements HeapSpec {


    @Override
    public Heap<Integer, String> create() {
        return new HeapImpl<>(Integer::compareTo);
    }
}
