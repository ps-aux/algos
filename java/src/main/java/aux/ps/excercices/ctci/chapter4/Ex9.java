package aux.ps.excercices.ctci.chapter4;

import aux.ps.excercices.ctci.chapter4.data.Bst.Node;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.params.provider.Arguments.of;


interface BstSequencesSpec {


    List<List<Integer>> bstSequences(Node bst);

    @ParameterizedTest(name = "all bst sequences")
    @ArgumentsSource(Data.class)
    default void test(Node a, Integer[][] expected) {
        List<List<Integer>> ex = Arrays.stream(expected)
                .map(Arrays::asList)
                .collect(toList());
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

            Integer[][] t1Res = {
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

/**
 * TODO finish`
 */
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


    LinkedList<Integer> concat(List<Integer> a, List<Integer> b) {
        var x = new LinkedList<Integer>();
        x.addAll(a);
        x.addAll(b);
        return x;
    }

    List<List<Integer>> perms(List<Integer> list) {
        if (list.size() == 1)
            return List.of(list);

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            var el = list.get(i);
            var others = list.stream().filter(e -> !e.equals(el))
                    .collect(toList());
            var perms = perms(others).stream().map(l -> concat(List.of(el), l))
                    .collect(toList());

            res.addAll(perms);
        }

        return res;
    }

    List<List<Integer>> perms(List<List<Integer>> a, List<List<Integer>> b) {
        return a.stream().map(l1 ->
                b.stream().map(l2 -> concat(l1, l2)))
                .flatMap(x -> x)
                .collect(toList());
    }


    LinkedList<Integer> tail(List<Integer> l) {
        LinkedList<Integer> n = new LinkedList<>(l);
        n.removeFirst();
        return n;
    }

    List<LinkedList<Integer>> weave(List<Integer> a, List<Integer> b) {
        if (a.isEmpty())
            return List.of(new LinkedList<>(a));
        if (b.isEmpty())
            return List.of(new LinkedList<>(b));


        List<LinkedList<Integer>> as = IntStream.range(1, a.size())
                .mapToObj(i -> {
                    List<Integer> head = a.subList(0, i);
                    return weave(a.subList(i, a.size()), b)
                            .stream()
                            .map(l -> concat(head, l));
                })
                .flatMap(x -> x)
                .collect(toList());

        List<LinkedList<Integer>> bs = IntStream.range(1, b.size())
                .mapToObj(i -> {
                    List<Integer> head = b.subList(0, i);
                    return weave(b.subList(i, b.size()), a)
                            .stream()
                            .map(l -> concat(head, l));
                })
                .flatMap(x -> x)
                .collect(toList());


        as.addAll(bs);
        return as;
    }

    List<LinkedList<Integer>> weaveAll(List<List<Integer>> a, List<List<Integer>> b) {
        return a.stream()
                .flatMap(l1 ->
                        b.stream().flatMap(l2 -> weave(l1, l2).stream()))
                .collect(toList());
    }

    private LinkedList<Integer> prepend(Integer h, LinkedList<Integer> tail) {
        var n = new LinkedList<>(tail);
        n.addFirst(h);
        return n;
    }

    @Override
    public List<List<Integer>> bstSequences(Node bst) {
        if (bst == null)
            return Collections.emptyList();

        if (bst.left == null && bst.right == null)
            return List.of(List.of(bst.val));

        var w = weaveAll(bstSequences(bst.left), bstSequences(bst.right));

        List<List<Integer>> r = w.stream().map(l -> prepend(bst.val, l))
                .collect(toList());

        System.out.println("--");
        System.out.println(weave(List.of(1), List.of(4, 5)));
        System.out.println("--");
//        System.out.println(r);
        return r;
    }
}