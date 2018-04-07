package aux.ps.datastructures.queue;


public class LinkedListQueueTest implements QueueSpec {

    @Override
    public <T> Queue<T> create() {
        return new LinkedListQueue<>();
    }
}
