import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import org.w3c.dom.Node;

public class KdTree {
    Node root;
    int size = 0;
    // construct an empty set of points
    public KdTree() {

    }
    // is the set empty?
    public boolean isEmpty() {
        return root.point == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(null, p);
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
                    curNode.rightChild = new Node(curNode, p);
                } else if (curNode.rightChild.point != p) {
                    recursiveInsert(curNode.rightChild, p, x, y);
                }
            } else if (x < curNode.x) {
                if (curNode.leftChild == null) {
                    curNode.leftChild = new Node(curNode, p);
                } else if (curNode.leftChild.point != p) {
                    recursiveInsert(curNode.leftChild, p, x, y);
                }
            }
        } else {
            if (y > curNode.y) {
                if (curNode.rightChild == null) {
                    curNode.rightChild = new Node(curNode, p);
                } else if (curNode.rightChild.point != p) {
                    recursiveInsert(curNode.rightChild, p, x, y);
                }
            } else if (y < curNode.y) {
                if (curNode.leftChild == null) {
                    curNode.leftChild = new Node(curNode, p);
                } else if (curNode.leftChild.point != p) {
                    recursiveInsert(curNode.leftChild, p, x, y);
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        if (root.point == p) return true;
        return recursiveSearch(root, p, p.x(), p.y());
    }

    private boolean recursiveSearch(Node curNode, Point2D p, double x, double y) {
        if (curNode.vertical) {
            if (x > curNode.x) {
                if (curNode.rightChild.point == null) {
                    return false;
                } else if (curNode.rightChild.point == p) {
                    return true;
                } else {
                    recursiveSearch(curNode.rightChild, p, x, y);
                }
            } else if (x < curNode.x) {
                if (curNode.leftChild.point == null) {
                    return false;
                } else if (curNode.leftChild.point == p) {
                    return true;
                } else {
                    recursiveSearch(curNode.leftChild, p, x, y);
                }
            }
        } else {
            if (x > curNode.x) {
                if (curNode.rightChild.point == null) {
                    return false;
                } else if (curNode.rightChild.point == p) {
                    return true;
                } else {
                    recursiveSearch(curNode.rightChild, p, x, y);
                }
            } else if (x < curNode.x) {
                if (curNode.leftChild.point == null) {
                    return false;
                } else if (curNode.leftChild.point == p) {
                    return true;
                } else {
                    recursiveSearch(curNode.leftChild, p, x, y);
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
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.x, node.y);
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.x, 0, node.x, 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(0, node.y, 1, node.y);
        }
        if (node.leftChild != null) {
            recursiveDraw(node.leftChild);
        }
        if (node.rightChild!= null) {
            recursiveDraw(node.rightChild);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

    }

    public static void main(String[] args) {

    }

    private class Node {
        Node parent;
        Node leftChild = null;
        Node rightChild = null;
        Point2D point;
        double x;
        double y;
        boolean vertical;

        public Node(Node parent, Point2D point) {
            this.parent = parent;
            this.point = point;
            vertical = parent == null || !parent.vertical;
            x = point.x();
            y = point.y();
        }
    }
}
