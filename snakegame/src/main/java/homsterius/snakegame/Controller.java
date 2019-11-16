package homsterius.snakegame;

import java.util.LinkedList;
import java.util.List;

public abstract class Controller {

    private final List<ControllerListener> controllerListeners;

    public Controller() {
        this.controllerListeners = new LinkedList<>();
    }

    public final void addListener(ControllerListener controllerListener) {
        this.controllerListeners.add(controllerListener);
    }

    public final void triggerEvent(ControllerEvent controllerEvent) {
        this.controllerListeners.forEach(
                controllerListener -> controllerListener.update(controllerEvent));
    }
}
