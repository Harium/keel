package com.harium.keel.modifier;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.core.Modifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.Direction;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.graph.PointEdge;
import com.harium.keel.graph.PointNode;
import com.harium.keel.helper.PointListHelper;
import com.harium.storage.graph.Graph;
import com.harium.storage.graph.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MiddleLine modifier
 */
public class MiddleLineModifier implements ComponentModifierStrategy, Modifier<PointFeature, Graph<Point2D>> {

    public MiddleLineModifier() {
        super();
    }

    @Override
    public PointFeature modifyComponent(PointFeature component) {

        Graph<Point2D> g = apply(component);

        PointFeature polygon = new PointFeature(0, 0);

        for (Node<Point2D> node : g.getNodes()) {
            polygon.add(node.getData());
        }

        return polygon;

    }

    public Graph<Point2D> apply(PointFeature feature) {

        List<Point2D> points = feature.getPoints();

        List<Point2D> list = PointListHelper.cloneList(points);

        Graph<Point2D> graph = new Graph<>();

        if (points.size() < 3) return graph;

        List<Point2D> mediumNodes = new ArrayList<Point2D>();

        List<Direction> directions = processDirections(list);

        for (int i = 0; i < list.size(); i++) {

            List<Direction> subList = directions.subList(0, 3);

            if (isRegion(subList)) {

                addNodes(list, mediumNodes, graph);

                rotateLists(3, directions, list);

                i += 2;

                continue;

            }

            rotateLists(1, directions, list);

        }

        addVisibleComponents(graph, mediumNodes, list);

        return graph;

    }

    private void rotateLists(int times, List<?>... lists) {

        for (int i = 0; i < times; i++) {

            for (List<?> list : lists) {

                Collections.rotate(list, -1);

            }

        }

    }

    private void addNodes(List<Point2D> list, List<Point2D> mediumNodes, Graph<Point2D> graph) {

        Node<Point2D> a = new Node<Point2D>(list.get(0));
        Node<Point2D> b = new Node<Point2D>(list.get(1));
        Node<Point2D> c = new Node<Point2D>(list.get(2));
        Node<Point2D> d = new Node<Point2D>(list.get(3));

        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);

        graph.addEdge(new PointEdge(a, b));
        graph.addEdge(new PointEdge(b, c));
        graph.addEdge(new PointEdge(c, d));
        graph.addEdge(new PointEdge(d, a));

        double nodeSumX = 0;
        double nodeSumY = 0;

        for (Node<Point2D> node : graph.getNodes()) {

            Point2D point = node.getData();

            nodeSumX += point.x;
            nodeSumY += point.y;
        }

        Node<Point2D> mediumNode = new PointNode((nodeSumX) / 4, (nodeSumY) / 4);

        graph.addNode(mediumNode);

        mediumNodes.add(mediumNode.getData());

    }

    private List<Direction> processDirections(List<Point2D> list) {

        List<Direction> directions = new ArrayList<Direction>();

        for (int i = 1; i < list.size(); i++) {

            Point2D fa = list.get(i - 1);
            Point2D fb = list.get(i);

            directions.add(classify(fa, fb));

        }

        return directions;
    }

    private void addVisibleComponents(Graph<Point2D> graph, List<Point2D> mediumNodes, List<Point2D> list) {

        Node<Point2D> centroid = new Node<Point2D>(getCentroid(list));

        graph.addNode(centroid);

        for (Point2D node : mediumNodes) {
            graph.addEdge(new PointEdge(new Node<Point2D>(node), centroid));
        }
    }

    private Point2D getCentroid(List<Point2D> points) {

        int px = 0;
        int py = 0;

        int n = points.size();

        for (Point2D point : points) {
            px += point.x;
            py += point.y;
        }

        return new Point2D(px / n, py / n);

    }

    public static Direction classify(Point2D a, Point2D b) {
        double dx = b.x - a.x;
        double dy = b.y - a.y;

        double tolerance = 3;

        //Right
        if (dx > 0) {

            //Down
            if (dy > 0) {

                if (dx - dy < tolerance) {
                    return Direction.DOWN_RIGHT;
                }

                //Right is Higher
                if (dx > dy) {
                    return Direction.RIGHT;
                    //Down is Higher
                } else {
                    return Direction.DOWN;
                }

                //Up
            } else {

                //Flip Coordinates Vertically
                dy = -dy;

                if (dx - dy < tolerance) {
                    return Direction.UP_RIGHT;
                }

                //Right is Higher
                if (dx > dy) {
                    return Direction.RIGHT;
                    //Up is Higher
                } else {
                    return Direction.UP;
                }

            }

            //Left
        } else {

            //Flip Coordinates Horizontally
            dx = -dx;

            //Down
            if (dy > 0) {

                if (dx - dy < tolerance) {
                    return Direction.DOWN_LEFT;
                }

                //Left is Higher
                if (dx > dy) {
                    return Direction.LEFT;
                    //Down is Higher
                } else {
                    return Direction.DOWN;
                }

                //Up
            } else {

                dy = -dy;

                if (dx - dy < tolerance) {
                    return Direction.UP_LEFT;
                }

                //Left is Higher
                if (dx > dy) {
                    return Direction.LEFT;
                    //Up is Higher
                } else {
                    return Direction.UP;
                }

            }

        }

    }

    private boolean isRegion(List<Direction> list) {

        if (list.size() < 3) {

            return false;
        }

        Direction a = list.get(0);

        Direction b = list.get(1);

        Direction c = list.get(2);

        return isRegion(a, b, c);

    }

    private boolean isRegion(Direction a, Direction b, Direction c) {

        if (isUpDirection(a) && isRightDirection(b) && isDownDirection(c)) {
            return true;
        }

        if (isRightDirection(a) && isDownDirection(b) && isLeftDirection(c)) {
            return true;
        }

        if (isDownDirection(a) && isLeftDirection(b) && isUpDirection(c)) {
            return true;
        }

        if (isLeftDirection(a) && isUpDirection(b) && isRightDirection(c)) {
            return true;
        }

        return false;

    }

    private boolean isUpDirection(Direction direction) {
        return (direction == Direction.UP) || (direction == Direction.UP_LEFT) || (direction == Direction.UP_RIGHT);
    }

    private boolean isDownDirection(Direction direction) {
        return (direction == Direction.DOWN) || (direction == Direction.DOWN_LEFT) || (direction == Direction.DOWN_RIGHT);
    }

    private boolean isRightDirection(Direction direction) {
        return (direction == Direction.RIGHT) || (direction == Direction.UP_RIGHT) || (direction == Direction.DOWN_RIGHT);
    }

    private boolean isLeftDirection(Direction direction) {
        return (direction == Direction.LEFT) || (direction == Direction.UP_LEFT) || (direction == Direction.DOWN_LEFT);
    }

}
