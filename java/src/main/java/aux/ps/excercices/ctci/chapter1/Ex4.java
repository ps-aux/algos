package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


interface IsPalindromePermutationSpec {

    boolean isPalindromPermutation(String a);

    @ParameterizedTest(name = "{0} is permutation of palindrome")
    @CsvSource({
            "atco cta",
            "bba",
            "x",
            "''",
            "' '"
    })
    default void is(String s) {
        assertThat(isPalindromPermutation(s)).isTrue();
    }

    @ParameterizedTest(name = "{0} is not permutation of palindrome")
    @CsvSource({
            "atcoe cta",
            "bbac",
            "y u",
            "abc"
    })
    default void isNot(String s) {
        assertThat(isPalindromPermutation(s)).isFalse();
    }


}


class FirstIsPalindromePermutation implements IsPalindromePermutationSpec {

    /**
     * time: O(n)
     * space: O(m) , m = size of alphabet
     */
    @Override
    public boolean isPalindromPermutation(String a) {
        int[] counts = new int[128];

        for (var c : a.toCharArray()) {
            if (c == ' ')
                continue;
            counts[c] = counts[c] + 1;
        }

        var hasOdd = false;

        for (var c : counts) {
            if (c % 2 == 1) {
                if (hasOdd)
                    return false;
                hasOdd = true;
            }
        }

        return true;
    }
}
