package snake;

import snake.Exceptions.BiteItselfException;
import snake.Exceptions.BiteWallException;
import snake.Exceptions.ThereIsNoPointsLeftException;

public class Game implements ControllerListener {

    private final Board board;
    private final Snake snake;
    private Food food;

    private StepsCalculator stepsCalc;

    private final Thread controllerThread;
    private final Controller controller;
    private final Renderer renderer;

    /**
     * @param theController player input, works in another thread
     * @param theRenderer Game calls a method {@code render()} of this
     *                    object when there is something that can affect on game view
     */
    public Game(Controller theController, Renderer theRenderer) {
        this.board = BoardFactory.buildSquareBoard(25);
        this.snake = this.board.buildSnake();

        this.renderer = theRenderer;

        this.controller = theController;
        this.controller.addListener(this);

        this.controllerThread = new Thread(this.controller);
        this.controllerThread.start();
    }

    /**
     * This method is called when the player has made an action
     */
    public void update(ControllerEvent controllerEvent) {
        switch (controllerEvent) {
            case KEY_UP:
                this.snake.setDirection(Direction.UP);
                break;

            case KEY_DOWN:
                this.snake.setDirection(Direction.DOWN);
                break;

            case KEY_RIGHT:
                this.snake.setDirection(Direction.RIGHT);
                break;

            case KEY_LEFT:
                this.snake.setDirection(Direction.LEFT);
                break;
        }
    }

    public void run() throws BiteItselfException, BiteWallException {
        this.stepsCalc = new StepsCalculator(1.0);

        try {
            this.food = this.buildFood();
            this.render();
            this.mainLoop();
        } catch (ThereIsNoPointsLeftException e) {
            // the player won
        }
    }

    private void mainLoop()
            throws BiteItselfException, BiteWallException, ThereIsNoPointsLeftException {

        while (this.board.getNumberOfInnerPoints() > this.snake.size()) {
            if (this.snake.getSteps() < this.stepsCalc.getSteps()) {
                this.snake.nextStep();

                if (!this.board.isAPointInside(this.snake.getHeadPoint())) {
                    throw new BiteWallException();
                }

                if (this.snake.eats(this.food)) {
                    this.food = this.buildFood();
                }

                this.render();
            }
        }
    }

    private Food buildFood() throws ThereIsNoPointsLeftException {
        return this.board.buildFood(1, this.snake);
    }

    private void render() {
        this.renderer.render(this);
    }
}
