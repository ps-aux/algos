package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

interface RotateStringSpec {

    boolean isRotationOf(String a, String b);

    @ParameterizedTest(name = "{0} is rotation to {1}")
    @CsvSource({
            "erbottlewat,waterbottle",
            "abcde,deabc",
            "a,a",
            "'',''"
    })
    default void is(String a, String b) {
        assertThat(isRotationOf(a, b)).isTrue();
    }

    @ParameterizedTest(name = "{0} is not rotation to {1}")
    @CsvSource({
            "rbottlewat,waterbottle",
    })
    default void isNot(String a, String b) {
        assertThat(isRotationOf(a, b)).isFalse();
    }


}


class RotateStringImpl implements RotateStringSpec {

    @Override
    public boolean isRotationOf(String a, String b) {
        if (a.length() != b.length())
            return false;

        if (a.isEmpty())
            return true;

        var l = a.length();

        var offset = 0;
        while (offset < l) {
            if (a.charAt(offset) == b.charAt(0)) {
                var i = 0;
                var j = offset;
                while (true) {
                    // We found difference. Continue with outer.
                    if (b.charAt(i) != a.charAt(j))
                        break;

                    // We checked the whole string
                    if (i == l - 1)
                        return true;

                    i++;
                    // Rotate
                    j = j == l - 1 ? 0 : j + 1;
                }
            } else {
                offset++;
            }
        }

        return false;
    }
}


class RotateStringImpl2 implements RotateStringSpec {

    /**
     * for every string with len > 0
     * s = ab where a,b are some substrings
     * <p>
     * rotation of s  rs is then
     * rs = ba
     * <p>
     * therefore
     * <p>
     * ss = abab and rs  must be in ss (because ba is in ss)
     */
    @Override
    public boolean isRotationOf(String a, String b) {
        if (a.length() != b.length())
            return false;

        return (b + b).contains(a);

    }
}



