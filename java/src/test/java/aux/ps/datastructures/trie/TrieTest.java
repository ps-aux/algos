package aux.ps.datastructures.trie;

import aux.ps.datastructures.map.Map;
import aux.ps.datastructures.map.MapSpec;

public class TrieTest implements MapSpec {

    @Override
    public <V> Map<String, V> create() {
        return new Trie<>();
    }
}
