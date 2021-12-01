package homsterius.snakegame;

import org.testng.annotations.Test;
import homsterius.snakegame.Exceptions.BiteItselfException;

import java.util.HashSet;

import static org.testng.Assert.*;

public class SnakeTest {

    @Test
    public void testDoesNotEat() {
        var snake = this.createSnake();

        assertEquals(snake.size(), 1);

        boolean didSnakeEat = snake.eats(new Food(1, new Point(1, 1)));

        assertFalse(didSnakeEat);

        try {
            snake.nextStep()
                    .nextStep();
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }

        assertEquals(snake.size(), 1);
    }

    @Test
    public void testEat() {
        var snake = this.createSnake();

        assertEquals(snake.size(), 1);

        boolean didSnakeEat = snake.eats(
                new Food(2, new Point(0, 0))
        );

        assertTrue(didSnakeEat);

        try {
            snake.nextStep()
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
        snake.eats(new Food(2, new Point(0, 0)));

        try {
            snake.nextStep()
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
        snake.eats(new Food(4, new Point(0, 0)));

        snake.nextStep()
                .setDirection(Direction.RIGHT)
                .nextStep()
                .setDirection(Direction.UP)
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
        snake.eats(new Food(3, new Point(0, 0)));

        try {
            snake.nextStep().nextStep().nextStep();
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }

        var expected = new HashSet<Point>();
        expected.add(new Point(1,0));
        expected.add(new Point(1,1));
        assertEquals(snake.diff(points), expected);
    }

    @Test
    public void testGetSteps() {
        var snake = this.createSnake();
        assertEquals(snake.getSteps(), 0);

        try {
            snake.nextStep();
            assertEquals(snake.getSteps(), 1);

            snake.nextStep();
            assertEquals(snake.getSteps(), 2);

            snake.nextStep();
            assertEquals(snake.getSteps(), 3);
        } catch (BiteItselfException e) {
            fail("Exception BiteItselfException shouldn't be thrown");
        }
    }

    private Snake createSnake() {
        return new Snake(new Point(0, 0), Direction.DOWN);
    }
}