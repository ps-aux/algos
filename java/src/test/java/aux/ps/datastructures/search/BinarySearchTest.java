package aux.ps.datastructures.search;

public class BinarySearchTest implements SearchSpec {

    @Override
    public int search(int val, int[] array) {
        return new BinarySearch().search(val, array);
    }
}
