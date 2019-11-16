package homsterius.javafxgui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import homsterius.snakegame.ControllerEvent;
import homsterius.snakegame.Exceptions.BiteItselfException;
import homsterius.snakegame.Exceptions.BiteWallException;
import homsterius.snakegame.Game;

public class SnakeWithJavaFX extends Application {

    private GameController gameController = new GameController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Snake with JavaFX");

        CanvasPane canvasPane = new CanvasPane(600, 600);

        var scene = new Scene(canvasPane);
        stage.setScene(scene);

        this.initInputHandler(scene);
        this.initGame(canvasPane.getCanvas());

        stage.show();
    }

    private void initGame(Canvas canvas) {
        var gameThread = new Thread(
                () -> {
                    try {
                        new Game(this.gameController, new GameRenderer(canvas)).run();
                    } catch (BiteItselfException e) {
                        e.printStackTrace();
                    } catch (BiteWallException e) {
                        e.printStackTrace();
                    }
                }
        );

        gameThread.start();
    }

    private void initInputHandler(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case K:
                case W:
                case UP:
                    this.gameController.triggerEvent(ControllerEvent.KEY_UP);
                    break;

                case J:
                case S:
                case DOWN:
                    this.gameController.triggerEvent(ControllerEvent.KEY_DOWN);
                    break;

                case H:
                case A:
                case LEFT:
                    this.gameController.triggerEvent(ControllerEvent.KEY_LEFT);
                    break;

                case L:
                case D:
                case RIGHT:
                    this.gameController.triggerEvent(ControllerEvent.KEY_RIGHT);
                    break;
            }
        });
    }
}
