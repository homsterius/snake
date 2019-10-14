package snake;

import org.testng.annotations.Test;
import snake.Exceptions.BiteItselfException;

import java.util.HashSet;

import static org.testng.Assert.*;

public class SnakeTest {

    @Test
    public void testEat() {
        var snake = this.createSnake();

        assertEquals(snake.size(), 1);

        var food = new Food(2, new Point(0, 0));
        try {
            snake.eats(food)
                    .nextStep()
                    .nextStep()
                    .nextStep()
                    .nextStep();
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }

        assertEquals(snake.size(), 3);
    }

    @Test
    public void testNextStepPosition() {
        var snake = this.createSnake();

        try {
            snake.nextStep()
                    .nextStep()
                    .nextStep();
            assertEquals(snake.getHeadPoint(), new Point(0, 3));

            snake.setDirection(Direction.RIGHT)
                    .nextStep()
                    .nextStep()
                    .nextStep();
            assertEquals(snake.getHeadPoint(), new Point(3, 3));
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }
    }

    @Test
    public void testNextStepShape() {
        var snake = this.createSnake();
        var food = new Food(2, new Point(10, 10));

        try {
            snake.eats(food)
                    .nextStep()
                    .setDirection(Direction.RIGHT)
                    .nextStep();
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }

        var shape = new Point[]{
                new Point(1, 1),
                new Point(0, 1),
                new Point(0, 0)
        };

        int i = 0;
        for (Point point : snake) {
            assertEquals(point, shape[i]);
            ++i;
        }
    }

    @Test(expectedExceptions = BiteItselfException.class)
    public void testBitingSnakeItself() throws BiteItselfException {
        var snake = this.createSnake();
        var food = new Food(4, new Point(0, 0));

        snake.eats(food)
                .nextStep()
                .setDirection(Direction.RIGHT)
                .nextStep()
                .setDirection(Direction.DOWN)
                .nextStep()
                .setDirection(Direction.LEFT)
                .nextStep();
    }

    @Test
    public void testDiff() {
        var points = new HashSet<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));

        var snake = this.createSnake();

        try {
            snake.eats(new Food(3, new Point(0, 0)))
                .nextStep().nextStep().nextStep();
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }

        var expected = new HashSet<Point>();
        expected.add(new Point(1,0));
        expected.add(new Point(1,1));
        assertEquals(snake.diff(points), expected);
    }

    private Snake createSnake() {
        return new Snake(new Point(0, 0), Direction.UP);
    }
}