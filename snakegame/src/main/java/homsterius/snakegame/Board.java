package homsterius.snakegame;

import homsterius.snakegame.Exceptions.ThereIsNoPointsLeftException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Point> {

    private final Point[] points;
    private final List<Point> innerPoints;

    private int leftmostX = 0;
    private int rightmostX = 0;
    private int upperY = 0;
    private int lowerY = 0;

    /**
     * @param boardVertices is the list of all vertices of the board
     */
    Board(@NotNull Point[] boardVertices) {
        if (boardVertices.length < 3) {
            throw new IllegalArgumentException("Board should consist at least of three points");
        }

        this.points = new Point[boardVertices.length];
        this.initBoundaries(boardVertices);

        this.innerPoints = new ArrayList<>();
        this.initInnerPoints();
    }

    public int getWidth() {
        return this.rightmostX - this.leftmostX + 1;
    }

    public int getHeight() {
        return this.lowerY - this.upperY + 1;
    }

    private void initInnerPoints() {
        for (int y = this.upperY + 1; y < this.lowerY; ++y) {
            for (int x = this.leftmostX + 1; x < this.rightmostX; ++x) {
                Point p = new Point(x, y);

                if (this.isAPointInside(p)) {
                    this.innerPoints.add(p);
                }
            }
        }
    }

    private void initBoundaries(Point[] boardVertices) {
        int i = 0;
        for (Point point : boardVertices) {
            if (this.leftmostX > point.getX()) {
                this.leftmostX = point.getX();
            }
            if (this.rightmostX < point.getX()) {
                this.rightmostX = point.getX();
            }
            if (this.upperY > point.getY()) {
                this.upperY = point.getY();
            }
            if (this.lowerY < point.getY()) {
                this.lowerY = point.getY();
            }

            this.points[i++] = point;
        }
    }

    /**
     * Check collision by even-odd rule
     */
    public boolean isAPointInside(@NotNull Point point) {
        boolean isAPointInside = false;

        for (var pair : new PointsPairIterable(this.points)) {
            if ((pair.firstPoint.getY() < point.getY()) != (pair.secondPoint.getY() < point.getY())) {
                int x = this.getXOfALine(point.getY(), pair);

                if (point.getX() < x) {
                    isAPointInside = !isAPointInside;
                } else if (point.getX() == x) {
                    return false;
                }
            } else if (this.isPointOnAHorizontalLine(point, pair)) {
                return false;
            }
        }

        return isAPointInside;
    }

    private boolean isPointOnAHorizontalLine(Point point, PointsPair pair) {
        return pair.firstPoint.getY() == point.getY() && pair.secondPoint.getY() == point.getY() &&
                (point.getX() >= pair.firstPoint.getX() && point.getX() <= pair.secondPoint.getX() ||
                        point.getX() >= pair.secondPoint.getX() && point.getX() <= pair.firstPoint.getX());
    }

    public int getNumberOfInnerPoints() {
        return this.innerPoints.size();
    }

    /**
     * Returns x coordinate of a point on a line by y coordinate
     */
    private int getXOfALine(int y, PointsPair pair) {
        return pair.firstPoint.getX() +
                (y - pair.firstPoint.getY()) * (pair.secondPoint.getX() - pair.firstPoint.getX()) /
                        (pair.secondPoint.getY() - pair.firstPoint.getY());
    }

    private @NotNull Point randomPoint(@NotNull Snake snake) throws ThereIsNoPointsLeftException {
        if (snake.size() == this.innerPoints.size()) {
            throw new ThereIsNoPointsLeftException();
        }

        var j = (int) (Math.random() * this.innerPoints.size());

        while (true) {
            Point p = this.innerPoints.get(j);
            if (!snake.contains(p)) {
                break;
            }

            j = (j + 1) % this.innerPoints.size();
        }

        return this.innerPoints.get(j);
    }

    private @NotNull Point randomPoint() {
        var j = (int) (Math.random() * this.innerPoints.size());
        return this.innerPoints.get(j);
    }

    @NotNull Food buildFood(int calorie, Snake snake) throws ThereIsNoPointsLeftException {
        return new Food(calorie, this.randomPoint(snake));
    }

    @NotNull Snake buildSnake() {
        var point = this.randomPoint();
        Direction direction;

        if (this.isAPointInside(new Point(point.getX(), point.getY() - 1))) {
            direction = Direction.UP;
        } else if (this.isAPointInside(new Point(point.getX() + 1, point.getY()))) {
            direction = Direction.RIGHT;
        } else if (this.isAPointInside(new Point(point.getX(), point.getY() + 1))) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.LEFT;
        }

        return new Snake(point, direction);
    }

    @Override
    public Iterator<Point> iterator() {
        return new BorderIterator(this.points);
    }
}
