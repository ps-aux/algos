package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


interface IsPalindromeSpec {

    boolean isPalindrome(Node list);


    @ParameterizedTest(name = "{0} is palindrome ? {1}")
    @ArgumentsSource(Data.class)
    default void test(Node list, boolean expected) {
        var res = isPalindrome(list);

        if (expected) {
            assertThat(res).isTrue();
        } else {
            assertThat(res).isFalse();
        }
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {


            return Stream.of(
                    Arguments.of(
                            of(1, 2, 1),
                            true),
                    Arguments.of(
                            of(1, 1, 1),
                            true),
                    Arguments.of(
                            of(1),
                            true),
                    Arguments.of(
                            of(1, 2),
                            false),
                    Arguments.of(
                            of(1, 2, 3),
                            false)
            );
        }
    }

}


class IsPalindrome implements IsPalindromeSpec {


    Node findLast(Node n) {
        return n.next == null ? n : findLast(n.next);
    }


/*
    boolean isPalindrome(Node start, Node end) {
        if (start == end)
            return true;
        if (start.data != end.data)
            return false;
        return is

    }
*/

    @Override
    public boolean isPalindrome(Node list) {
        Map<Integer, Integer> map = new HashMap<>();

        Node last = findLast(list);

        Node n = list;
        boolean odd;
        while (n.next != null) {
            var count = map.get(n.data);
            if (count == null)
                map.put(n.data, 1);
            else
                map.put(n.data, count + 1);
//            size++;
            n = n.next;
        }


        return false;
    }
}

