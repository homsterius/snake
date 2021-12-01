package homsterius.snakegame;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BoardTest {

    @Test(dataProvider = "testBoard")
    public void testIsAPointInside(Point point, Board board, boolean expectedResult) {
        assertEquals(
                board.isAPointInside(point),
                expectedResult
        );
    }

    @DataProvider(name = "testBoard")
    public Object[][] snakesAndBoarders() {
        // in block comments dots depict borders and star depicts a homsterius.snake

        return new Object[][]{
                /*
                 * ...
                 * .*.
                 * ...
                 */
                {
                    new Point(1, 1),
                    new Board(new Point[]{
                            new Point(0, 0),
                            new Point(0, 2),
                            new Point(2, 2),
                            new Point(2, 0),
                    }),
                    true
                },

                /*
                 * ...
                 * * .
                 * ...
                 */
                {
                        new Point(0, 1),
                        new Board(new Point[]{
                                new Point(0, 0),
                                new Point(0, 2),
                                new Point(2, 2),
                                new Point(2, 0),
                        }),
                        false
                },

                /*
                 * ...
                 * . .
                 * .*.
                 */
                {
                        new Point(1, 0),
                        new Board(new Point[]{
                                new Point(0, 0),
                                new Point(0, 2),
                                new Point(2, 2),
                                new Point(2, 0),
                        }),
                        false
                },

                /*
                 * .... ....
                 * .* ...  .
                 * .       .
                 * .........
                 */
                {
                        new Point(1, 2),
                        new Board(new Point[]{
                                new Point(0, 0),
                                new Point(0, 3),
                                new Point(3, 3),
                                new Point(3, 2),
                                new Point(5, 2),
                                new Point(5, 3),
                                new Point(8, 3),
                                new Point(8, 0),
                        }),
                        true
                },

                /*
                 * .... ....
                 * .  .*.  .
                 * .       .
                 * .........
                 */
                {
                        new Point(4, 2),
                        new Board(new Point[]{
                                new Point(0, 0),
                                new Point(0, 3),
                                new Point(3, 3),
                                new Point(3, 2),
                                new Point(5, 2),
                                new Point(5, 3),
                                new Point(8, 3),
                                new Point(8, 0),
                        }),
                        false
                },

                /*
                 *    .....
                 *   .   .
                 *  . * .
                 * .....
                 */
                {
                        new Point(3, 1),
                        new Board(new Point[]{
                                new Point(0, 0),
                                new Point(3, 3),
                                new Point(7, 3),
                                new Point(4, 0),
                        }),
                        true
                },

                /*
                 *    .....
                 *   .   .
                 *  .   *
                 * .....
                 */
                {
                        new Point(5, 1),
                        new Board(new Point[]{
                                new Point(0, 0),
                                new Point(3, 3),
                                new Point(7, 3),
                                new Point(4, 0),
                        }),
                        false
                },
        };
    }
}