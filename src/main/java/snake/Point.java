package snake;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class Point {

    private final int x;
    private final int y;

    public Point(int theX, int theY) {
        this.x = theX;
        this.y = theY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point relativePoint(@NotNull Direction direction, int distance) {
        int newX = this.x + direction.i * distance;
        int newY = this.y + direction.j * distance;
        return new Point(newX, newY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }
}
