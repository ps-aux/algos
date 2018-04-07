package aux.ps.datastructures.stack;

public class LinkedListStackTest implements StackSpec {

    public <T> Stack<T> create() {
        return new LinkedListStack<>();
    }

}
