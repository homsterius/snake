package snake;

import snake.Exceptions.ThereIsNoPointsLeft;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Board {

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

    private void initInnerPoints() {
        for (int y = this.getLowerY() + 1; y < this.getUpperY(); ++y) {
            for (int x = this.getLeftmostX() + 1; x < this.getRightmostX(); ++x) {
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
            if (this.upperY < point.getY()) {
                this.upperY = point.getY();
            }
            if (this.lowerY > point.getY()) {
                this.lowerY = point.getY();
            }

            this.points[i++] = point;
        }
    }

    /**
     * Check collision by even-odd rule
     */
    public boolean isAPointInside(@NotNull Point point) {
        var p0 = this.points[this.points.length - 1];

        boolean isAPointInside = false;

        for (var p1 : this.points) {
            if ((p0.getY() < point.getY()) != (p1.getY() < point.getY())) {
                int x = this.getXOfALine(point.getY(), p0, p1);

                if (point.getX() < x) {
                    isAPointInside = !isAPointInside;
                } else if (point.getX() == x) {
                    return false;
                }
            } else if (p0.getY() == point.getY() && p1.getY() == point.getY() &&
                    (point.getX() >= p0.getX() && point.getX() <= p1.getX() ||
                            point.getX() >= p1.getX() && point.getX() <= p0.getX())
            ) {
                return false;
            }

            p0 = p1;
        }

        return isAPointInside;
    }

    /**
     * Returns x coordinate of a point on a line by y coordinate
     */
    private int getXOfALine(int y, Point p0, Point p1) {
        return p0.getX() +
                (y - p0.getY()) * (p1.getX() - p0.getX()) /
                        (p1.getY() - p0.getY());
    }

    public int getLeftmostX() {
        return leftmostX;
    }

    public int getRightmostX() {
        return rightmostX;
    }

    public int getUpperY() {
        return upperY;
    }

    public int getLowerY() {
        return lowerY;
    }

    private @NotNull Point randomPoint(@NotNull Snake snake) throws ThereIsNoPointsLeft {
        if (snake.size() == this.innerPoints.size()) {
            throw new ThereIsNoPointsLeft();
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

    @NotNull Food buildFood(int calorie, Snake snake) throws ThereIsNoPointsLeft {
        return new Food(calorie, this.randomPoint(snake));
    }

    @NotNull Snake buildSnake() {
        var point = this.randomPoint();
        Direction direction;

        if (this.isAPointInside(new Point(point.getX(), point.getY() + 1))) {
            direction = Direction.UP;
        } else if (this.isAPointInside(new Point(point.getX() + 1, point.getY()))) {
            direction = Direction.RIGHT;
        } else if (this.isAPointInside(new Point(point.getX(), point.getY() - 1))) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.LEFT;
        }

        return new Snake(point, direction);
    }
}
