package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.LinkedListUtils.size;
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
                            of(1, 2, 2, 1),
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


class LinkedListUtils {

    static int size(Node n, int acc) {
        if (n == null)
            return acc;
        return size(n.next, acc + 1);
    }

    static int size(Node n) {
        return size(n, 0);
    }
}

class IsPalindrome implements IsPalindromeSpec {


    private static class Res {
        Node last;
        boolean is;
    }

    Res isPalindrome(Node n, int levelsLeft, boolean isOdd) {

        if (levelsLeft == 1) {
            Res res = new Res();

            if (isOdd) {
                res.is = true;
                res.last = n;
            } else {
                res.is = n.data == n.next.data;
                res.last = n.next;
            }
            return res;
        }

        Res r = isPalindrome(n.next, levelsLeft - 1, isOdd);
        Node last = r.last.next;

        Res res = new Res();
        res.is = r.is && n.data == last.data;
        res.last = last;

        return res;
    }

    @Override
    public boolean isPalindrome(Node list) {
        int size = size(list);
        int levels = (size + 1) / 2;
        return isPalindrome(list, levels, size % 2 == 1).is;
    }
}

class IsPalindromeReversedImpl implements IsPalindromeSpec {


    Node reverse(Node n, Node next) {
        Node newN = new Node(n.data);
        newN.next = next;

        // End
        if (n.next == null) {
            return newN;
        }

        return reverse(n.next, newN);
    }


    @Override
    public boolean isPalindrome(Node list) {
        Node reversed = reverse(list, null);

        Node p1 = list;
        Node p2 = reversed;
        while (p1 != null) {
            if (p1.data != p2.data)
                return false;
            p1 = p1.next;
            p2 = p2.next;
        }


        return true;
    }
}

class IsPalindromeStackImpl implements IsPalindromeSpec {


    @Override
    public boolean isPalindrome(Node list) {
        Deque<Node> stack = new LinkedList<>();

        Node slowP = list;
        Node fastP = list;
        while (fastP != null && fastP.next != null) {
            stack.push(slowP);
            slowP = slowP.next;
            fastP = fastP.next.next;
        }


        // Odd number. Slow pointer is middle node.
        if (fastP != null) {
            slowP = slowP.next;
        }

        Node n = slowP;

        assert stack.size() == size(n);

        while (!stack.isEmpty()) {
            if (stack.pop().data != n.data)
                return false;
            n = n.next;
        }


        return true;
    }
}

