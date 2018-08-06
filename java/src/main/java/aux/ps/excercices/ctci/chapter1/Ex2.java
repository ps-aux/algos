package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


interface IsPermutationSpec {

    boolean isPermutation(String a, String b);

    @ParameterizedTest(name = "{0} is permutation of {1}")
    @CsvSource({
            "abc, bca",
            "aba, baa"
    })
    default void is_permutation(String a, String b) {
        assertThat(isPermutation(a, b)).isTrue();

    }

    @ParameterizedTest(name = "{0} is not permutation of {1}")
    @CsvSource({
            "abc, bcd",
            "abc, efg",
            "aaa, aaaa"
    })
    default void is_not_permutation(String a, String b) {
        assertThat(isPermutation(a, b)).isFalse();
    }


}


class MapSolution implements IsPermutationSpec {

    @Override
    public boolean isPermutation(String a, String b) {
        if (a.length() != b.length())
            return false;

        Map<Character, Integer> map = new HashMap<>();

        for (var c : b.toCharArray()) {
            var count = map.get(c);
            if (count == null)
                map.put(c, 1);
            else
                map.put(c, count + 1);

        }

        for (var c : a.toCharArray()) {
            var count = map.get(c);
            if (count == null || count == 0)
                return false;
            map.put(c, count - 1);
        }


        return true;
    }
}

class OrderingSolution implements IsPermutationSpec {


    private String sort(String s) {
        var chars = s.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }

    @Override
    public boolean isPermutation(String a, String b) {
        if (a.length() != b.length())
            return false;

        return sort(a).equals(sort(b));
    }
}
