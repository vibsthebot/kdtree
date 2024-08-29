import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {

    SET<Point2D> pointSet;
    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> inRect = new ArrayList<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) inRect.add(point);
        }
        return inRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Point2D nearest = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D point : pointSet) {
            if (p.distanceTo(point) < distance) nearest = point;
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}
