package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.Utils.nth;
import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.Java6Assertions.assertThat;


interface LoopDetectionSpec {

    Node detectLoop(Node list);

    @ParameterizedTest(name = "{0} has loop ? {1}")
    @ArgumentsSource(Data.class)
    default void test(Node list, Node res) {
        assertThat(detectLoop(list)).isSameAs(res);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            // Whole loop
            Node a = of(1, 2, 3, 4);
            nth(a, 3).next = a;
            Node aRes = a;

            // In the part of list
            Node b = of(1, 2, 3, 4);
            Node bRes = nth(b, 2);
            nth(b, 3).next = bRes;


            // No loop
            Node c = of(1, 2, 3, 4);
            Node cRes = null;


            return Stream.of(
                    Arguments.of(a, aRes),
                    Arguments.of(b, bRes),
                    Arguments.of(c, cRes));
        }
    }
}


class LoopDetectionImpl implements LoopDetectionSpec {


    @Override
    public Node detectLoop(Node list) {
        Node slow = list;
        Node fast = list;
        int hops = 0;
        int firstMeet = -1;
        boolean hasMet = false;

        while (slow != null && fast != null) {
            slow = slow.next;
            hops++;
            fast = fast.next;
            if (fast == null) {
                return null;
            } else {
                fast = fast.next;
            }

            if (slow == fast) {
                if (!hasMet) {
                    firstMeet = hops;
                } else {
                    int loopLength = hops - firstMeet;
                    System.out.println("len " + loopLength);
                    int loopStart = firstMeet - (loopLength - 1);
                    System.out.println("start " + loopStart);
                    return nth(list, loopStart);
                }
                hasMet = true;
            }
        }

        return null;
    }
}



