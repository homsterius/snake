package homsterius.snakegame;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int i;
    public final int j;

    Direction(int theI, int theJ) {
        this.i = theI;
        this.j = theJ;
    }
}
