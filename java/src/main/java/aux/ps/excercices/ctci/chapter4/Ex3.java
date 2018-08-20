package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


interface ListOfDepthsSpec {

    List<LinkedList<Node>> listOfDepths(Node bst);

    @ParameterizedTest(name = "list of depths match")
    @ArgumentsSource(Data.class)
    default void test(Node root, int[][] expected) {
        var res = listOfDepths(root);

        for (int i = 0; i < res.size(); i++) {
            var l = res.get(i);
            for (int j = 0; j < l.size(); j++) {
                assertThat(l.get(j).val).isEqualTo(expected[i][j]);
            }
        }
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            var t1 = new Node(10);
            int[][] t1Res = {{10}};

            var t2 = new Node(10);
            t2.add(5);
            t2.add(1);
            t2.add(7);

            t2.add(15);
            t2.add(13);
            t2.add(17);
            int[][] t2Res = {
                    {10},
                    {5, 15},
                    {1, 7, 13, 17}
            };

            var t3 = new Node(10);
            t3.add(5);
            t3.add(1);
            t3.add(7);
            int[][] t3Res = {
                    {10},
                    {5},
                    {1, 7}
            };

            return Stream.of(
                    of(t1, t1Res),
                    of(t2, t2Res),
                    of(t3, t3Res)
            );
        }
    }
}

// TODO Implement with stack
class ListOfDepthsImpl implements ListOfDepthsSpec {

    @Override
    public List<LinkedList<Node>> listOfDepths(Node bst) {
        var h = bst.height();
        var list = new LinkedList<LinkedList<Node>>();

        range(0, h)
                .forEach(i -> list.add(new LinkedList<>()));


        visitLevel(bst, 1, list);

        return list;
    }

    private void visitLevel(Node n, int lvl, LinkedList<LinkedList<Node>> list) {
        if (n == null)
            return;
        list.get(lvl - 1).add(n);
        // We reached the end

        var nextLv = lvl + 1;

        visitLevel(n.left, nextLv, list);
        visitLevel(n.right, nextLv, list);
    }
}

