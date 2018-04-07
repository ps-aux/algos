package aux.ps.datastructures.list;


public class ArrayListTest implements ListSpec {

    @Override
    public <T> List<T> create(T... ts) {
        return ArrayList.of(ts);
    }
}
