package aux.ps.excercices.ctci.chapter2.data;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static aux.ps.excercices.ctci.chapter2.data.LinkedList.empty;
import static aux.ps.excercices.ctci.chapter2.data.LinkedList.of;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class Node {

    public Node next = null;
    public int data;

    public Node(int d) {
        data = d;
    }

    void appendToTail(int d) {
        Node end = new Node(d);
        Node n = this;
        while (n.next != null) {
            n = n.next;
        }
        n.next = end;
    }

    public static Node of(int... is) {
        Node first = new Node(is[0]);

        for (int i = 1; i < is.length; i++) {
            first.appendToTail(is[i]);
        }


        return first;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        var that = (Node) o;
        if (that.data != this.data)
            return false;

        if (next == null) {
            return that.next == null;
        }

        return next.equals(that.next);
    }

    @Override
    public String toString() {
        return "(" + data + ", " + next + ")";
    }

}

class NodeSpec {

    @Test
    void equals() {
        assertThat(of(1, 2)).isEqualTo(of(1, 2));
        assertThat(of(1, 2)).isNotEqualTo(of(1, 2, 3));
    }

}