package aux.ps.excercices.ctci.data;

public class Coords {

    public final int x;
    public final int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coords c(int x, int y) {
        return new Coords(x, y);
    }

    public Coords add(Coords c) {
        return add(c.x, c.y);
    }

    public Coords add(int x, int y) {
        return new Coords(this.x + x, this.y + y);
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
