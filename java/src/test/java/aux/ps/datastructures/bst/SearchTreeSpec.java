package aux.ps.datastructures.bst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface SearchTreeSpec {

    <K extends Comparable<K>, V> SearchTree<K, V> create();

    @Test
    default void find() {
        SearchTree<Integer, String> st = create();

        st.insert(1, "a");
        st.insert(2, "a");
        st.insert(100, "c");
        assertEquals("c", st.find(100));

    }

    @Test
    default void size() {
        SearchTree<Integer, String> st = create();

        assertEquals(0, st.size());
        st.insert(1, "a");
        assertEquals(1, st.size());
        st.delete(1);
        assertEquals(0, st.size());
    }

    @Test
    default void delete() {
        SearchTree<Integer, String> st = create();
        // Non-existent
        assertEquals(null, st.find(123));

        st.insert(1, "any");
        st.delete(1);
        assertEquals(null, st.find(1));
        // Idempotent op
        st.delete(1);
        assertEquals(null, st.find(1));
        assertEquals(0, st.size());
    }

}
