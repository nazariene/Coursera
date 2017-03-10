import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Set;
import java.util.TreeSet;

public class KdTree {

    private Node treeRoot;
    private int size;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeRoot == null;
    }

    // number of points in the set
    public int size() {
        return size(treeRoot, 0);
    }

    private int size(Node node, int currentSize) {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            RectHV rect = new RectHV(0, 0, p.x(), 1);
            treeRoot = new Node(p, null, null, rect);
            size++;
        } else {
            insert(p, treeRoot, true);
        }
    }

    private void insert(Point2D p, Node currentNode, boolean useX) {
        if (p.equals(currentNode.point)) {
            return;
        }

        boolean goLeft;
        if (useX) {
            goLeft = p.x() < currentNode.point.x();
        } else {
            goLeft = p.y() < currentNode.point.y();
        }

        if (goLeft) {
            if (currentNode.leftChild == null) {
                currentNode.leftChild = new Node(p);
                size++;
            } else {
                insert(p, currentNode.leftChild, !useX);
            }
        } else {
            if (currentNode.rightChild == null) {
                currentNode.rightChild = new Node(p);
                size++;
            } else {
                insert(p, currentNode.rightChild, !useX);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        Node node = treeRoot;
        boolean useX = true;
        while (true) {
            if (node == null) {
                return false;
            }
            if (p.equals(node.point)) {
                return true;
            }
            boolean goLeft;
            if (useX) {
                goLeft = p.x() < node.point.x();
            } else {
                goLeft = p.y() < node.point.y();
            }

            if (goLeft) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }

            useX = !useX;
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(treeRoot, true);
    }

    private void draw(Node node, boolean useX) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();
        StdDraw.setPenRadius();
        if (useX) {
            StdDraw.setPenColor(StdDraw.BLUE);
            node.rect.draw();
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            node.rect.draw();
        }

        if (node.leftChild != null) {
            draw(node.leftChild, !useX);
        }
        if (node.rightChild != null) {
            draw(node.rightChild, !useX);
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> points = new TreeSet<>();
        range(rect, treeRoot, points, true);
        return points;
    }

    private void range(RectHV rect, Node node, Set<Point2D> result, boolean useX) {
        if (checkPoint(rect, node.point)) {
            result.add(node.point);
        }

        if (useX) {
            if (rect.xmin() <= node.point.x() && node.leftChild != null) {
                range(rect, node.leftChild, result, !useX);
            }
            if (rect.xmax() >= node.point.x() && node.rightChild != null) {
                range(rect, node.rightChild, result, !useX);
            }
        } else {
            if (rect.ymin() <= node.point.y() && node.leftChild != null) {
                range(rect, node.leftChild, result, !useX);
            }
            if (rect.ymax() >= node.point.y() && node.rightChild != null) {
                range(rect, node.rightChild, result, !useX);
            }
        }
    }

    private boolean checkPoint(RectHV rect, Point2D p) {
        return rect.xmin() <= p.x() && rect.xmax() >= p.x() && rect.ymin() <= p.y() && rect.ymax() >= p.y();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        if (treeRoot == null) {
            return null;
        }

        Node node = treeRoot;
        double bestDistanceSoFar = treeRoot.point.distanceTo(p);
        Point2D closestPoint = treeRoot.point;
        boolean useX = true;
        while (true) {
            if (node == null) {
                break;
            }

            if (node.point.distanceTo(p) < bestDistanceSoFar) {
                closestPoint = node.point;
            }

            boolean goLeft;
            if (useX) {
                goLeft = p.x() < node.point.x();
            } else {
                goLeft = p.y() < node.point.y();
            }

            if (goLeft) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }

            useX = !useX;
        }

        return closestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {


        KdTree tree = new KdTree();
        Point2D point2D = new Point2D(1, 1);
        tree.insert(point2D);
        System.out.println(tree.contains(point2D));
        System.out.println(tree.size());
        Point2D point2D1 = new Point2D(1, 1);
        tree.insert(point2D);
        System.out.println(tree.contains(point2D1));
        System.out.println(tree.size());
    }

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node leftChild;
        private Node rightChild;

        public Node(Point2D point) {
            this(point, null, null, null);
        }

        public Node(Point2D point, Node left, Node right, RectHV rect) {
            this.point = point;
            this.leftChild = left;
            this.rightChild = right;
            this.rect = rect;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }

    }
}
