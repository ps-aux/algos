package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

interface IsOneEditAwaySpec {

    boolean isOneEditAwayFrom(String a, String b);

    @ParameterizedTest(name = "{0} is one edit away from {1}")
    @CsvSource({
            "pales,pale",
            "opale,pale",
            "paxle,pale",
            "bale,pale",
            "pxle,pale",
            "a,''",
            "'',a",
            "pale,pales",
            "aaa,aaab",
    })
    default void is(String a, String b) {
        assertThat(isOneEditAwayFrom(b, a)).isTrue();
    }

    @ParameterizedTest(name = "{0} is not one edit away from {1}")
    @CsvSource({
            "abc,a",
            "'',ab",
            "ab,''",
            "abc,xx",
            "pale,bake"
    })
    default void isNot(String a, String b) {
        assertThat(isOneEditAwayFrom(b, a)).isFalse();
    }

}


// TODO try to merge both methods into one loop
class IsOneEditAwayImpl implements IsOneEditAwaySpec {


    boolean isRemovedChar(String a, String b) {
        // A must be shoter by one char from b
        if (a.length() != b.length() - 1)
            return false;

        boolean diffFound = false;
        var j = 0;
        for (int i = 0; i < b.length(); i++) {
            // We hit the last char in the shorter string.
            if (i == a.length())
                break;

            if (a.charAt(j) != b.charAt(i)) {
                if (diffFound)
                    // Found another diff
                    return false;

                // Diff found. We assume it is the deleted char in a.
                diffFound = true;
            } else {
                j++;
            }

        }

        return true;
    }


    boolean isReplacedChar(String a, String b) {
        if (a.length() != b.length())
            return false;

        boolean diffFound = false;
        for (int i = 0; i < b.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (diffFound)
                    // Found another diff
                    return false;
                diffFound = true;
            }

            // Diff found. We assume it is the replaced char.

        }

        return diffFound;
    }

    boolean isInsertedChar(String a, String b) {
        return isRemovedChar(b, a);
    }

    /**
     *  time: O(n)
     *  space: O(1)
     */
    @Override
    public boolean isOneEditAwayFrom(String a, String b) {
        return isRemovedChar(a, b) || isReplacedChar(a, b) || isInsertedChar(a, b);
    }
}
