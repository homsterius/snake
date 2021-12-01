package homsterius.snakegame;

class BoardFactory {

    static Board buildSquareBoard(int size) {
        var points = new Point[4];

        points[0] = new Point(0,0);
        points[1] = new Point(0, size);
        points[2] = new Point(size, size);
        points[3] = new Point(size, 0);

        return new Board(points);
    }
}
