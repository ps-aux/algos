package aux.ps.excercices.ctci.chapter3.data;

import java.util.Deque;

public class Utils {
    public static void restack(Deque from, Deque to) {
        while (!from.isEmpty())
            to.push(from.pop());
    }

    public static void restack(Stack from, Stack to) {
        while (!from.isEmpty())
            to.push(from.pop());
    }
}
