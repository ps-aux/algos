package aux.ps.datastructures.queue;

public class HeapPriorityQueueTest implements PriorityQueueSpec {

    @Override
    public PriorityQueue<Integer, String> create() {
        return new HeapPriorityQueue<>(Integer::compareTo);
    }
}
