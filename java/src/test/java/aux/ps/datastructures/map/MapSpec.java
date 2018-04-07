package aux.ps.datastructures.map;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface MapSpec {

    <V> Map<String, V> create();

    @Test
    default void size() {
        Map<String, String> m = create();
        assertEquals(0, m.size());

        m.put("key", "val");
        assertEquals(1, m.size());

        m.remove("key");
        assertEquals(0, m.size());
    }

    @Test
    default void putAndGet() {
        Map<String, String> m = create();

        m.put("key", "val");
        m.put("other", "other-val");
        assertEquals("val", m.get("key"));
    }

    @Test
    default void getOnNonExistentReturnsNull() {
        Map<String, String> m = create();
        assertEquals(null, m.get("key"));
    }

    @Test
    default void doublePutAndGet() {
        Map<String, String> m = create();

        m.put("key", "val1");
        m.put("key", "val2");
        assertEquals("val2", m.get("key"));
    }

    @Test
    default void remove() {
        Map<String, String> m = create();

        m.put("key", "val");
        m.remove("key");
        assertEquals(null, m.get("key"));
        // Remove is idempotent operation
        m.remove("key");
    }

    @Test
    default void putAndGetManyTimes() {
        Map<String, String> m = create();
        int times = 1000;
        IntStream.range(0, times)
                .forEach(i ->
                        m.put("key-" + i, "val-" + i));

        assertEquals(times, m.size());
        IntStream.range(0, times)
                .forEach(i ->
                        assertEquals("val-" + i, m.get("key-" + i)));

    }
}
