package homsterius.snakegame;

import java.util.Iterator;

class PointsPairIterable implements Iterable<PointsPair> {

    private class PointsPairIterator implements Iterator<PointsPair> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return this.index < points.length;
        }

        @Override
        public PointsPair next() {
            int first = this.index - 1 < 0
                    ? points.length - 1
                    : this.index - 1;
            int second = this.index;

            ++this.index;

            return new PointsPair(points[first], points[second]);
        }
    }


    private final Point[] points;

    PointsPairIterable(Point[] thePoints) {
        this.points = thePoints;
    }


    @Override
    public Iterator<PointsPair> iterator() {
        return new PointsPairIterator();
    }
}
