package homsterius.snakegame;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PointTest {

    @Test
    public void testRelativePoint() {
        var point = new Point(3, 5);

        assertEquals(point.relativePoint(Direction.DOWN, 10), new Point(3, 15));

        assertEquals(point.relativePoint(Direction.LEFT, 6), new Point(-3, 5));
    }
}