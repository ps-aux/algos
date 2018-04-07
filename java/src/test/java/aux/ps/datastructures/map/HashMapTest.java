package aux.ps.datastructures.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashMapTest implements MapSpec {

    @Override
    public <V> Map<String, V> create() {
        return new HashMap<>();
    }

    @Test
    void sameHashCodeKeys() {
        Obj o1 = new Obj(123, "a");
        Obj o2 = new Obj(123, "b");

        Map<Obj, String> m = new HashMap<>();

        m.put(o1, o1.val);
        m.put(o2, o2.val);

        assertEquals(2, m.size());
        assertEquals(o1.val, m.get(o1));
        assertEquals(o2.val, m.get(o2));
    }


    private static class Obj {

        int hashCode;
        String val;

        Obj(int hashCode, String val) {
            this.hashCode = hashCode;
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != Obj.class)
                return false;
            Obj other = (Obj) o;

            return hashCode == other.hashCode &&
                    val.equals(other.val);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }

}
