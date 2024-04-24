import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] res = new LineSegment[0];
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        Queue<LineSegment> queue = new Queue<>();
        for (int p = 0; p < n; ++p) {
            if (points[p] == null) throw new IllegalArgumentException();
            for (int q = p + 1; q < n; ++q) {
                if (points[q] == null) throw new IllegalArgumentException();
                if (points[p].compareTo(points[q]) == 0) throw new IllegalArgumentException();
                for (int r = q + 1; r < n; ++r) {
                    if (points[r] == null) throw new IllegalArgumentException();
                    for (int s = r + 1; s < n; ++s) {
                        if (points[s] == null) throw new IllegalArgumentException();
                        double slopePQ = points[p].slopeTo(points[q]);
                        double slopePR = points[p].slopeTo(points[r]);
                        double slopePS = points[r].slopeTo(points[s]);
                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            Point[] points1 = new Point[]{points[p], points[q], points[r], points[s]};
                            Arrays.sort(points1);
                            queue.enqueue(new LineSegment(points1[0], points1[3]));
                        }
                    }
                }
            }
        }
        if (queue.size() > 0) {
            res = new LineSegment[queue.size()];
            int i = 0;
            while (!queue.isEmpty()) {
                res[i++] = queue.dequeue();
            }
        }
    }
    // the number of line segments
    public           int numberOfSegments() {
        return res.length;
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
        Point[] points = new Point[8];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000, 3000);
        points[4] = new Point(20000, 21000);
        points[5] = new Point(3000, 4000);
        points[6] = new Point(14000, 15000);
        points[7] = new Point(6000, 7000);
//         draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        System.out.println(collinear.segments().length);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment.toString());
            segment.draw();
        }
        StdDraw.show();
    }
}