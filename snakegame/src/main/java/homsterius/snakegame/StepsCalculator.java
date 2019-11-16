package homsterius.snakegame;

class StepsCalculator {

    private double stepsPerSecond;
    private long lastSpeedChange;
    private int prevSteps;

    StepsCalculator(double sps) {
        this.stepsPerSecond = sps;
        this.lastSpeedChange = System.nanoTime();
        this.prevSteps = 0;
    }

    void setStepsPerSecond(long sps) {
        this.prevSteps += this.calcSteps();
        this.stepsPerSecond = sps;
        this.lastSpeedChange = System.nanoTime();
    }

    int getSteps() {
        return this.prevSteps + this.calcSteps();
    }

    private int calcSteps() {
        return (int) ((System.nanoTime() - this.lastSpeedChange) / (1_000_000_000.0 / this.stepsPerSecond));
    }
}
