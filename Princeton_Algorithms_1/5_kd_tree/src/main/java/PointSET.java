import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private Set<Point2D> point2DSet;

    // construct an empty set of points
    public PointSET() {
        this.point2DSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return point2DSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return point2DSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (!point2DSet.contains(p)) {
            point2DSet.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        return point2DSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : point2DSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        Set<Point2D> rangePoints = new TreeSet<>();
        for (Point2D point : point2DSet) {
            if (rect.contains(point)) {
                rangePoints.add(point);
            }
        }

        return rangePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (point2DSet.isEmpty()) {
            return null;
        }

        Point2D nearest = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D point : point2DSet) {
            if (point.distanceTo(p) < distance) {
                nearest = point;
                distance = point.distanceTo(p);
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}