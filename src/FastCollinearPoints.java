import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] res = new LineSegment[0];
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        Point[] temp = new Point[n];
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) throw new IllegalArgumentException();
            temp[i] = points[i];
        }
        Queue<Point[]> queue = new Queue<>();
        for (Point p : points) {
            Arrays.sort(temp, p.slopeOrder());
            double preSlope = p.slopeTo(temp[0]);
            int count = 1;
            for (int i = 1; i < n; ++i) {
                if (p.compareTo(temp[i]) == 0) throw new IllegalArgumentException();
                double currSlope = p.slopeTo(temp[i]);
                if (currSlope == preSlope) {
                    ++count;
                }
                else {
                    addIfValid(temp, queue, count, i, p);
                    count = 1;
                    preSlope = currSlope;
                }
            }
            if (count > 2) {
                addIfValid(temp, queue, count, n, p);
            }

        }
        if (queue.size() > 0) {
            res = new LineSegment[queue.size()];
            int i = 0;
            while (!queue.isEmpty()) {
                Point[] curr = queue.dequeue();
                LineSegment seg = new LineSegment(curr[0], curr[1]);
                res[i++] = seg;
            }
        }

    }
    // the number of line segments
    public           int numberOfSegments() {
        return res.length;
    }
    private void addIfValid(Point[] points, Queue<Point[]> queue, int count, int i, Point p) {
        if (count > 2) {
            Point[] curr = new Point[count + 1];
            while (count > 0) {
                curr[count] = points[i - count--];
            }
            curr[0] = p;
            Arrays.sort(curr);
            boolean listed = false;
            for (Point[] x : queue) {
                if (x[0] == curr[0] && x[1] == curr[curr.length - 1]) {
                    listed = true;
                    break;
                }
            }
            if (!listed) {
                Point[] toAdd = new Point[]{curr[0], curr[curr.length - 1]};
                queue.enqueue(toAdd);
            }
        }
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[res.length];
        for (int i = 0; i < res.length; ++i) {
            copy[i] = res[i];
        }
        return copy;
    }
    public static void main(String[] args) {
        Point[] points = new Point[4];
        points[0] = new Point(10794, 17999);
        points[1] = new Point(5094, 17999);
        points[2] = new Point(6756, 17999);
        points[3] = new Point(10337, 17999);

//        Point[] points = new Point[6];
//        points[0] = new Point(14000, 10000);
//        points[1] = new Point(1234, 5678);
//        points[2] = new Point(21000, 10000);
//        points[3] = new Point(32000, 10000);
//        points[4] = new Point(18000, 10000);
//        points[5] = new Point(19000, 10000);

//        Point[] points = new Point[8];
//        points[0] = new Point(10000, 0);
//        points[1] = new Point(0, 10000);
//        points[2] = new Point(3000, 7000);
//        points[3] = new Point(7000, 3000);
//        points[4] = new Point(20000, 21000);
//        points[5] = new Point(3000, 4000);
//        points[6] = new Point(14000, 15000);
//        points[7] = new Point(6000, 7000);
//         draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        System.out.println(collinear.segments().length);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment.toString());
            segment.draw();
        }
        StdDraw.show();
    }
}