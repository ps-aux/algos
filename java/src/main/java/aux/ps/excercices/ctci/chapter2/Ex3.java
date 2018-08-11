package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter2.data.Node.of;
import static org.assertj.core.api.Assertions.assertThat;


interface DeleteMiddleSpec {

    void deleteMiddleNode(Node m);

    @ParameterizedTest(name = "{0} after deleting of {1} is {2}")
    @ArgumentsSource(Data.class)
    default void test(Node list, Node m, Node res) {
        deleteMiddleNode(m);
        assertThat(list).isEqualTo(res);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            Node n1 = of(1, 2, 3, 4);
            Node n2 = of(1, 2, 3, 4);


            return Stream.of(
                    Arguments.of(n1,
                            n1.next.next,
                            of(1, 2, 4)),
                    Arguments.of(n2,
                            n2.next,
                            of(1, 3, 4)));
        }
    }

}


class DeleteMiddleSpecImpl implements DeleteMiddleSpec {


    @Override
    public void deleteMiddleNode(Node m) {
        assert m.next != null;// This not the last node
        m.data = m.next.data;
        m.next = m.next.next;
    }
}

