package aux.ps.excercices.ctci.chapter2;

import aux.ps.excercices.ctci.chapter2.data.Node;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

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
                            of(1, 5, 3, 6, 2, 4),
                            3),
                    Arguments.of(
                            of(1, 1, 1, 3, 1, 1, 1),
                            3)
            );
        }
    }

}


class IsPalindrome implements IsPalindromeSpec {


    @Override
    public boolean isPalindrome(Node list) {
        return false;
    }
}

