package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.assertj.core.api.Assertions.assertThat;


interface UrlifySpec {

    void urlify(char[] s);

    @ParameterizedTest(name = "urlify {0} == {1}")
    @CsvSource({
            "'Mr John Smith    ',Mr%20John%20Smith",
            "'   ',%20",
            "'abc   ',abc%20",
            "abc,abc",
            "'',''"
    })
    default void test(String a, String b) {
        var chars = a.toCharArray();
        urlify(chars);

        assertThat(b.toCharArray()).isEqualTo(chars);
    }

}


class FirstUrlify implements UrlifySpec {

    void moveBy(char[] s, int idx, int offset) {
        var to = s.length - 1;

        for (int i = to; i >= idx + offset; i--) {
            s[i] = s[i - offset];
        }
    }

    void insert(char[] s1, int idx, char[] s2) {
        for (int i = 0; i < s2.length; i++) {
            s1[i + idx] = s2[i];
        }

    }

    void replace(char[] s, char what, char[] with) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] == what) {
                System.out.println(String.valueOf(s));
                moveBy(s, i, with.length - 1);
                insert(s, i, with);
                // Move to the end of replaced part
                i = i + with.length - 1;
            }
        }
    }

    @Override
    public void urlify(char[] s) {
        replace(s, ' ', "%20".toCharArray());
    }
}
