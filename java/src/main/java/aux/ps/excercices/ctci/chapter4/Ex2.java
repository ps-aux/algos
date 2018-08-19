package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.log;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


interface MinimalTreeSpec {

    Node minimalTree(int[] array);


    @ParameterizedTest(name = "{0} has minimal length")
    @ArgumentsSource(Data.class)
    default void test(int[] array) {
        var res = minimalTree(array);

        var len = array.length;
        int expected = (int) (log(len) / log(2)) + 1;

        assertThat(res.height()).isEqualTo(expected);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    of(IntStream.range(0, 10).toArray()),
                    of(IntStream.range(0, 2).toArray()),
                    of(IntStream.range(0, 100).toArray())
            );
        }
    }

}


/**
 * TODO reimplement in cleaner way (as in solutions)
 */
class MinimalTreeImpl implements MinimalTreeSpec {

    private static class Split {
        int pivot;
        int[] left;
        int[] right;

    }

    Split split(int[] arr) {
        assert arr.length > 2;
        Split s = new Split();
        int pivot = arr.length / 2; // +1 - 1
        s.pivot = arr[pivot];
        // Assume this is returns view (i.e. makes left and right in contant time)
        s.left = Arrays.copyOfRange(arr, 0, pivot);
        s.right = Arrays.copyOfRange(arr, pivot + 1, arr.length);

        return s;
    }

    @Override
    public Node minimalTree(int[] array) {
        var inserts = new LinkedList<Integer>();
        minimalTree(inserts, array);

        var n = new Node(inserts.removeFirst());

        inserts.forEach(n::add);

        return n;
    }

    public void minimalTree(Deque<Integer> inserts, int[] array) {
        if (array.length < 3) {
            Arrays.stream(array).forEach(inserts::add);
            return;
        }
        var s = split(array);
        inserts.add(s.pivot);
        minimalTree(inserts, s.left);
        minimalTree(inserts, s.right);
    }

}


