package homsterius.javafxgui;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

class CanvasPane extends Pane {

    private final Canvas canvas;

    CanvasPane(int width, int height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setWidth(width);
        this.setHeight(height);

        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
    }

    Canvas getCanvas() {
        return canvas;
    }
}
