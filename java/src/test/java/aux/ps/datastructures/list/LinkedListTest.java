package aux.ps.datastructures.list;


public class LinkedListTest implements ListSpec {

    @Override
    public <T> List<T> create(T... ts) {
        return LinkedList.of(ts);
    }
}
