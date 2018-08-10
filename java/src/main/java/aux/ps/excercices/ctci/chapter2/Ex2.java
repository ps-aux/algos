package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.Assertions.assertThat;


interface KthToLastSpec {

    int kthToLast(Node n, int k);

    @ParameterizedTest(name = "{1}th to last in {0} is {2}")
    @ArgumentsSource(Data.class)
    default void test(Node a, int k, int x) {
        assertThat(kthToLast(a, k)).isEqualTo(x);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            Node n = of(1, 2, 3, 4);
            return Stream.of(
                    Arguments.of(
                            n, 2, 2),
                    Arguments.of(
                            n, 0, 4));
        }
    }

}


class KthToLastImpl implements KthToLastSpec {

    @Override
    public int kthToLast(Node n, int k) {
        int size = 0;
        var p = n;
        while (p.next != null) {
            size++;
            p = p.next;
        }

        int idx = size - k;
        int i = 0;
        p = n;
        while (true) {
            if (i == idx)
                return p.data;
            p = p.next;
            i++;
        }
    }
}

