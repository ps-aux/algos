package aux.ps.datastructures.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SortSpec {

    int[] sort(int[] array);

    default int[] array() {
        return new int[]{
                5, 7, 9, 1, 3, 5, 7, 8, 9, 8
        };
    }

    @Test
    default void isSorted() {

        int[] sorted = sort(array());

        for (int i = 0; i < sorted.length - 2; i++) {
            assertTrue(sorted[i] < sorted[i + 1]);
        }
    }

    @Test
    default void hasSameContent() {

        int[] orig = array();
        int[] sorted = sort(array());

        assertTrue(orig.length == sorted.length);

        Arrays.sort(orig);
        assertArrayEquals(orig, sorted);

    }

}
