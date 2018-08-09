package com.harium.keel.graph;

import com.harium.etyl.geometry.Point2D;
import com.harium.storage.graph.Node;

public class PointNode extends Node<Point2D> {

    public PointNode(Point2D data) {
        super(data);
    }

    public PointNode(double x, double y) {
        super(new Point2D(x, y));
    }

    public Point2D getPoint() {
        return super.getData();
    }
}
