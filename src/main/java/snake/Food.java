package snake;

public class Food {

    private final int calorie;

    private final Point position;

    public Food(int theCalorie, Point thePosition) {
        this.calorie = theCalorie;
        this.position = thePosition;
    }

    public int getCalorie() {
        return calorie;
    }

    public Point getPosition() {
        return position;
    }
}
