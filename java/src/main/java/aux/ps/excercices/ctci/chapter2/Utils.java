package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;

public class Utils {

    public static Node combine(Node... nodes) {
        for (int i = 0; i < nodes.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        return nodes[0];
    }

    public static Node last(Node list) {
        Node n = list;
        while (n.next != null) {
            n = n.next;
        }

        return n;
    }

    public static Node nth(Node list, int index) {
        Node n = list;
        int i = 0;
        while (true) {
            if (n == null)
                throw new IndexOutOfBoundsException();
            if (index == i)
                return n;

            n = n.next;
            i++;
        }
    }
}
