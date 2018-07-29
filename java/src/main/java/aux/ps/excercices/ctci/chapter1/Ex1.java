package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


interface IsUniqueSpec {

    boolean isUnique(String s);

    @ParameterizedTest
    @ValueSource(strings = {
            "uniqe",
            "blautk",
            "abcdefghijklmnoprstu"
    })
    default void is_unique(String s) {
        assertThat(isUnique(s)).isTrue();

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "blabla",
            "qwertyq",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    })
    default void is_not_unique(String s) {
        assertThat(isUnique(s)).isFalse();
    }


}

class SetImpl implements IsUniqueSpec {

    /**
     * time:  O(n)
     * space: O(n)
     */
    public boolean isUnique(String s) {
        Set<Character> set = new HashSet<>();
        for (Character c : s.toCharArray()) {
            if (!set.add(c))
                return false;

        }
        return true;
    }

}

class RecursiveImpl implements IsUniqueSpec {

    /**
     * time:  O (n^2)
     * space: O(1)
     */
    public boolean isUnique(String s) {
        if (s.length() == 1)
            return true;
        char head = s.charAt(0);
        String tail = s.substring(1);

        if (tail.contains(String.valueOf(head)))
            return false;
        return isUnique(tail);
    }

}

class SortingImpl implements IsUniqueSpec {

    /**
     * time:  O (n log n)
     * space: O(n)
     */
    public boolean isUnique(String s) {
        char[] cs = s.toCharArray();
        Arrays.sort(cs);
        for (int i = 0; i < cs.length - 1; i++) {
            if (cs[i] == cs[i + 1])
                return false;

        }
        return true;
    }

}

