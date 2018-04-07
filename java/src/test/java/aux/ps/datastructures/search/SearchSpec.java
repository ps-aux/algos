package aux.ps.datastructures.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface SearchSpec {

    int search(int val, int[] array);

    default int[] array() {
        return new int[]{
                5, 7, 9, 1, 3, 5, 7, 8, 9, 8
        };
    }

    @Test
    default void findsIndex() {

        int[] array = new int[]{5, 7, 9, 1, 3, 5, 10, 8};

        assertEquals(search(5, array), 1);
        assertEquals(search(8, array), array.length - 1);
        assertEquals(search(3, array), 4);
    }

    @Test
    default void properlyReportsNotFound() {

        int[] array = new int[]{5, 10, 8};

        assertEquals(search(999, array), -1);
    }

}
