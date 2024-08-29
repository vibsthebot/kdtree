import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    Node root;
    int size = 0;
    // construct an empty set of points
    public KdTree() {

    }
    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(null, p, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }
        double x = p.x();
        double y = p.y();
        recursiveInsert(root, p, x, y);
    }

    private void recursiveInsert(Node curNode, Point2D p, double x, double y) {
        if (curNode.vertical) {
            if (x > curNode.x) {
                if (curNode.rightChild == null) {
                    curNode.rightChild = new Node(curNode, p, new RectHV(curNode.x, curNode.rect.ymin(), curNode.rect.xmax(), curNode.rect.ymax()));
                    size++;
                } else if (!curNode.rightChild.point.equals(p)) {
                    recursiveInsert(curNode.rightChild, p, x, y);
                }
            } else if (x < curNode.x) {
                if (curNode.leftChild == null) {
                    curNode.leftChild = new Node(curNode, p, new RectHV(curNode.rect.xmin(), curNode.rect.ymin(), curNode.x, curNode.rect.ymax()));
                    size++;
                } else if (!curNode.leftChild.point.equals(p)) {
                    recursiveInsert(curNode.leftChild, p, x, y);
                }
            }
        } else {
            if (y > curNode.y) {
                if (curNode.rightChild == null) {
                    curNode.rightChild = new Node(curNode, p, new RectHV(curNode.rect.xmin(), curNode.rect.ymin(), curNode.rect.xmax(), curNode.y));
                    size++;
                } else if (curNode.rightChild.point != p) {
                    recursiveInsert(curNode.rightChild, p, x, y);
                }
            } else if (y < curNode.y) {
                if (curNode.leftChild == null) {
                    curNode.leftChild = new Node(curNode, p, new RectHV(curNode.rect.xmin(), curNode.y, curNode.rect.xmax(), curNode.rect.ymax()));
                    size++;
                } else if (curNode.leftChild.point != p) {
                    recursiveInsert(curNode.leftChild, p, x, y);
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        if (root.point.equals(p)) return true;
        return recursiveSearch(root, p, p.x(), p.y());
    }

    private boolean recursiveSearch(Node curNode, Point2D p, double x, double y) {
        if (curNode.vertical) {
            if (x > curNode.x) {
                if (curNode.rightChild.point == null) {
                    return false;
                } else if (curNode.rightChild.point.equals(p)) {
                    return true;
                } else {
                    return recursiveSearch(curNode.rightChild, p, x, y);
                }
            } else if (x < curNode.x) {
                if (curNode.leftChild.point == null) {
                    return false;
                } else if (curNode.leftChild.point.equals(p)) {
                    return true;
                } else {
                    return recursiveSearch(curNode.leftChild, p, x, y);
                }
            }
        } else {
            if (y > curNode.y) {
                if (curNode.rightChild == null) {
                    return false;
                } else if (curNode.rightChild.point.equals(p)) {
                    return true;
                } else {
                    return recursiveSearch(curNode.rightChild, p, x, y);
                }
            } else if (y < curNode.y) {
                if (curNode.leftChild == null) {
                    return false;
                } else if (curNode.leftChild.point.equals(p)) {
                    return true;
                } else {
                    return recursiveSearch(curNode.leftChild, p, x, y);
                }
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        if (root.point != null) {
            recursiveDraw(root);
        }
    }

    private void recursiveDraw(Node node) {
        StdDraw.setPenRadius(0.02);
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            if (node.parent == null) {
                StdDraw.line(node.x, 0, node.x, 1);
            } else if (node.parent.parent.parent == null) {
                if (node.parent.y > node.y) {
                    StdDraw.line(node.x, 0, node.x, node.parent.y);
                } else if (node.parent.y != node.y) {
                    StdDraw.line(node.x, node.parent.y, node.x, 1);
                }
            } else {
                StdDraw.line(node.x, node.parent.y, node.x, node.parent.parent.parent.y);
            }
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (node.parent.parent == null) {
                if (node.parent.x > node.x) {
                    StdDraw.line(0, node.y, node.parent.x, node.y);
                } else if (node.parent.x != node.x) {
                    StdDraw.line(node.parent.x, node.y, 1, node.y);
                }
            } else {
                StdDraw.line(node.parent.x, node.y, node.parent.parent.parent.x, node.y);
            }
        }
        StdDraw.setPenRadius(0.04);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.x, node.y);
        if (node.leftChild != null) {
            recursiveDraw(node.leftChild);
        }
        if (node.rightChild!= null) {
            recursiveDraw(node.rightChild);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (isEmpty()) return null;
        double xMin = rect.xmin();
        double xMax = rect.xmax();
        double yMin = rect.ymin();
        double yMax = rect.ymax();
        ArrayList<Point2D> iterable = new ArrayList<>();
        recursiveRange(root, iterable, rect, xMin, xMax, yMin, yMax);
        return iterable;
    }

    private void recursiveRange(Node node, ArrayList<Point2D> iterable, RectHV rect, double xMin, double xMax, double yMin, double yMax) {
        if (rect.contains(node.point)) {
            iterable.add(node.point);
        }
        if (node.vertical) {
            if (xMin <= node.x && node.leftChild != null) {
                recursiveRange(node.leftChild, iterable, rect, xMin, xMax, yMin, yMax);
            }
            if (xMax >= node.x && node.rightChild != null) {
                recursiveRange(node.rightChild, iterable, rect, xMin, xMax, yMin, yMax);
            }
        } else {
            if (yMin <= node.y && node.leftChild != null) {
                recursiveRange(node.leftChild, iterable, rect, xMin, xMax, yMin, yMax);
            }
            if (yMax >= node.y && node.rightChild != null) {
                recursiveRange(node.rightChild, iterable, rect, xMin, xMax, yMin, yMax);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        if (contains(p)) return p;
        NearestData nearest = new NearestData(null, 2);
        recursiveNearest(root, p, nearest);
        return nearest.point;
    }

    private void recursiveNearest(Node node, Point2D p, NearestData nearest) {
        double distance = node.point.distanceTo(p);
        if (distance < nearest.distance) {
            nearest.distance = distance;
            nearest.point = node.point;
        }

        // Define left and right rectangles

        // Check if the point is within the distance from the left rectangle
        if (node.leftChild != null && node.leftChild.rect.distanceTo(p) < nearest.distance) {
            recursiveNearest(node.leftChild, p, nearest);
        }

        // Check if the point is within the distance from the right rectangle
        if (node.rightChild != null && node.rightChild.rect.distanceTo(p) < nearest.distance) {
            recursiveNearest(node.rightChild, p, nearest);
        }
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.7, 0.5));
        tree.insert(new Point2D(0.3, 0.2));
        tree.insert(new Point2D(0.4, 0.3));
        tree.insert(new Point2D(0.8, 0.7));
        tree.insert(new Point2D(0.6, 0.8));
        tree.draw();
        for (Point2D i : tree.range(new RectHV(0.5, 0.5, 0.5, 0.5))) {
            System.out.println(i);
        }
        System.out.println(tree.nearest(new Point2D(0.6, 0.79999)));
    }

    private class Node {
        Node leftChild = null;
        Node rightChild = null;
        Node parent;
        RectHV rect;
        Point2D point;
        double x;
        double y;
        boolean vertical;

        public Node(Node parent, Point2D point, RectHV rect) {
            this.point = point;
            this.parent = parent;
            this.rect = rect;
            vertical = parent == null || !parent.vertical;
            x = point.x();
            y = point.y();
        }
    }
    private class NearestData {
        Point2D point;
        double distance;

        public NearestData(Point2D point, double distance) {
            this.point = point;
            this.distance = distance;
        }
    }
}
