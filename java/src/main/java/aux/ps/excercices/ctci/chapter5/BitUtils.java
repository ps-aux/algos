package aux.ps.excercices.ctci.chapter5;

public class BitUtils {

    public static boolean readBit(int num, int idx) {
        return (num & 1 << idx) != 0;
    }

    public static int setBitOn(int num, int idx) {
        return (num | 1 << idx);
    }

    public static void printBin(int i) {
        System.out.println(Integer.toString(i, 2));
    }

    public static void main(String[] args) {

        int a = Integer.valueOf("1010", 2);
        printBin(setBitOn(a, 2));

    }
}
