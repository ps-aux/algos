package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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


class First implements IsPermutationSpec {

    @Override
    public boolean isPermutation(String a, String b) {
        if (a.length() != b.length())
            return false;
        return false;
    }
}
