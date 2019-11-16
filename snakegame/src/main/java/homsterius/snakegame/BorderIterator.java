package homsterius.snakegame;

import java.util.Iterator;

class BorderIterator implements Iterator<Point> {

    private final Iterator<PointsPair> pointsPairIterator;
    private PointsPair pair;
    private int x;
    private int y;

    BorderIterator(Point[] points) {
        this.pointsPairIterator = new PointsPairIterable(points).iterator();
        this.initNextPair();
    }

    @Override
    public boolean hasNext() {
        return this.pair != null;
    }

    @Override
    public Point next() {
        var point = new Point(this.x, this.y);
        var p0 = this.pair.firstPoint;
        var p1 = this.pair.secondPoint;

        if (point.equals(p1)) {
            this.initNextPair();
        } else if (p0.getX() == p1.getX()) {
            this.y += p0.getY() > p1.getY()
                    ? -1
                    : 1;
        } else if (p0.getY() == p1.getY()) {
            this.x += p0.getX() > p1.getX()
                    ? -1
                    : 1;
        } else {
            this.updateNextPoint();
        }

        return point;
    }

    private void initNextPair() {
        if (this.pointsPairIterator.hasNext()) {
            this.pair = this.pointsPairIterator.next();
            this.x = this.pair.firstPoint.getX();
            this.y = this.pair.firstPoint.getY();
        } else {
            this.pair = null;
        }
    }

    private void updateNextPoint() {
        var p0 = this.pair.firstPoint;
        var p1 = this.pair.secondPoint;

        double slope = (double) (p1.getY() - p0.getY()) / (p1.getX() - p0.getX());
        double b = p0.getY() - p0.getX() * slope;

        this.x += p0.getX() > p1.getX()
                ? -1
                : 1;

        this.y = (int) (this.x * slope + b);
    }
}
