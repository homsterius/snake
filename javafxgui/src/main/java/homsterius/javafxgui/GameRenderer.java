package homsterius.javafxgui;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import homsterius.snakegame.*;

class GameRenderer implements Renderer {

    private final Canvas canvas;

    GameRenderer(Canvas theCanvas) {
        this.canvas = theCanvas;
    }

    @Override
    public void render(Game game) {
        var board = game.getBoard();
        double unit = this.getUnit(board);

        this.clearCanvas();
        this.drawBoard(game.getBoard(), unit);
        this.drawSnake(game.getSnake(), unit);
        this.drawFood(game.getFood(), unit);
    }

    private void clearCanvas() {
        var gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.color(0.3, 0.3, 0.3));
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    private void drawFood(Food food, double unit) {
        this.setFill(Color.color(0.7, 0.3, 0.3));
        this.drawSquare(food.getPosition(), unit);
    }

    private void drawBoard(Board board, double unit) {
        this.setFill(Color.color(0.7, 0.7, 0.7));
        for (Point p : board) {
            this.drawSquare(p, unit);
        }
    }

    private void drawSnake(Snake snake, double unit) {
        this.setFill(Color.color(0.3, 0.6, 0.3));
        for (Point p : snake) {
            this.drawSquare(p, unit);
        }
    }

    private double getUnit(Board board) {
        double windowRatio = this.canvas.getWidth() / this.canvas.getHeight();
        double boardRatio = (double) board.getWidth() / board.getHeight();

        return windowRatio < boardRatio
                ? this.canvas.getWidth() / board.getWidth()
                : this.canvas.getHeight() / board.getHeight();
    }

    private void drawSquare(Point point, double unit) {
       this.canvas.getGraphicsContext2D().fillRect(
               point.getX() * unit,
               point.getY() * unit,
               unit,
               unit
       );
    }

    private void setFill(Color color) {
        this.canvas.getGraphicsContext2D().setFill(color);
    }
}
