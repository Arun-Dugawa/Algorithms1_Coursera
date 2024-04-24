import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;

    public PointSET() {  // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() {  // is the set empty?
        return set.isEmpty();
    }
    public               int size() {  // number of points in the set
        return set.size();
    }
    public              void insert(Point2D p) {  // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }
    public           boolean contains(Point2D p) {  // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }
    public              void draw() {  // draw all points to standard draw
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {  // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : set) {
            if (rect.xmin() <= p.x() && rect.xmax() >= p.x() &&
                rect.ymin() <= p.y() && rect.ymax() >= p.y()) {
                queue.enqueue(p);
            }
        }
        return queue;
    }
    public           Point2D nearest(Point2D p) {  // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        for (Point2D currPoint : set) {
            if (nearest == null || p.distanceSquaredTo(currPoint) < p.distanceSquaredTo(nearest)) {
                nearest = currPoint;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {  // unit testing of the methods (optional)
        PointSET ps = new PointSET();
        ps.insert(new Point2D(1, 1));
        ps.insert(new Point2D(1, 1));
        ps.insert(new Point2D(1, 1));
        System.out.println(ps.size());
    }
}