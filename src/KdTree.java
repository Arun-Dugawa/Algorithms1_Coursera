import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

public class KdTree {

    private static class Node {
        private double x, y;
        private Node left, right;
        private boolean isVertical;
    }
    private static final double EPSILON = 0.0000001;
    private Node root;
    private int size = 0;
    public KdTree() {
    }
    public boolean isEmpty() {  // is the set empty?
        return size() == 0;
    }
    public               int size() {  // number of points in the set
        return this.size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) {
            root = insert(root, p, true);
            ++size;
        }
    }

    private Node insert(Node root, Point2D p, boolean curr) {
        if (root == null) {
            Node temp = new Node();
            temp.x = p.x();
            temp.y = p.y();
            temp.isVertical = curr;
            return temp;
        } else if (root.isVertical) {
            if (p.x() < root.x) {
                root.left = insert(root.left, p, !curr);
            } else {
                root.right = insert(root.right, p, !curr);
            }
        } else {
            if (p.y() < root.y) {
                root.left = insert(root.left, p, !curr);
            } else {
                root.right = insert(root.right, p, !curr);
            }
        }
        return root;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node root, Point2D p) {
        if (root == null) return false;
//        Point2D curr = new Point2D(root.x, root.y);
        if (root.isVertical) {
            if (Math.abs(p.x() - root.x) < EPSILON && Math.abs(p.y() - root.y) < EPSILON) return true;
            if (root.x > p.x()) {
                return contains(root.left, p);
            } else {
                return contains(root.right, p);
            }
        } else {
            if (Math.abs(p.x() - root.x) < EPSILON && Math.abs(p.y() - root.y) < EPSILON) return true;
            if (root.y > p.y()) {
                return contains(root.left, p);
            } else {
                return contains(root.right, p);
            }
        }
    }

    public void draw() {
        draw(root, 0, 1, 0, 1);
        StdDraw.show();
    }
    private void draw(Node root, double minX, double maxX, double minY, double maxY) {
        if (root == null) return;
        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(root.x, root.y, 0.005);
        if (root.isVertical) {
            StdDraw.setPenColor(Color.red);
            StdDraw.line(root.x, minY, root.x, maxY);
            draw(root.left, minX, root.x, minY, maxY);
            draw(root.right, root.x, maxX, minY, maxY);
        } else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.line(minX, root.y, maxX, root.y);
            draw(root.left, minX, maxX, minY, root.y);
            draw(root.right, minX, maxX, root.y, maxY);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {  // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        range(rect, root, queue);
        return queue;
    }

    private void range(RectHV rect, Node root, Queue<Point2D> queue) {
        if (root == null) return;
        Point2D curr = new Point2D(root.x, root.y);
        if (rect.contains(curr)) {
            queue.enqueue(curr);
        }
        if (root.isVertical) {
            if (root.x > rect.xmax()) {
                range(rect, root.left, queue);
            } else if (root.x < rect.xmin()) {
                range(rect, root.right, queue);
            } else {
                range(rect, root.left, queue);
                range(rect, root.right, queue);
            }
        } else {
            if (root.y > rect.ymax()) {
                range(rect, root.left, queue);
            } else if (root.y < rect.ymin()) {
                range(rect, root.right, queue);
            } else {
                range(rect, root.left, queue);
                range(rect, root.right, queue);
            }
        }
    }
    public           Point2D nearest(Point2D p) {  // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        if (root == null ) return null;
        Point2D curr = new Point2D(root.x, root.y);
        return nearest(p, curr, root);
    }
    private Point2D nearest(Point2D p, Point2D nearest, Node root) {
        if (root == null) return nearest;
        Point2D curr = new Point2D(root.x, root.y);
        Point2D temp = p.distanceSquaredTo(curr) < p.distanceSquaredTo(nearest) ? curr : nearest;
        if (root.isVertical) {
            if (p.x() > root.x) {
                Point2D npr = nearest(p, temp, root.right);
                if (p.distanceSquaredTo(npr) > (p.x() - root.x) * (p.x() - root.x)) {
                    Point2D npl = nearest(p, npr, root.left);
                    return p.distanceSquaredTo(npr) < p.distanceSquaredTo(npl) ? npr : npl;
                } else {
                    return npr;
                }
            } else {
                Point2D npl = nearest(p, temp, root.left);
                if (p.distanceSquaredTo(nearest) > (root.x - p.x()) * (root.x - p.x())) {
                    Point2D npr = nearest(p, npl, root.right);
                    return p.distanceSquaredTo(npr) < p.distanceSquaredTo(npl) ? npr : npl;
                } else {
                    return npl;
                }

            }
        } else {
            if (p.y() > root.y) {
                Point2D npr = nearest(p, temp, root.right);
                if (p.distanceSquaredTo(nearest) > (p.y() - root.y) * (p.y() - root.y)) {
                    Point2D npl = nearest(p, nearest, root.left);
                    return p.distanceSquaredTo(npr) < p.distanceSquaredTo(npl) ? npr : npl;
                } else {
                    return npr;
                }
            } else {
                Point2D npl = nearest(p, temp, root.left);
                if (p.distanceSquaredTo(nearest) > (root.y - p.y()) * (root.y - p.y())) {
                    Point2D npr = nearest(p, nearest, root.right);
                    return p.distanceSquaredTo(npr) < p.distanceSquaredTo(npl) ? npr : npl;
                } else {
                    return npl;
                }
            }
        }

    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        System.out.println(tree .isEmpty());
        tree.insert(new Point2D(0.7, 0.2));
//        System.out.println(tree.root.x + " " + tree.root.y);
        tree.insert(new Point2D(0.5, 0.4));
//        System.out.println(tree.root.x + " " + tree.root.y);
        tree.insert(new Point2D(0.2, 0.3));
//        System.out.println(tree.root.x + " " + tree.root.y);
        tree.insert(new Point2D(0.4, 0.7));
//        System.out.println(tree.root.x + " " + tree.root.y);
        tree.insert(new Point2D(0.9, 0.6));
        System.out.println(tree.root.x + " " + tree.root.y);
        System.out.println(tree.isEmpty());
        System.out.println(tree.size());
        tree.draw();

        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.4);

//        Iterable<Point2D> queue = tree.range(rect);

        for (Point2D p : tree.range(rect)) {
            System.out.print("(" + p.x() + ", " + p.y() + "), ");
        }
        System.out.println();
        Point2D p = new Point2D(0.5, 0.1);
        Point2D ans = tree.nearest(p);
//        StdDraw.setPenColor(Color.magenta);
//        StdDraw.filledCircle(p.x(), p.y(), 0.005);
//        StdDraw.line(p.x(), p.y(), ans.x(), ans.y());
        System.out.println("nearest (" + ans.x() + ", " + ans.y() + ")");
//        System.out.println(tree.contains(new Point2D(0.5, 0.4)));
    }



}
