package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.params.provider.Arguments.of;


interface BstSequencesSpec {


    int[][] bstSequences(Node bst);

    @ParameterizedTest(name = "all bst sequences")
    @ArgumentsSource(Data.class)
    default void test(Node a, int[][] expected) {
        bstSequences(a);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            Node t1 = new Node(8);
            t1.add(4);
            t1.add(5);

            t1.add(20);
            t1.add(14);
            t1.add(22);

            int[][] t1Res = {
                    {8, 4, 5, 20, 14, 22},
                    {8, 4, 5, 20, 22, 14},
                    {8, 20, 22, 14, 4, 5}
            };


            return Stream.of(
                    of(t1, t1Res)
            );
        }
    }
}

class BstSequencesImpl implements BstSequencesSpec {

    private void collectLevels(Node n, int h, List<List<Integer>> levels) {
        if (n == null)
            return;
        List<Integer> l = levels.get(h);
        l.add(n.val);

        collectLevels(n.left, h - 1, levels);
        collectLevels(n.right, h - 1, levels);
    }


    List<List<Integer>> invert(List<List<Integer>> list) {
        List<List<Integer>> inverted = new ArrayList<>();

        for (int i = list.size() - 1; i >= 0; i--)
            inverted.add(list.get(i));

        return inverted;
    }

    List<List<Integer>> combine(List<List<Integer>> list, Integer val) {

    }

    List<List<Integer>> perms(List<List<Integer>> list) {
        List<List<Integer>> res = new ArrayList<>();

        res.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            var l = list.get(i);

        }

        return res;
    }



    @Override
    public int[][] bstSequences(Node bst) {
/*        List<List<Integer>> sequences = new ArrayList<>();
        List<Integer> toRoot = new ArrayList<>();
        toRoot.add(1);

        sequences.add(toRoot);*/

        int lvl = bst.height() - 1;
        List<List<Integer>> vals = IntStream.range(0, lvl + 1)
                .mapToObj(i -> new LinkedList<Integer>())
                .collect(toList());

        collectLevels(bst, lvl, vals);

        var inv = invert(vals);
        System.out.println(inv);
        return new int[0][];
    }
}