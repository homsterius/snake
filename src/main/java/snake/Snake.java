package snake;

import javax.validation.constraints.NotNull;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;

public class Snake implements Iterable<Point> {

    private int speed;

    /**
     * All points of the snake position
     */
    private final Deque<Point> bodyPosition;

    /**
     * Direction which the snake is moving
     */
    private Direction direction;

    private int growingSteps;

    Snake(@NotNull Point startingPoint, @NotNull Direction startingDirection, int startingSpeed) {
        this.bodyPosition = new LinkedList<>();
        this.bodyPosition.offerFirst(startingPoint);
        this.direction = startingDirection;
        this.speed = startingSpeed;
        this.growingSteps = 0;
    }

    /**
     * Returns {@code Iterator<Point>}
     */
    @Override
    public Iterator<Point> iterator() {
        return this.bodyPosition.iterator();
    }

    /**
     * Increase the size of the snake.
     * Size are not increasing immediately but by the next several steps of the snake.
     */
    public Snake eats(@NotNull Food food) {
        this.growingSteps += food.getCalorie();
        return this;
    }

    /**
     * Applies new position based on velocity and previous position.
     */
    public Snake nextStep() {
        Point headPoint = this.bodyPosition.getFirst();

        if (this.growingSteps > 0) {
            --this.growingSteps;
        } else {
            this.bodyPosition.removeLast();
        }

        Point nextPoint = headPoint.relativePoint(this.direction, this.speed);
        this.bodyPosition.offerFirst(nextPoint);

        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int size() {
        return this.bodyPosition.size();
    }

    public Snake setDirection(@NotNull Direction newDirection) {
        switch (newDirection) {
            case UP:
            case DOWN:
                if (this.direction.equals(Direction.RIGHT) || this.direction.equals(Direction.LEFT)) {
                    this.direction = newDirection;
                }
            case RIGHT:
            case LEFT:
                if (this.direction.equals(Direction.UP) || this.direction.equals(Direction.DOWN)) {
                    this.direction = newDirection;
                }
        }
        return this;
    }

    public Snake setSpeed(int speed) {
        this.speed = speed;
        return this;
    }
}
