package aux.ps.excercices.ctci.chapter5;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static aux.ps.excercices.ctci.chapter5.BitUtils.printBin;
import static aux.ps.excercices.ctci.chapter5.BitUtils.readBit;
import static aux.ps.excercices.ctci.chapter5.BitUtils.setBitOn;
import static org.assertj.core.api.Java6Assertions.assertThat;


interface FlipBitToWinSpec {

    int flipBitToWin(int in);


    @ParameterizedTest(name = "flip bit to win")
    @ArgumentsSource(Data.class)
    default void test(int in, int out) {
        assertThat(flipBitToWin(in)).isEqualTo(out);
    }

    class Data implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            int in1 = 1775; //11011101111
            int out1 = 8;
            return Stream.of(
                    Arguments.of(in1, out1),
                    Arguments.of(Integer.valueOf("11001100101", 2), 3)
            );
        }

    }

}


class FlipBitToWinBruteImpl implements FlipBitToWinSpec {

    int size = 32;

    private int longestSeq(int in) {
        int max = 0;
        int current = 0;

        for (int i = 0; i < size; i++) {
            boolean bit = readBit(in, i);
            if (bit)
                current++;
            else {
                if (current == 0)
                    continue;
                max = Math.max(current, max);
                current = 0;
            }
        }

        return max;
    }

    @Override
    public int flipBitToWin(int in) {
        int max = 0;

        for (int i = 0; i < size; i++) {
            boolean bit = readBit(in, i);
            if (!bit) {
                int longest = longestSeq(setBitOn(in, i));
                max = Math.max(max, longest);
            }

        }
        return max;
    }
}

class FlipBitToWinImpl implements FlipBitToWinSpec {

    int size = 32;

    @Override
    public int flipBitToWin(int in) {

        int prevSeq = 0;
        int currentSeq = 0;
        boolean previous = false;
        int max = 0;

        for (int i = 0; i < size; i++) {
            boolean bit = readBit(in, i);
            if (bit) {
                currentSeq++;
            } else {
                if (!previous) {
                    // reset
                    currentSeq = 0;
                    prevSeq = 0;
                    continue;
                } else {
                    int count = prevSeq + currentSeq;
                    max = Math.max(count, max);
                    prevSeq = currentSeq;
                    currentSeq = 0;
                }
            }
            previous = bit;
        }
        return max;
    }
}


