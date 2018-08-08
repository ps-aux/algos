package aux.ps.excercices.ctci.chapter1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

interface CompressStringSpec {

    String compressString(String s);

    @ParameterizedTest(name = "{0} is compressed to {1}")
    @CsvSource({
            "aaabbbccc,a3b3c3",
            "aaaabc,aaaabc",
            "aaaaabc,a5b1c1",
            "'',''"
    })
    default void test(String a, String b) {
        assertThat(compressString(a)).isEqualTo(b);
    }


}


class CompressStringSpecImpl implements CompressStringSpec {


    @Override
    public String compressString(String s) {
        if (s.isEmpty())
            return s;

        StringBuilder out = new StringBuilder();
        char current = s.charAt(0);
        int count = 0;


        for (char c : s.toCharArray()) {
            if (c != current) {
                out.append(current).append(count);
                current = c;
                count = 1;
            } else {
                count++;
            }
        }
        // Output last
        out.append(current).append(count);

        var res = out.toString();

        if (res.length() >= s.length())
            return s;

        return res;
    }
}
