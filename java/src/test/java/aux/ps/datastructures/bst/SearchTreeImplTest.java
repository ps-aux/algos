package aux.ps.datastructures.bst;

public class SearchTreeImplTest implements SearchTreeSpec {

    @Override
    public <K extends Comparable<K>, V> SearchTree<K, V> create() {
        return new SearchTreeImpl<>();
    }
}
