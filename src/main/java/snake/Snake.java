package snake;

import snake.Exceptions.BiteItselfException;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

public class Snake implements Iterable<Point> {

    /**
     * All points of the snake position
     */
    private final Deque<Point> bodyPosition;

    /**
     * It needs to detect a bite of the snake tail
     */
    private final Set<Point> bodyPointsSet;

    /**
     * Direction which the snake is moving
     */
    private Direction direction;

    /**
     * The number of steps left when the snake grows
     */
    private int growingSteps;

    Snake(@NotNull Point startingPoint, @NotNull Direction startingDirection) {
        this.bodyPosition = new LinkedList<>();
        this.bodyPosition.offerFirst(startingPoint);

        this.bodyPointsSet = new HashSet<>();
        this.bodyPointsSet.add(startingPoint);

        this.direction = startingDirection;
        this.growingSteps = 0;
    }

    /**
     * Returns {@code Iterator<Point>}
     */
    @Override
    public synchronized Iterator<Point> iterator() {
        return this.bodyPosition.iterator();
    }

    /**
     * Increase the size of the snake.
     * Size are not increasing immediately but by the next several steps of the snake.
     */
    synchronized Snake eats(@NotNull Food food) {
        this.growingSteps += food.getCalorie();
        return this;
    }

    /**
     * Applies new position based on velocity and previous position.
     *
     * @throws BiteItselfException in case if snake bites itself
     */
    synchronized Snake nextStep() throws BiteItselfException {
        Point headPoint = this.bodyPosition.getFirst();

        if (this.growingSteps > 0) {
            --this.growingSteps;
        } else {
            this.bodyPointsSet.remove(
                    this.bodyPosition.pollLast()
            );
        }

        Point nextPoint = headPoint.relativePoint(this.direction);

        this.bodyPosition.offerFirst(nextPoint);
        if (!this.bodyPointsSet.add(nextPoint)) {
            throw new BiteItselfException();
        }

        return this;
    }

    public synchronized int size() {
        return this.bodyPosition.size();
    }

    public synchronized Direction getDirection() {
        return direction;
    }

    public synchronized Snake setDirection(@NotNull Direction newDirection) {
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

    public synchronized Point getHeadPoint() {
        return this.bodyPosition.getFirst();
    }

    /**
     * Returns points without snake
     */
    public synchronized Set<Point> diff(Set<Point> points) {
        return points.stream()
                .filter(p -> !this.bodyPointsSet.contains(p))
                .collect(Collectors.toSet());
    }

    synchronized boolean contains(Point point) {
        return this.bodyPointsSet.contains(point);
    }
}
