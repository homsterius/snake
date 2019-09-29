package snake;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class SnakeTest {

    @Test
    public void testEat() {
        var snake = new Snake(new Point(0, 0), Direction.UP, 1);

        assertEquals(snake.size(), 1);

        var food = new Food(1, new Point(0, 0));

        for (int i = 0; i < 10; ++i) {
            snake.eats(food).nextStep();
        }

        assertEquals(snake.size(), 11);
    }

    @Test
    public void testNextStepPosition() {
        var snake = new Snake(new Point(0, 0), Direction.UP, 1);

        snake.nextStep()
                .nextStep()
                .nextStep();
        assertEquals(snake.iterator().next(), new Point(0, 3));

        snake.setDirection(Direction.RIGHT)
                .nextStep()
                .nextStep()
                .nextStep();
        assertEquals(snake.iterator().next(), new Point(3, 3));

        snake.setSpeed(3)
                .nextStep()
                .nextStep()
                .nextStep();
        assertEquals(snake.iterator().next(), new Point(12, 3));
    }

    @Test
    public void testNextStepShape() {
        var snake = new Snake(new Point(0, 0), Direction.UP, 1);
        var food = new Food(2, new Point(10, 10));
        snake.eats(food)
                .nextStep()
                .setDirection(Direction.RIGHT)
                .nextStep();
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
}