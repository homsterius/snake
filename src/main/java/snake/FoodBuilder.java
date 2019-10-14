package snake;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

class FoodBuilder {

    private ArrayList<Point> points;
    private Snake snake;

    FoodBuilder(@NotNull Board theBoard, @NotNull Snake theSnake) {
        this.snake = theSnake;

        for (int y = theBoard.getLowerY() + 1; y < theBoard.getUpperY(); ++y) {
            for (int x = theBoard.getLeftmostX() + 1; x < theBoard.getRightmostX(); ++x) {
                Point p = new Point(x, y);

                if (theBoard.isAPointInside(p)) {
                    this.points.add(p);
                }
            }
        }
    }

    Point nextPoint() {
        var index = (int) (Math.random() * this.points.size()) - this.snake.size();
        int i = 0;
        int j = 0;

        for (Point p : this.points) {
            if (!this.snake.contains(p) && j++ >= index) {
                break;
            }
            ++i;
        }

        return this.points.get(i);
    }
}
